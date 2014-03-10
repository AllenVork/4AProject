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

public class ParseXmlCommend {

	/*
     ArrayOfVideoRev
     <videoRev>
    <id>20</id>
 <vid>6</vid>
   <review>’‚ «∆¿¬€1</review>
     <uid>0</uid>
    <utime>2013-09-12</utime>
  </videoRev>
  09-24 10:05:21.207: I/System.out(22155):     <uname>boy</uname>

	 */
	private static final String ARRAYOFVIDEOREV = "ArrayOfVideoRev";
	private static final String VIDEOREV = "videoRev";
	private static final String ID = "id";
	private static final String VID = "vid";
	private static final String REVIEW = "review";
	private static final String UID = "uid";
	private static final String UTIME = "utime";
	private static final String UNAME = "uname";


	public static List<CommendItem> parseXmlCommend(InputStream in) {

		List<CommendItem> commendItems=null ;
		CommendItem  commendItem=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					commendItems = new ArrayList<CommendItem>();
					
					break;
				case XmlPullParser.START_TAG:
					if(VIDEOREV.equals(xpp.getName())){
						commendItem=new CommendItem();
					}
					if(commendItem!=null){
						if(ID.equals(xpp.getName())){
							commendItem.setID(xpp.nextText());
						}else if(VID.equals(xpp.getName())){
							commendItem.setVid(xpp.nextText());
						}else if(REVIEW.equals(xpp.getName())){
							commendItem.setCommendMsg(xpp.nextText());
						}else if(UID.equals(xpp.getName())){
							commendItem.setuID(xpp.nextText());
							
						}else if(UTIME.equals(xpp.getName())){
							commendItem.setuTime(xpp.nextText());
						}else if(UNAME.equals(xpp.getName())){
							commendItem.setuName(xpp.nextText());
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(VIDEOREV.equals(xpp.getName())){
						commendItems.add(commendItem);
						commendItem=null;
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

		return commendItems;

	}

}
