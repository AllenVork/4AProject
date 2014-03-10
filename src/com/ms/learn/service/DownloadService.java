

package com.ms.learn.service;

import java.io.File;
import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import com.ms.learn.ALearnApplication;
import com.ms.learn.activity.DownloadActivity;
import com.ms.learn.bean.OtherVideo;
import com.ms.learn.download.DownloadDatabase;
import com.ms.learn.download.DownloadDatabaseImpl;
import com.ms.learn.download.DownloadJob;
import com.ms.learn.download.DownloadJobListener;
import com.ms.learn.download.MediaScannerNotifier;
import com.ms.learn.util.FileUtil;

public class DownloadService extends Service {
	
	public static final String ACTION_ADD_TO_DOWNLOAD = "add_to_download";
	
	public static final String EXTRA_PLAYLIST_ENTRY = "video_entry";

	private static final int DOWNLOAD_NOTIFY_ID = 667668;
	private final String VIDEO_FORMAT=".MP4";
	
	private NotificationManager mNotificationManager = null;//����֪ͨ

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate(){
		super.onCreate();
		
		// TODO check download database for any not downloaded things, add the to downloadQueue and start
		mNotificationManager = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		if(intent == null){
			return;
		}
		
		String action = intent.getAction();
		
		if(action.equals(ACTION_ADD_TO_DOWNLOAD)){
			OtherVideo entry = (OtherVideo) intent.getSerializableExtra(EXTRA_PLAYLIST_ENTRY);
			addToDownloadQueue(entry, startId);//��ʼ����
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private DownloadJobListener mDownloadJobListener = new DownloadJobListener(){

		@Override
		public void downloadEnded(DownloadJob job) {
			getQueuedDownloads().remove(job);
			getCompletedDownloads().add(job);
			DownloadDatabase downloadDatabase = getDownloadDatabase();//��ȡ��������ݿ⣬��������
			if(downloadDatabase != null){
				downloadDatabase.setStatus(job.getOtherVideo(), true);//�������ݿ������ص�״̬
			}
			displayNotifcation(job);//��ʾ֪ͨ
			new MediaScannerNotifier(DownloadService.this, job);
		}

		@Override
		public void downloadStarted() {
		}

	};
	
	//��ʾ֪ͨ
	private void displayNotifcation(DownloadJob job)
	{

		String notificationMessage = job.getOtherVideo().getVideoName() ;

		Notification notification = new Notification(
				android.R.drawable.stat_sys_download_done, notificationMessage, System.currentTimeMillis() );

		PendingIntent contentIntent = PendingIntent.getActivity( this, 0,
				new Intent( this, DownloadActivity.class ), 0);

		notification.setLatestEventInfo( this, "�������",
				notificationMessage, contentIntent );
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		mNotificationManager.notify( DOWNLOAD_NOTIFY_ID, notification );
	}
	
	/**
	 * Interface to database on the remote storage device
	 */
	public static DownloadDatabase getDownloadDatabase(){
		return new DownloadDatabaseImpl(getDownloadPath()+"/alean.db");
	}

	public static String getDownloadPath(){
		System.out.println("+++++++++++++++++++"+FileUtil.sdcardIsExit());
		if(FileUtil.sdcardIsExit()){
			File file=new File(Environment.getExternalStorageDirectory(),"alean/video");
			if(!file.exists()){
				file.mkdirs();
			}
			/*File fileDir=new File(file.getAbsolutePath(),"/db");
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}*/
			System.out.println("=================="+file.getAbsolutePath()+"=========="+file.exists());
			
		}
		
		return Environment.getExternalStorageDirectory()+"/alean/video";
	}
	
	public void addToDownloadQueue(OtherVideo entry, int startId) {
		
		// check database if record already exists, if so abandon starting
		// another download process
		String downloadPath = getDownloadPath();//��ȡ�������ļ������·��
		DownloadJob downloadJob = new DownloadJob(entry, downloadPath, startId, VIDEO_FORMAT);//��������JOB
		DownloadDatabase downloadDatabase = getDownloadDatabase();//�����������ݵ����ݿ�
		if(downloadDatabase != null){
			boolean existst = downloadDatabase.addToLibrary(downloadJob.getOtherVideo());//��ӵ����ݿ�֮��
			if(existst){
			    Toast.makeText(DownloadService.this, "������", 3000).show();
				return;
		  }else{
			  Toast.makeText(DownloadService.this, "��ʼ����", 3000).show();
			downloadJob.setListener(mDownloadJobListener);	//�������ؼ���
			getQueuedDownloads().add(downloadJob);//��ӵ����ض�����
			downloadJob.start();//��ʼ����
			}
		}
		
	
	}
	
	public ArrayList<DownloadJob> getQueuedDownloads(){
		return ALearnApplication.getInstance().getQueuedDownloads();
	}
	
	public ArrayList<DownloadJob> getCompletedDownloads(){
		return ALearnApplication.getInstance().getCompletedDownloads();
	}

}
