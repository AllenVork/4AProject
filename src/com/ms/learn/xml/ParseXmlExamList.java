package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.ExamListEntry;

public class ParseXmlExamList {

	/*
09-29 16:43:58.202: I/System.out(9915):   <quesInfo>
09-29 16:43:58.202: I/System.out(9915):     <id>2</id>
09-29 16:43:58.210: I/System.out(9915):     <type>1</type>
09-29 16:43:58.210: I/System.out(9915):     <title>³õÖÐÊýÑ§</title>
09-29 16:43:58.210: I/System.out(9915):     <grade>-1</grade>
09-29 16:43:58.210: I/System.out(9915):   </quesInfo>
>

	 */
	private static final String QUESINFO = "quesInfo";
	private static final String ID = "id";
	private static final String TYPE = "type";
	private static final String TITLE = "title";
	private static final String GRADE = "grade";


	public static List<ExamListEntry> parseXmlExamList(InputStream in) {

		List<ExamListEntry> examListEntries=null;
		ExamListEntry  examListEntry=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					examListEntries = new ArrayList<ExamListEntry>();
					
					break;
				case XmlPullParser.START_TAG:
					if(QUESINFO.equals(xpp.getName())){
						examListEntry=new ExamListEntry();
					}
					if(examListEntry!=null){
						if(ID.equals(xpp.getName())){
							examListEntry.setId(xpp.nextText());
						}else if(TYPE.equals(xpp.getName())){
							examListEntry.setCatogryId(xpp.nextText());
						}else if(TITLE.equals(xpp.getName())){
							examListEntry.setExamName(xpp.nextText());
						}else if(GRADE.equals(xpp.getName())){
							examListEntry.setExamGrade(xpp.nextText());
							
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(QUESINFO.equals(xpp.getName())){
						examListEntries.add(examListEntry);
						examListEntry=null;
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

		return examListEntries;

	}

}
