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
 * �ļ��첽������
 */
public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

	DownloadJob mJob;

	public DownloadTask(DownloadJob job) {
		mJob = job;
	}

	@Override
	public void onPreExecute() {
		mJob.notifyDownloadStarted();// ���ؿ�ʼ
		super.onPreExecute();
	}

	@Override
	public Boolean doInBackground(Void... params) {

		try {
			return downloadFile(mJob);// �����ļ�
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public void onPostExecute(Boolean result) {
		mJob.notifyDownloadEnded();
		super.onPostExecute(result);
	}

	// �����ļ�����
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
				.getExternalStorageDirectory().getAbsolutePath() + "/alean/video");// ��ȡ�����ļ������·��
		String fileName = DownloadHelper.getFileName(mOtherVideo);// ��ȡ�ļ�����
		// Ŀ¼
	
		File pathFile = new File(pathDir);
		if (!pathFile.exists()) {
			// 2.����Ŀ¼��������Ӧ��������ʱ�򴴽�
			pathFile.mkdirs();
		}
		
		File secondFile=new File(pathFile+"/"+mOtherVideo.getVideoTitle());
		if (!secondFile.exists()) {
			// 2.����Ŀ¼��������Ӧ��������ʱ�򴴽�
			secondFile.mkdirs();
		}
		
		// �ļ�
		File file = new File(secondFile + fileName);
		if (!file.exists()) {
			// 3.�����ļ�
			file.createNewFile();
			
		}
		System.out.println("++++++++file++++++"+file.getAbsolutePath());
	
	
		FileOutputStream f = new FileOutputStream(file);

		InputStream in = c.getInputStream();

		byte[] buffer = new byte[1024];
		int lenght = 0;
		while ((lenght = in.read(buffer)) > 0) {
			f.write(buffer, 0, lenght);
			job.setDownloadedSize(job.getDownloadedSize() + lenght);// ���½�����
		}
		f.flush();
		in.close();
		
		return true;

	}

}
