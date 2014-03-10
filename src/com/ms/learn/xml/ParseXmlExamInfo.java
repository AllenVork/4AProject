package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.ExamInfoEntry;

public class ParseXmlExamInfo {

	/*
09-29 22:07:26.746: I/System.out(22465):   <topics>
09-29 22:07:26.746: I/System.out(22465):     <topic>
09-29 22:07:26.746: I/System.out(22465):       <title>����˵����ȷ����</title>
09-29 22:07:26.746: I/System.out(22465):       <a>̫������������</a>
09-29 22:07:26.746: I/System.out(22465):       <b>�人���й��׶�</b>
09-29 22:07:26.746: I/System.out(22465):       <c>IT�ж��ǿ����</c>
09-29 22:07:26.746: I/System.out(22465):       <d>һ�����������</d>
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
	private static final String RESULT = "result";
	private static final String GRADE = "grade";


	public static List<ExamInfoEntry> parseXmlExamInfo(InputStream in) {

		List<ExamInfoEntry> examInfoEntrys=null;
		ExamInfoEntry  examInfoEntry=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					examInfoEntrys = new ArrayList<ExamInfoEntry>();
					
					break;
				case XmlPullParser.START_TAG:
					if(topic.equals(xpp.getName())){
						examInfoEntry=new ExamInfoEntry();
					}
					if(examInfoEntry!=null){
						if(TITLE.equals(xpp.getName())){
							examInfoEntry.setTitle(xpp.nextText());
						}else if(A.equals(xpp.getName())){
							examInfoEntry.setChoic_A(xpp.nextText());
						}else if(B.equals(xpp.getName())){
							examInfoEntry.setChoic_B(xpp.nextText());
						}else if(C.equals(xpp.getName())){
							examInfoEntry.setChoic_C(xpp.nextText());
							
						}else if(D.equals(xpp.getName())){
							examInfoEntry.setChoic_D(xpp.nextText());
							
						}else if(RESULT.equals(xpp.getName())){
							examInfoEntry.setAnswer(xpp.nextText());
							
						}else if(GRADE.equals(xpp.getName())){
							examInfoEntry.setGrad(xpp.nextText());
							
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(topic.equals(xpp.getName())){
						examInfoEntrys.add(examInfoEntry);
						examInfoEntry=null;
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

		return examInfoEntrys;

	}

}
