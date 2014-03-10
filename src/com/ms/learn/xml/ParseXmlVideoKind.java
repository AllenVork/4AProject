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

import com.ms.learn.bean.CommendItem;
import com.ms.learn.bean.VideoInfo;
import com.ms.learn.bean.VideoKind;

public class ParseXmlVideoKind {

	/*
     ArrayOfVideoType 
     <videoType>
    <pid>20</pid>
 <pname>
	 */
	private static final String ARRAYOFVIDEOTYPE = "ArrayOfVideoType";
	private static final String VIDEOTYPE = "videoType";
	
	private static final String PID = "pid";
	private static final String ID = "id";
	private static final String PNAME = "pname";


	public static List<VideoKind> parseXmlVideoKind(InputStream in) {

		List<VideoKind> videoKinds=null ;
		VideoKind  videoKind=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					videoKinds = new ArrayList<VideoKind>();
					break;
				case XmlPullParser.START_TAG:
					if(VIDEOTYPE.equals(xpp.getName())){
						videoKind=new VideoKind();
						}
					if(videoKind!=null){
						if(PID.equals(xpp.getName())){
							videoKind.setpID(xpp.nextText());
						}else if(PNAME.equals(xpp.getName())){
							videoKind.setpName(xpp.nextText());
						}else if(ID.equals(xpp.getName())){
							videoKind.setId(xpp.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if(VIDEOTYPE.equals(xpp.getName())){
						videoKinds.add(videoKind);
						videoKind=null;
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

		return videoKinds;

	}

}
