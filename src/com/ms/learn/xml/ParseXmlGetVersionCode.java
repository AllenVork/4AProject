package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.UserInfo;
import com.ms.learn.bean.VersionMsg;
import com.ms.learn.bean.VideoInfo;

public class ParseXmlGetVersionCode {
	
	 /*<apk xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
	  <VersionCode>1.02</VersionCode>
	  <ApkUrl>http://115.47.59.83/apkFile/4A.apk</ApkUrl>
	  </apk>*/
	
    private static final String VERSIONCODE="VersionCode";
    private static final String APKURL="ApkUrl";
    
    public static VersionMsg parseXml(InputStream in){
    	VersionMsg mVersionMsg=null;
    	
    	XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					mVersionMsg = new VersionMsg();					
					break;
				case XmlPullParser.START_TAG:
					 if(VERSIONCODE.equals(xpp.getName())){
						  mVersionMsg.setvCode(xpp.nextText());
						}else if(APKURL.equals(xpp.getName())){
							mVersionMsg.setvUrl(xpp.nextText());
							}
					break;
				case XmlPullParser.END_TAG:
					
					break;
				}
				event = xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return mVersionMsg;
    }
    

}
