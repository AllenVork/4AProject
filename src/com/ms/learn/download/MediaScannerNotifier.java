

package com.ms.learn.download;

import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;

import com.ms.learn.service.DownloadService;

/**
 * 扫描音乐文件到系统媒体库
 */
public class MediaScannerNotifier implements MediaScannerConnectionClient{
	
    private MediaScannerConnection mConnection;
    private DownloadJob mDownloadJob;
    private DownloadService mService;

	public MediaScannerNotifier(DownloadService service, DownloadJob job) {
		mDownloadJob = job;
		mService = service;
		mConnection = new MediaScannerConnection(mService, this);
		mConnection.connect();
	} 

	@Override
	public void onMediaScannerConnected() {
		//String path = mDownloadJob.getDestination();
		String path = DownloadHelper.getAbsolutePath(mDownloadJob.getDestination());//获取保存到本地的文件路径
		String fileName = DownloadHelper.getFileName(mDownloadJob.getOtherVideo());//文件名称
		mConnection.scanFile(path+"/"+fileName, null); //扫描文件
	}

	@Override
	public void onScanCompleted(String text, Uri uri) {
		mConnection.disconnect();
		
		// stop service if there is no downloads left
		if(mService.getQueuedDownloads().size() == 0){
			mService.stopSelf();
		}
	}

}
