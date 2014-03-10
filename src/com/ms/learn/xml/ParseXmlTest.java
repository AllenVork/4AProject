package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.TestEntry;

public class ParseXmlTest {

   /*10-16 17:24:41.856: I/System.out(27696):   <tests>
	10-16 17:24:41.864: I/System.out(27696):     <tid>2</tid>
	10-16 17:24:41.864: I/System.out(27696):     <testName>–ƒ¿Ì≤‚ ‘</testName>
	10-16 17:24:41.864: I/System.out(27696):   </tests>
*/
	private static final String TESTS = "tests";
	private static final String TID = "tid";
	private static final String TESTNAME = "testName";


	public static List<TestEntry> parseXmlTest(InputStream in) {

		List<TestEntry> testEntrys=null ;
		TestEntry  testEntry=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					testEntrys = new ArrayList<TestEntry>();
					
					break;
				case XmlPullParser.START_TAG:
					if(TESTS.equals(xpp.getName())){
						testEntry=new TestEntry();
					}
					if(testEntry!=null){
						if(TID.equals(xpp.getName())){
							testEntry.settId(xpp.nextText());
						}else if(TESTNAME.equals(xpp.getName())){
							testEntry.settName(xpp.nextText());
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(TESTS.equals(xpp.getName())){
						testEntrys.add(testEntry);
						testEntry=null;
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

		return testEntrys;

	}

}
