package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Xml;
import com.ms.learn.bean.PushItem;

public class ParseXmlPush {

	/*
   	/*10-15 10:07:11.747: I/System.out(4213):   <pushInfo>
	10-15 10:07:11.747: I/System.out(4213):     <id>21</id>
	10-15 10:07:11.747: I/System.out(4213):     <uid>0</uid>
	10-15 10:07:11.747: I/System.out(4213):     <pushTime>2013-10-15T09:40:14.187</pushTime>
	10-15 10:07:11.747: I/System.out(4213):     <title>nihao</title>
	10-15 10:07:11.747: I/System.out(4213):     <state>2</state>
	10-15 10:07:11.747: I/System.out(4213):     <details>http�此◆◆◆�JIA</details>
	10-15 10:07:11.747: I/System.out(4213):   </pushInfo>*/
	

	
	private static final String PUSHINFO = "pushInfo";
	private static final String ID = "id";
	private static final String UID = "uid";
	private static final String PUSHTIME = "pushTime";
	private static final String TITLE = "title";
	private static final String STATE = "state";
	private static final String DETAILS = "details";


	public static List<PushItem> parseXmlPush(InputStream in) {

		List<PushItem> pushItems=null ;
		PushItem  pushItem=null;
		
		
		XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					pushItems = new ArrayList<PushItem>();
					
					break;
				case XmlPullParser.START_TAG:
					if(PUSHINFO.equals(xpp.getName())){
						pushItem=new PushItem();
					}
					if(pushItem!=null){
						if(ID.equals(xpp.getName())){
							pushItem.setId(xpp.nextText());
						}else if(UID.equals(xpp.getName())){
							pushItem.setuId(xpp.nextText());
						}else if(PUSHTIME.equals(xpp.getName())){
							pushItem.setPushTime(xpp.nextText());
						}else if(DETAILS.equals(xpp.getName())){
							pushItem.setDetails(xpp.nextText());
							
						}else if(TITLE.equals(xpp.getName())){
							pushItem.setTitle(xpp.nextText());
						}else if(STATE.equals(xpp.getName())){
							pushItem.setState(xpp.nextText());
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(PUSHINFO.equals(xpp.getName())){
						pushItems.add(pushItem);
						pushItem=null;
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

		return pushItems;

	}

}
