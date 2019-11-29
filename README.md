# URLDownloader
This utility library helps in downloading files from url and provides callback for the whole process.

[![](https://jitpack.io/v/aashitshah26/URLDownloader.svg)](https://jitpack.io/#aashitshah26/URLDownloader)
![](https://img.shields.io/apm/l/vim-mode)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)

This project will help you to download files from url and provides callbacks for progress , completion , pause and restart.


To add *URLDownloader* in your project follow the following steps :-

## Installing

Add [JitPack](https://jitpack.io) repository to your root `build.gradle`

```gradle
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add dependency to your module `build.gradle`:

```gradle
implementation 'com.github.aashitshah26:URLDownloader:1.0.2'
```

## Usage

Add *URLDownloader* to your Code:

Initialize *URLDownloader* in your activity:

```java
URLDownloader urlDownloader = new URLDownloader(this);
urlDownloader.setDownload(url, downloadTitle, Environment.getExternalStorageDirectory().getPath());
urlDownloader.startDownload();  //Always call setDownload() before calling startDownload()
```

Set callbacks for *URLDownloader* in your activity:

```java
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
            
            @Override
            public void onCancelled(String downloadTitle){
               //Download Cancelled
            }
        });
      
        
```


## License 

    MIT License

    Copyright (c) 2019 Aashit Shah

    Permission is hereby granted, free of charge, to any person obtaining a copy 
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
    of the Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies
    or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
