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
	// 临时保存用户信息
	private UserInfo userInfo;

	/**
	 * 下载接口
	 */
	private DownloadInterface mDownloadInterface;
	/**
	 * 存储已经下载完成的请求信息
	 */
	private ArrayList<DownloadJob> mCompletedDownloads;

	/**
	 * 存储请求下载的队列信息
	 */
	private ArrayList<DownloadJob> mQueuedDownloads;

	/**
	 * 获取图片缓存
	 * 
	 * @return
	 */
	public ImageCache getImageCache() {
		return mImageCache;
	}

	// 单例模式中获取唯一的MyApplication实例
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

	// 获取DownloadInterface对象
	public DownloadInterface getDownloadInterface() {
		if (mDownloadInterface == null) {
			mDownloadInterface = new IntentDownloadInterface();
		}
		return mDownloadInterface;
	}

	// 添加下载任务
	public void setQueuedDownloads(ArrayList<DownloadJob> mQueuedDownloads) {
		this.mQueuedDownloads = mQueuedDownloads;
	}

	// 获取下载任务
	public ArrayList<DownloadJob> getQueuedDownloads() {
		return mQueuedDownloads;
	}

	// 记录已经下载完成的任务
	public void setCompletedDownloads(ArrayList<DownloadJob> mCompletedDownloads) {
		this.mCompletedDownloads = mCompletedDownloads;
	}

	// 获取下载完成的任务
	public ArrayList<DownloadJob> getCompletedDownloads() {
		return mCompletedDownloads;
	}

	// 下载队列
	private class IntentDownloadInterface implements DownloadInterface {

		// 添加到下载队列中
		@Override
		public void addToDownloadQueue(OtherVideo entry) {
			// 启动下载服务
			Intent intent = new Intent(ALearnApplication.this,
					DownloadService.class);
			intent.setAction(DownloadService.ACTION_ADD_TO_DOWNLOAD);
			intent.putExtra(DownloadService.EXTRA_PLAYLIST_ENTRY, entry);
			startService(intent);
		}

		// 获得所有的队列
		@Override
		public ArrayList<DownloadJob> getAllDownloads() {
			ArrayList<DownloadJob> allDownloads = new ArrayList<DownloadJob>();
			allDownloads.addAll(mCompletedDownloads);
			allDownloads.addAll(mQueuedDownloads);
			return allDownloads;
		}

		// 获取下载完成的任务
		@Override
		public ArrayList<DownloadJob> getCompletedDownloads() {
			return mCompletedDownloads;
		}

		// 处于正在下载中的任务
		@Override
		public ArrayList<DownloadJob> getQueuedDownloads() {
			return mQueuedDownloads;
		}

		// 获取下载至SDcard文件的路径
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
				// 获取下载文件保存的路径，判断是否真的存在
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
