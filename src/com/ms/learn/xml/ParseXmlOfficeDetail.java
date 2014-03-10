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
import com.ms.learn.bean.OfficeCategoryItem;
import com.ms.learn.bean.OfficeDetailItem;
import com.ms.learn.bean.VideoInfo;
import com.ms.learn.bean.VideoKind;

public class ParseXmlOfficeDetail {

	/*
     documentInfo 
     <id>
    <cid>
 <dowCount>
 <filename>
 <filedescribe>
 fileurl
 
	 */
	
	private static final String DOCUMENTINFO = "documentInfo";
	
	private static final String ID = "id";
	private static final String CID = "cid";
	private static final String DOWCOUNT = "dowCount";
	private static final String FILENAME = "filename";
	private static final String FILEDESCRIBE = "filedescribe";
	private static final String FILEURL = "fileurl";


	public static List<OfficeDetailItem> parseXmlOfficeDtail(InputStream in) {

		List<OfficeDetailItem> officeDetailItems=null ;
		OfficeDetailItem  officeDetailItem=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					officeDetailItems=new ArrayList<OfficeDetailItem>();
					break;
				case XmlPullParser.START_TAG:
					if(DOCUMENTINFO.equals(xpp.getName())){
						officeDetailItem=new OfficeDetailItem();
						}
					if(officeDetailItem!=null){
						if(ID.equals(xpp.getName())){
							officeDetailItem.setId(xpp.nextText());
						}else if(CID.equals(xpp.getName())){
							officeDetailItem.setCatetoryId(xpp.nextText());
						}else if(DOWCOUNT.equals(xpp.getName())){
							officeDetailItem.setDownCount(xpp.nextText());
						}else if(FILENAME.equals(xpp.getName())){
							officeDetailItem.setFileName(xpp.nextText());
						}else if(FILEDESCRIBE.equals(xpp.getName())){
							officeDetailItem.setFileDescribe(xpp.nextText());
						}else if(FILEURL.equals(xpp.getName())){
							officeDetailItem.setFileUrl(xpp.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if(DOCUMENTINFO.equals(xpp.getName())){
						officeDetailItems.add(officeDetailItem);
						officeDetailItem=null;
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

		return officeDetailItems;

	}

}
