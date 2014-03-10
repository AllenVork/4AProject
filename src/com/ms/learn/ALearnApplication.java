package com.ms.learn;

import java.io.File;
import java.util.ArrayList;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import cn.jpush.android.api.JPushInterface;

import com.ms.learn.bean.OtherVideo;
import com.ms.learn.bean.UserInfo;
import com.ms.learn.bean.VideoDetailInfo;
import com.ms.learn.download.DownloadDatabase;
import com.ms.learn.download.DownloadHelper;
import com.ms.learn.download.DownloadInterface;
import com.ms.learn.download.DownloadJob;
import com.ms.learn.service.DownloadService;
import com.ms.learn.util.ImageCache;

public class ALearnApplication extends Application {
	private static ALearnApplication instance = null;
	private String userName = null;
	private String userPwd = null;
	private ImageCache mImageCache;
	private VideoDetailInfo mVideoDetailInfo;
	private OtherVideo mOtherVideo;
	private String postsTid;
	// ��ʱ�����û���Ϣ
	private UserInfo userInfo;

	/**
	 * ���ؽӿ�
	 */
	private DownloadInterface mDownloadInterface;
	/**
	 * �洢�Ѿ�������ɵ�������Ϣ
	 */
	private ArrayList<DownloadJob> mCompletedDownloads;

	/**
	 * �洢�������صĶ�����Ϣ
	 */
	private ArrayList<DownloadJob> mQueuedDownloads;

	/**
	 * ��ȡͼƬ����
	 * 
	 * @return
	 */
	public ImageCache getImageCache() {
		return mImageCache;
	}

	// ����ģʽ�л�ȡΨһ��MyApplicationʵ��
	public static ALearnApplication getInstance() {
		if (null == instance) {
			instance = new ALearnApplication();
		}
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;
		mImageCache = new ImageCache();
		mQueuedDownloads = new ArrayList<DownloadJob>();
		mCompletedDownloads = new ArrayList<DownloadJob>();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public VideoDetailInfo getmVideoDetailInfo() {
		return mVideoDetailInfo;
	}

	public void setmVideoDetailInfo(VideoDetailInfo mVideoDetailInfo) {
		this.mVideoDetailInfo = mVideoDetailInfo;
	}

	public void setDownloadInterface(DownloadInterface downloadInterface) {
		mDownloadInterface = downloadInterface;
	}

	// ��ȡDownloadInterface����
	public DownloadInterface getDownloadInterface() {
		if (mDownloadInterface == null) {
			mDownloadInterface = new IntentDownloadInterface();
		}
		return mDownloadInterface;
	}

	// �����������
	public void setQueuedDownloads(ArrayList<DownloadJob> mQueuedDownloads) {
		this.mQueuedDownloads = mQueuedDownloads;
	}

	// ��ȡ��������
	public ArrayList<DownloadJob> getQueuedDownloads() {
		return mQueuedDownloads;
	}

	// ��¼�Ѿ�������ɵ�����
	public void setCompletedDownloads(ArrayList<DownloadJob> mCompletedDownloads) {
		this.mCompletedDownloads = mCompletedDownloads;
	}

	// ��ȡ������ɵ�����
	public ArrayList<DownloadJob> getCompletedDownloads() {
		return mCompletedDownloads;
	}

	// ���ض���
	private class IntentDownloadInterface implements DownloadInterface {

		// ��ӵ����ض�����
		@Override
		public void addToDownloadQueue(OtherVideo entry) {
			// �������ط���
			Intent intent = new Intent(ALearnApplication.this,
					DownloadService.class);
			intent.setAction(DownloadService.ACTION_ADD_TO_DOWNLOAD);
			intent.putExtra(DownloadService.EXTRA_PLAYLIST_ENTRY, entry);
			startService(intent);
		}

		// ������еĶ���
		@Override
		public ArrayList<DownloadJob> getAllDownloads() {
			ArrayList<DownloadJob> allDownloads = new ArrayList<DownloadJob>();
			allDownloads.addAll(mCompletedDownloads);
			allDownloads.addAll(mQueuedDownloads);
			return allDownloads;
		}

		// ��ȡ������ɵ�����
		@Override
		public ArrayList<DownloadJob> getCompletedDownloads() {
			return mCompletedDownloads;
		}

		// �������������е�����
		@Override
		public ArrayList<DownloadJob> getQueuedDownloads() {
			return mQueuedDownloads;
		}

		// ��ȡ������SDcard�ļ���·��
		@Override
		public String getTrackPath(OtherVideo entry) {
			if (entry == null) {
				return null;
			}

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {

				// we need to check the database to be sure whether file was
				// downloaded completely
				DownloadDatabase downloadDatabase = DownloadService
						.getDownloadDatabase();
				if (downloadDatabase != null) {
					if (!downloadDatabase.trackAvailable(entry.getVideoID()))
						return null;
				}

				// now we may give a reference to this file after we check it
				// really exists
				// in case file was somehow removed manually
				// ��ȡ�����ļ������·�����ж��Ƿ���Ĵ���
				String trackPath = DownloadHelper
						.getAbsolutePath(DownloadService.getDownloadPath());
				String fileNameVideo = DownloadHelper.getFileName(entry);
				File fileVideo = new File(trackPath, fileNameVideo);
				if (fileVideo.exists()) {
					String path = fileVideo.getAbsolutePath();
					return path;
				}
				/*
				 * String fileNameOGG = DownloadHelper.getFileName(entry); File
				 * fileOGG = new File(trackPath, fileNameOGG);
				 * if(fileOGG.exists()){ String path =
				 * fileOGG.getAbsolutePath(); return path; }
				 */
			}
			return null;
		}

	}

	public OtherVideo getmOtherVideo() {
		return mOtherVideo;
	}

	public void setmOtherVideo(OtherVideo mOtherVideo) {
		this.mOtherVideo = mOtherVideo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getPostsTid() {
		return postsTid;
	}

	public void setPostsTid(String postsTid) {
		this.postsTid = postsTid;
	}
}
