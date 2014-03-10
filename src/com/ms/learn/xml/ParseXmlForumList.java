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
import com.ms.learn.bean.ForumListEntry;
import com.ms.learn.bean.OfficeCategoryItem;
import com.ms.learn.bean.VideoInfo;
import com.ms.learn.bean.VideoKind;

public class ParseXmlForumList {

	/*
    10-11 13:09:03.441: I/System.out(3838):   <forumType>
10-11 13:09:03.449: I/System.out(3838):     <id>10</id>
10-11 13:09:03.449: I/System.out(3838):     <forumTypeName>Гѕжа</forumTypeName>
10-11 13:09:03.449: I/System.out(3838):     <pid>0</pid>
10-11 13:09:03.449: I/System.out(3838):   </forumType>

	 */
	
	private static final String FORUMTYPE = "forumType";
	
	private static final String PID = "pid";
	private static final String ID = "id";
	private static final String FORUMTYPENAME = "forumTypeName";


	public static List<ForumListEntry> parseXmlForumList(InputStream in) {

		List<ForumListEntry> mForumListEntrys=null ;
		ForumListEntry mForumListEntry=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					mForumListEntrys=new ArrayList<ForumListEntry>();
					break;
				case XmlPullParser.START_TAG:
					if(FORUMTYPE.equals(xpp.getName())){
						mForumListEntry=new ForumListEntry();
						}
					if(mForumListEntry!=null){
						if(PID.equals(xpp.getName())){
							mForumListEntry.setpId(xpp.nextText());
						}else if(FORUMTYPENAME.equals(xpp.getName())){
							mForumListEntry.setForumTypeName(xpp.nextText());
						}else if(ID.equals(xpp.getName())){
							mForumListEntry.setId(xpp.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if(FORUMTYPE.equals(xpp.getName())){
						mForumListEntrys.add(mForumListEntry);
						mForumListEntry=null;
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

		return mForumListEntrys;

	}

}
