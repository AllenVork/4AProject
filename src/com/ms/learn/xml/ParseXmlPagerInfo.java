package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.util.Xml;

import com.ms.learn.bean.VideoInfo;

public class ParseXmlPagerInfo {

	/*
	 * private int videoID; private int videoTypeID; private String
	 * videoTypeName; private String videoInfo; private String videoUrl; private
	 * String videoTitle; private String videoImagUrl;
	 */
	private static final String ARRAYOFVIDEOTYPE = "ArrayOfVideoType";
	private static final String VIDEOINFO = "videoInfo";
	private static final String ID = "id";
	private static final String VTYPE = "vtype";
	private static final String VTYPENAME = "vtypeName";
	private static final String TITLEIMG = "titleImg";
	private static final String IMG = "img";
	private static final String TITLE = "title";
	private static final String VIDEODES = "videoDes";

	public static List<VideoInfo> parseXmlPagerInfo(InputStream in) {

		List<VideoInfo> pagerVideoInfos=null ;
		VideoInfo mVideoInfo=null;
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
						pagerVideoInfos = new ArrayList<VideoInfo>();
					
					break;
				case XmlPullParser.START_TAG:
					if(VIDEOINFO.equals(xpp.getName())){
						  mVideoInfo=new VideoInfo();
					}
					if(mVideoInfo!=null){
						if(ID.equals(xpp.getName())){
							mVideoInfo.setVideoID(xpp.nextText());
						}else if(VTYPE.equals(xpp.getName())){
							mVideoInfo.setVideoTypeID(xpp.nextText());
						}else if(VTYPENAME.equals(xpp.getName())){
							mVideoInfo.setVideoTypeName(xpp.nextText());
						}else if(TITLEIMG.equals(xpp.getName())){
							mVideoInfo.setVideoPagerImagUrl(xpp.nextText());
						}else if(IMG.equals(xpp.getName())){
							mVideoInfo.setVideoImagUrl(xpp.nextText());
						}else if(TITLE.equals(xpp.getName())){
							mVideoInfo.setVideoTitle(xpp.nextText());
						}else if(VIDEODES.equals(xpp.getName())){
							mVideoInfo.setVideoInfo(xpp.nextText());
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(VIDEOINFO.equals(xpp.getName())){
						
						pagerVideoInfos.add(mVideoInfo);
						mVideoInfo=null;
					}
					break;
				}
				event = xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pagerVideoInfos;
	}
}
