package comm.downurl.urldownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.File;



public class URLDownloader {

    Context context;
    DownloadManager manager;
    DownloadManager.Request request;
    String downloadUrl;
    String downloadTitle;
    String directory;
    DownloadCallbacks listener;
     int lastProgress = -1;
     final private int PERMISSION_CODE=69;


    public interface DownloadCallbacks{
        void dowloadProgress(String downloadTitle, int percent);
        void onCompleted(String downloadTitle);
        void onFailed(String downloadTitle);
        void onPaused(String downloadTitle);
        void onRunning(String downloadTitle);
    }

    public URLDownloader(Context context){
        this.context =context;
    }

    public void setDownloadCallbacks(DownloadCallbacks callbacks){
        this.listener=callbacks;
    }



    public void setDownload(String downloadUrl,String downloadTitle,String directory){
        this.downloadUrl=downloadUrl;
        this.downloadTitle=downloadTitle;
        this.directory=directory;
        try {
            Uri uri = Uri.parse(downloadUrl);
             request = new DownloadManager.Request(uri);
            request.setTitle(downloadTitle);

            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            File file = new File(directory);
            if (!file.exists()) {
                file.mkdirs();
            }
            request.setDestinationUri(Uri.fromFile(new File(directory, downloadTitle)));

             manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void startDownload(){
        final long downloadId = manager.enqueue(request);
        getProgress(downloadId);
    }


    private void getProgress(final long downloadId) {


        new Thread(new Runnable() {




            @Override
            public void run() {

                boolean downloading = true;

                while (downloading) {

                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);

                    Cursor cursor = manager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                    }

                    final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);

                    if (listener!=null) {
                        if (dl_progress != lastProgress) {
                            lastProgress = dl_progress;
                            listener.dowloadProgress(downloadTitle, dl_progress);
                        }

                    }
                    Log.e("progress",dl_progress+"");

                     statusMessage(cursor,downloadTitle,listener);

                    cursor.close();
                }

            }
        }).start();
    }


    private void statusMessage(Cursor c,String downloadTitle,DownloadCallbacks listener) {
        String msg = "???";

        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                if (listener!=null) {
                    listener.onFailed(downloadTitle);
                }
                break;

            case DownloadManager.STATUS_PAUSED:
                if (listener!=null) {
                    listener.onPaused(downloadTitle);
                }
                break;

            case DownloadManager.STATUS_PENDING:
                msg = "Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                if (listener!=null) {
                    listener.onRunning(downloadTitle);
                }
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                if (listener!=null) {
                    listener.onCompleted(downloadTitle);
                }
                break;

            default:
                msg = "Download is nowhere in sight";
                break;
        }

    }

}
