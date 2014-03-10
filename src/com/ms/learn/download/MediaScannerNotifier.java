

package com.ms.learn.download;

import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;

import com.ms.learn.service.DownloadService;

/**
 * ɨ�������ļ���ϵͳý���
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
		String path = DownloadHelper.getAbsolutePath(mDownloadJob.getDestination());//��ȡ���浽���ص��ļ�·��
		String fileName = DownloadHelper.getFileName(mDownloadJob.getOtherVideo());//�ļ�����
		mConnection.scanFile(path+"/"+fileName, null); //ɨ���ļ�
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
