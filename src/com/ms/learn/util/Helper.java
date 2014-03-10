package com.ms.learn.util;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import com.ms.learn.ALearnApplication;
import com.ms.learn.bean.OtherVideo;
import com.ms.learn.download.DownloadInterface;

public class Helper {

	
	/**
	 * 将秒转化为string，format mm:ss e.g. 00:04:12
	 * 
	 * @param seconds
	 * @return
	 */
	public static String secondsToString(int seconds){
		int hour=seconds/3600;
		String time = null;
		if(hour>1){
			time=""+hour+":";
			 time = time+(seconds-hour*3600)/60+":";
		}else{
			 time = ""+seconds/60+":";
		}
		
		int t = seconds%60;
		time += t < 10 ? "0"+t : t; 
		return time;
	}
	
	
	public static String secondsToStringUpload(int seconds){
		int hour=seconds/3600;
		String time = null;
		if(hour>1){
			time=""+hour+":";
			 time = time+(seconds-hour*3600)/60+":";
		}else{
			int StrTime=(seconds-hour*3600)/60;
			if(StrTime<10){
				time="00:0"+StrTime+":";
			}else{
				time = "00:"+seconds/60+":";
			}
			 
		}
		
		int t = seconds%60;
		time += t < 10 ? "0"+t : t; 
		return time;
	}
	
	
	//添加下载线程到下载队列中
	public static void addToDownloads(Activity activity, OtherVideo entry){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			DownloadInterface downloadInterface = ALearnApplication.getInstance().getDownloadInterface();
			downloadInterface.addToDownloadQueue(entry);
		} else {
			
		}
	}
	
	//后去mac地址
	public static String getLocalMacAddress(Context mContext) {
		WifiManager wifi = (WifiManager)mContext. getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}
}
