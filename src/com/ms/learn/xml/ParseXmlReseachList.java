package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.ExamListEntry;
import com.ms.learn.bean.ResearchListEntry;

public class ParseXmlReseachList {

	/*
09-29 17:47:09.506: I/System.out(11130):   <questionnaire>
09-29 17:47:09.506: I/System.out(11130):     <id>5</id>
09-29 17:47:09.506: I/System.out(11130):     <title>µ÷²éÎÊ¾í</title>
09-29 17:47:09.506: I/System.out(11130):     <state>0</state>
09-29 17:47:09.506: I/System.out(11130):   </questionnaire>

>

	 */
	private static final String QUESTIONNAIRE = "questionnaire";
	private static final String ID = "id";
	private static final String STATE = "state";
	private static final String TITLE = "title";


	public static List<ResearchListEntry> parseXmlReseachList(InputStream in) {

		List<ResearchListEntry> researchListEntrys=null;
		ResearchListEntry  researchListEntry=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					researchListEntrys = new ArrayList<ResearchListEntry>();
					
					break;
				case XmlPullParser.START_TAG:
					if(QUESTIONNAIRE.equals(xpp.getName())){
						researchListEntry=new ResearchListEntry();
					}
					if(researchListEntry!=null){
						if(ID.equals(xpp.getName())){
							researchListEntry.setId(xpp.nextText());
						}else if(STATE.equals(xpp.getName())){
							researchListEntry.setState(xpp.nextText());
						}else if(TITLE.equals(xpp.getName())){
							researchListEntry.setResearchName(xpp.nextText());
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(QUESTIONNAIRE.equals(xpp.getName())){
						researchListEntrys.add(researchListEntry);
						researchListEntry=null;
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

		return researchListEntrys;

	}

}
