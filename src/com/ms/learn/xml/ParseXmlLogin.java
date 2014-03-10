package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.UserInfo;
import com.ms.learn.bean.VideoInfo;

public class ParseXmlLogin {
	
    private static final String UID="id";
    private static final String USERNAME="logname";
    private static final String USERINFO="userInfo";
    private static final String TIEZISTATE="tiezistate";
    
    public static UserInfo parseXml(InputStream in){
    	UserInfo mUserInfo=null;
    	
    	XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					mUserInfo = new UserInfo();
					
					break;
				case XmlPullParser.START_TAG:
					 if(UID.equals(xpp.getName())){
						   mUserInfo.setUserId(xpp.nextText());
						}else if(USERNAME.equals(xpp.getName())){
							mUserInfo.setUserName(xpp.nextText());
					   }else if(TIEZISTATE.equals(xpp.getName())){
							mUserInfo.setTiezistate(xpp.nextText());
					   }
					
					break;
				case XmlPullParser.END_TAG:
					if(USERINFO.equals(xpp.getName())){
						
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
    	return mUserInfo;
    }
    

}
