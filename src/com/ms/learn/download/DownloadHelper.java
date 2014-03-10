

package com.ms.learn.download;

import com.ms.learn.bean.OtherVideo;


/**
 * 下载帮助
 */
public class DownloadHelper {
	
	public static String getFileName(OtherVideo otherVideo){
		
		String url=otherVideo.getVideoUrl();
		String name=otherVideo.getVideoName();
		String urlLen =url.substring(url.lastIndexOf("/"));
		
		if(urlLen.contains(".")){
			return "/"+name+url.substring(url.lastIndexOf("."));
		}else{
			return "/"+name+".mp4";
		}
		
		
	}
	
	//获取服务器歌曲相对路径
	public static String getRelativePath(OtherVideo otherVideo){
		String url=otherVideo.getVideoUrl();
		return url.substring(url.lastIndexOf("/"));
	}
	//获取绝对的路径
	public static String getAbsolutePath( String destination){
		return destination;
	}
}
