package com.ms.learn.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;

import com.ms.learn.bean.OtherVideo;
import com.ms.learn.util.FileUtil;

/**
 * 文件异步下载类
 */
public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

	DownloadJob mJob;

	public DownloadTask(DownloadJob job) {
		mJob = job;
	}

	@Override
	public void onPreExecute() {
		mJob.notifyDownloadStarted();// 下载开始
		super.onPreExecute();
	}

	@Override
	public Boolean doInBackground(Void... params) {

		try {
			return downloadFile(mJob);// 下载文件
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public void onPostExecute(Boolean result) {
		mJob.notifyDownloadEnded();
		super.onPostExecute(result);
	}

	// 下载文件方法
	public static boolean downloadFile(DownloadJob job) throws IOException {

		// TODO rewrite to apache client

		OtherVideo mOtherVideo = job.getOtherVideo();
		String mDestination = job.getDestination();
		if(!FileUtil.sdcardIsExit()){
			return false;
		}

		URL u = new URL(mOtherVideo.getVideoUrl());
		HttpURLConnection c = (HttpURLConnection) u.openConnection();
		// c.connect();
		job.setTotalSize(c.getContentLength());
		String pathDir = DownloadHelper.getAbsolutePath(Environment
				.getExternalStorageDirectory().getAbsolutePath() + "/alean/video");// 获取下载文件保存的路径
		String fileName = DownloadHelper.getFileName(mOtherVideo);// 获取文件名称
		// 目录
	
		File pathFile = new File(pathDir);
		if (!pathFile.exists()) {
			// 2.创建目录，可以在应用启动的时候创建
			pathFile.mkdirs();
		}
		
		File secondFile=new File(pathFile+"/"+mOtherVideo.getVideoTitle());
		if (!secondFile.exists()) {
			// 2.创建目录，可以在应用启动的时候创建
			secondFile.mkdirs();
		}
		
		// 文件
		File file = new File(secondFile + fileName);
		if (!file.exists()) {
			// 3.创建文件
			file.createNewFile();
			
		}
		System.out.println("++++++++file++++++"+file.getAbsolutePath());
	
	
		FileOutputStream f = new FileOutputStream(file);

		InputStream in = c.getInputStream();

		byte[] buffer = new byte[1024];
		int lenght = 0;
		while ((lenght = in.read(buffer)) > 0) {
			f.write(buffer, 0, lenght);
			job.setDownloadedSize(job.getDownloadedSize() + lenght);// 更新进度条
		}
		f.flush();
		in.close();
		
		return true;

	}

}
