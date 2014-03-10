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
import com.ms.learn.bean.VideoInfo;
import com.ms.learn.bean.VideoKind;

public class ParseXmlOfficeCategory {

	/*
     ArrayOfVideoType 
     <videoType>
    <pid>20</pid>
 <pname>
	 */
	
	private static final String DOCUMENTTYPE = "documentType";
	
	private static final String PID = "pid";
	private static final String ID = "id";
	private static final String PNAME = "pname";


	public static List<OfficeCategoryItem> parseXmlOfficeCategory(InputStream in) {

		List<OfficeCategoryItem> officeCategoryItems=null ;
		OfficeCategoryItem  officeCategoryItem=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					officeCategoryItems=new ArrayList<OfficeCategoryItem>();
					break;
				case XmlPullParser.START_TAG:
					if(DOCUMENTTYPE.equals(xpp.getName())){
						   officeCategoryItem=new OfficeCategoryItem();
						}
					if(officeCategoryItem!=null){
						if(PID.equals(xpp.getName())){
							officeCategoryItem.setCategoryId(xpp.nextText());
						}else if(PNAME.equals(xpp.getName())){
							officeCategoryItem.setCategoryName(xpp.nextText());
						}else if(ID.equals(xpp.getName())){
							officeCategoryItem.setId(xpp.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if(DOCUMENTTYPE.equals(xpp.getName())){
						officeCategoryItems.add(officeCategoryItem);
						officeCategoryItem=null;
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

		return officeCategoryItems;

	}

}
