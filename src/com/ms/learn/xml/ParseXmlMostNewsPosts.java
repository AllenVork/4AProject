package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.ExamInfoEntry;
import com.ms.learn.bean.MostNewsPostsEntry;

public class ParseXmlMostNewsPosts {
	/*10-11 22:45:18.503: I/System.out(7876):   <ThemeInfo>
	10-11 22:45:18.503: I/System.out(7876):     <id>50</id>
	10-11 22:45:18.503: I/System.out(7876):     <ftype>1</ftype>
	10-11 22:45:18.503: I/System.out(7876):     <title>aaa</title>
	10-11 22:45:18.503: I/System.out(7876):     <uname>lwfl</uname>
	10-11 22:45:18.503: I/System.out(7876):     <utime>2013-10-11T20:06:18.937</utime>
	10-11 22:45:18.503: I/System.out(7876):     <readCount>5</readCount>
	10-11 22:45:18.503: I/System.out(7876):     <replyCount>0</replyCount>
	10-11 22:45:18.503: I/System.out(7876):   </ThemeInfo>*/
	
	private static final String THEMEINFO = "ThemeInfo";
	private static final String ID = "id";
	private static final String FTYPE = "ftype";
	private static final String TITLE = "title";
	private static final String UNAME = "uname";
	private static final String UTIME = "utime";
	private static final String READCOUNT = "readCount";
	private static final String REPLYCOUNT = "replyCount";


	public static List<MostNewsPostsEntry> parseXmlMostNewsPosts(InputStream in) {

		List<MostNewsPostsEntry> mostNewsPostsEntrys=null;
		MostNewsPostsEntry  mostNewsPostsEntry=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					mostNewsPostsEntrys = new ArrayList<MostNewsPostsEntry>();
					
					break;
				case XmlPullParser.START_TAG:
					if(THEMEINFO.equals(xpp.getName())){
						mostNewsPostsEntry=new MostNewsPostsEntry();
					}
					if(mostNewsPostsEntry!=null){
						if(ID.equals(xpp.getName())){
							mostNewsPostsEntry.setId(xpp.nextText());
						}else if(FTYPE.equals(xpp.getName())){
							mostNewsPostsEntry.setFtype(xpp.nextText());
						}else if(TITLE.equals(xpp.getName())){
							mostNewsPostsEntry.setTitle((xpp.nextText()));
						}else if(UNAME.equals(xpp.getName())){
							mostNewsPostsEntry.setUserName(xpp.nextText());
							
						}else if(UTIME.equals(xpp.getName())){
							mostNewsPostsEntry.setUserTime(xpp.nextText());
							
						}else if(READCOUNT.equals(xpp.getName())){
							mostNewsPostsEntry.setReadCount(xpp.nextText());
							
						}else if(REPLYCOUNT.equals(xpp.getName())){
							mostNewsPostsEntry.setReplyCount(xpp.nextText());
							
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(THEMEINFO.equals(xpp.getName())){
						mostNewsPostsEntrys.add(mostNewsPostsEntry);
						mostNewsPostsEntry=null;
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

		return mostNewsPostsEntrys;

	}

}
