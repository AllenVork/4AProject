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
import com.ms.learn.bean.VideoKindDetailItem;

public class ParseXmlKindDetail {

	/*
	 * private int videoID; private int videoTypeID; private String
	 * videoTypeName; private String videoInfo; private String videoUrl; private
	 * String videoTitle; private String videoImagUrl;
	 * 
	 *   <updatetime>2013-09-20T14:58:39.89</updatetime>

	 */
	private static final String VIDEOINFO = "videoInfo";
	private static final String ID = "id";
	private static final String IMG = "img";
	private static final String TITLE = "title";
	private static final String VIDEODES = "videoDes";
	private static final String UPDATETIME = "updatetime";

	public static List<VideoKindDetailItem> parseXmlKindDetail(InputStream in) {

		List<VideoKindDetailItem>  videoKindDetailItems=null ;
		VideoKindDetailItem mVideoKindDetailItem=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					videoKindDetailItems = new ArrayList<VideoKindDetailItem>();
					
					break;
				case XmlPullParser.START_TAG:
					if(VIDEOINFO.equals(xpp.getName())){
						mVideoKindDetailItem=new VideoKindDetailItem();
					}
					if(mVideoKindDetailItem!=null){
						if(ID.equals(xpp.getName())){
							mVideoKindDetailItem.setVideoId(xpp.nextText());
						}else if(UPDATETIME.equals(xpp.getName())){
							mVideoKindDetailItem.setVideoChangeTime(xpp.nextText());
						}else if(IMG.equals(xpp.getName())){
							mVideoKindDetailItem.setVideoImagUrl(xpp.nextText());
						}else if(TITLE.equals(xpp.getName())){
							mVideoKindDetailItem.setVideoName(xpp.nextText());
						}else if(VIDEODES.equals(xpp.getName())){
							mVideoKindDetailItem.setVideoDesc(xpp.nextText());
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(VIDEOINFO.equals(xpp.getName())){
						videoKindDetailItems.add(mVideoKindDetailItem);
						mVideoKindDetailItem=null;
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

		return videoKindDetailItems;

	}

}
