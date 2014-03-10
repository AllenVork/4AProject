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

import com.ms.learn.bean.RecommendListItem;
import com.ms.learn.bean.VideoInfo;

public class ParseXmlrecommendInfo {

	/*
	 * private int videoID; private int videoTypeID; private String
	 * videoTypeName; private String videoInfo; private String videoUrl; private
	 * String videoTitle; private String videoImagUrl;
	 */
	private static final String RECOMMENDVIDEOINFO = "recommendvideoInfo";
	private static final String VIDEOINFO = "videoInfo";
	private static final String ID = "id";
	private static final String VTYPE = "vtype";
	private static final String VTYPENAME = "vtypeName";
	private static final String TITLEIMG = "titleImg";
	private static final String IMG = "img";
	private static final String TITLE = "title";
	private static final String VIDEODES = "videoDes";
	private static final String PID = "pid";
	private static final String PNAME = "pname";
	private static final String VIDEOMODEL = "videoModel";

	public static List<RecommendListItem>  parseXmlrecommendInfo(InputStream in) {

	    List<RecommendListItem> mRecommendListItems = null;// lv其他的数据
	    List<VideoInfo> VideoInfos=null;
		VideoInfo mVideoInfo=null;
		RecommendListItem  mRecommendListItem=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					mRecommendListItems = new ArrayList<RecommendListItem>();
					
					break;
				case XmlPullParser.START_TAG:
				
					if(RECOMMENDVIDEOINFO.equals(xpp.getName())){
						mRecommendListItem=new RecommendListItem();
						
					}
					if(VIDEOMODEL.equals(xpp.getName())){
						VideoInfos=new ArrayList<VideoInfo>();
					}else if(VIDEOINFO.equals(xpp.getName())){
						  mVideoInfo=new VideoInfo();
					}else if(PID.equals(xpp.getName())){
						mRecommendListItem.setCategoryID(xpp.nextText());
					}else if(PNAME.equals(xpp.getName())){
						mRecommendListItem.setCategoryName(xpp.nextText());
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
						VideoInfos.add(mVideoInfo);
						mVideoInfo=null;
						
					}else if(VIDEOMODEL.equals(xpp.getName())){
						mRecommendListItem.setVideoInfos(VideoInfos);
					}else if(RECOMMENDVIDEOINFO.equals(xpp.getName())){
						mRecommendListItems.add(mRecommendListItem);
						mRecommendListItem=null;
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

		return mRecommendListItems;

	}

}
