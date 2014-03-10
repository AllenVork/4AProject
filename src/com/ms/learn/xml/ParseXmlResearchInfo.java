package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.ResearchInfoEntry;

public class ParseXmlResearchInfo {

	/*
09-29 22:07:26.746: I/System.out(22465):   <topics>
09-29 22:07:26.746: I/System.out(22465):     <topic>
09-29 22:07:26.746: I/System.out(22465):       <title>下面说法正确的是</title>
09-29 22:07:26.746: I/System.out(22465):       <a>太阳从西边升起</a>
09-29 22:07:26.746: I/System.out(22465):       <b>武汉是中国首都</b>
09-29 22:07:26.746: I/System.out(22465):       <c>IT男都是苦逼男</c>
09-29 22:07:26.746: I/System.out(22465):       <d>一年有五个季节</d>
09-29 22:07:26.746: I/System.out(22465):       <result>c</result>
09-29 22:07:26.746: I/System.out(22465):       <grade>2</grade>
09-29 22:07:26.746: I/System.out(22465):     </topic>


	 */
	
	private static final String TOPICS = "topics";
	private static final String topic = "topic";
	private static final String TITLE = "title";
	private static final String A = "a";
	private static final String B = "b";
	private static final String C = "c";
	private static final String D = "d";


	public static List<ResearchInfoEntry> parseXmlExamInfo(InputStream in) {

		List<ResearchInfoEntry> researchInfoEntrys=null;
		ResearchInfoEntry  researchInfoEntry=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					researchInfoEntrys = new ArrayList<ResearchInfoEntry>();
					
					break;
				case XmlPullParser.START_TAG:
					if(topic.equals(xpp.getName())){
						researchInfoEntry=new ResearchInfoEntry();
					}
					if(researchInfoEntry!=null){
						if(TITLE.equals(xpp.getName())){
							researchInfoEntry.setTitle(xpp.nextText());
						}else if(A.equals(xpp.getName())){
							researchInfoEntry.setChoic_A(xpp.nextText());
						}else if(B.equals(xpp.getName())){
							researchInfoEntry.setChoic_B(xpp.nextText());
						}else if(C.equals(xpp.getName())){
							researchInfoEntry.setChoic_C(xpp.nextText());
							
						}else if(D.equals(xpp.getName())){
							researchInfoEntry.setChoic_D(xpp.nextText());
							
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(topic.equals(xpp.getName())){
						researchInfoEntrys.add(researchInfoEntry);
						researchInfoEntry=null;
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

		return researchInfoEntrys;

	}

}
