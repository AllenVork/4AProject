

package com.ms.learn.download;

import com.ms.learn.bean.OtherVideo;


/**
 * ���ذ���
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
	
	//��ȡ�������������·��
	public static String getRelativePath(OtherVideo otherVideo){
		String url=otherVideo.getVideoUrl();
		return url.substring(url.lastIndexOf("/"));
	}
	//��ȡ���Ե�·��
	public static String getAbsolutePath( String destination){
		return destination;
	}
}
