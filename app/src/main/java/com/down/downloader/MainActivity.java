package com.down.downloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import comm.downurl.urldownloader.URLDownloader;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    URLDownloader urlDownloader;
    String extension = ".mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit);
        urlDownloader = new URLDownloader(this);
        urlDownloader.setDownloadCallbacks(new URLDownloader.DownloadCallbacks() {
            @Override
            public void dowloadProgress(String downloadTitle, int percent) {
                //Progress percentage
            }

            @Override
            public void onCompleted(String downloadTitle) {
                //Download Completed
            }

            @Override
            public void onFailed(String downloadTitle) {
                //Download Failed
            }

            @Override
            public void onPaused(String downloadTitle) {
                //Download Paused
            }

            @Override
            public void onRunning(String downloadTitle) {
                //Download Running
            }
        });
    }

    public void button(View view) {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (TextUtils.isEmpty(editText.getText())) {
                String url = editText.getText().toString();
                urlDownloader.setDownload(url, "Try" + extension, Environment.getExternalStorageDirectory().getPath());
                urlDownloader.startDownload();
            } else {
                Toast.makeText(this, "Enter a url!", Toast.LENGTH_SHORT).show();
            }
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (TextUtils.isEmpty(editText.getText())) {
                    String url = editText.getText().toString();
                    urlDownloader.setDownload(url, "Try" + extension, Environment.getExternalStorageDirectory().getPath());
                    urlDownloader.startDownload();
                } else {
                    Toast.makeText(this, "Enter a url!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please grant permission to download!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
