package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.OtherVideo;
import com.ms.learn.bean.RecommendListItem;
import com.ms.learn.bean.VideoDetailInfo;
import com.ms.learn.bean.VideoInfo;

public class ParseXmlVideoDetail {

	/*
	 * private int videoID; private int videoTypeID; private String
	 * videoTypeName; private String videoInfo; private String videoUrl; private
	 * String videoTitle; private String videoImagUrl;
	 */
	private static final String VIDEOINFO = "videoInfo";
	private static final String ID = "id";
	private static final String VID = "vid";
	
	private static final String TITLE = "title";
	private static final String VIDEOITEM = "videoItem";
	private static final String VIDEOITEMS = "videoItems";
	private static final String VIDEOITEMNMAE = "videoItemNmae";
	private static final String VIDEODES = "videoDes";
	private static final String VIDEOURL = "videoUrl";
	private static final String VIDEODOWN = "vstate";
	public static VideoDetailInfo  parseXmlVideoDetail(InputStream in) {

		VideoDetailInfo videoDetailInfo = null;// lv其他的数据
	    List<OtherVideo>  otherVideos=null;
	    OtherVideo otherVideo=null;
	
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					videoDetailInfo = new VideoDetailInfo();
					
					break;
				case XmlPullParser.START_TAG:
					
					 if(ID.equals(xpp.getName())&&otherVideo==null){
						 videoDetailInfo.setVideoID(xpp.nextText());
					}else if(VIDEODES.equals(xpp.getName())){
						videoDetailInfo.setVideoDetail(xpp.nextText());
					}else if(TITLE.equals(xpp.getName())){
						videoDetailInfo.setTitleName(xpp.nextText());
					}else if(VIDEOITEMS.equals(xpp.getName())){
						otherVideos=new ArrayList<OtherVideo>();
					}else if(VIDEOITEM.equals(xpp.getName())){
						otherVideo=new OtherVideo();
					}
					 if(otherVideo!=null){
						 if(VIDEOITEMNMAE.equals(xpp.getName())){
								otherVideo.setVideoName(xpp.nextText());
							}else if(VIDEOURL.equals(xpp.getName())){
								otherVideo.setVideoUrl(xpp.nextText());
							}else if(ID.equals(xpp.getName())){
								otherVideo.setVideoID(xpp.nextText());
							}else if(VID.equals(xpp.getName())){
								otherVideo.setVideoVid(xpp.nextText());
							}else if(VIDEODOWN.equals(xpp.getName())){
								otherVideo.setVideoAllowDn(xpp.nextText());
							}
					 } 
					
					
					break;
				case XmlPullParser.END_TAG:
					if(VIDEOITEM.equals(xpp.getName())){
						otherVideos.add(otherVideo);
						otherVideo=null;
					}else if(VIDEOITEMS.equals(xpp.getName())){
						/*List<OtherVideo> videos=new ArrayList<OtherVideo>(); 
						 for (int i = 0; i < otherVideos.size(); i++) {
							  OtherVideo p = (OtherVideo) otherVideos.get(otherVideos.size()-1-i);
							  videos.add(p);
							 
						    }*/
						videoDetailInfo.setOtherVideos(otherVideos);
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

		return videoDetailInfo;

	}

}
