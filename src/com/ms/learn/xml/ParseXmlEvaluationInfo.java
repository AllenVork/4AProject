package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.EvaluationInfoEntry;
import com.ms.learn.bean.ResearchInfoEntry;

public class ParseXmlEvaluationInfo {

	/*
09-30 09:21:34.106: I/System.out(15453):   <tsetInfo>
09-30 09:21:34.106: I/System.out(15453):     <id>2</id>
09-30 09:21:34.106: I/System.out(15453):     <questions />
09-30 09:21:34.106: I/System.out(15453):   </tsetInfo>



	 */
	
	private static final String TSETINFO = "tsetInfo";
	private static final String ID = "id";
	private static final String QUESTIONS = "questions";


	public static List<EvaluationInfoEntry> parseXmlEvaluationInfo(InputStream in) {

		List<EvaluationInfoEntry> evaluationInfoEntrys=null;
		EvaluationInfoEntry  evaluationInfoEntry=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					evaluationInfoEntrys = new ArrayList<EvaluationInfoEntry>();
					
					break;
				case XmlPullParser.START_TAG:
					if(TSETINFO.equals(xpp.getName())){
						evaluationInfoEntry=new EvaluationInfoEntry();
					}
					if(evaluationInfoEntry!=null){
						if(ID.equals(xpp.getName())){
							evaluationInfoEntry.setId(xpp.nextText());
						}else if(QUESTIONS.equals(xpp.getName())){
							evaluationInfoEntry.setQuestion(xpp.nextText());
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(TSETINFO.equals(xpp.getName())){
						evaluationInfoEntrys.add(evaluationInfoEntry);
						evaluationInfoEntry=null;
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

		return evaluationInfoEntrys;

	}

}
