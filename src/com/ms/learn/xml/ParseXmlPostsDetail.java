package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.PostsDetailEntry;
import com.ms.learn.bean.PostsReciveEnrty;
import com.ms.learn.bean.UserInfo;

public class ParseXmlPostsDetail {
	
	
/*	10-12 11:31:08.305: I/System.out(29882): <ThemeToReply xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
	10-12 11:31:08.305: I/System.out(29882):   <id>37</id>
	10-12 11:31:08.305: I/System.out(29882):   <title>²âÊÔ£¡</title>
	10-12 11:31:08.305: I/System.out(29882):   <contentInfo>²âÊÔ</contentInfo>
	10-12 11:31:08.305: I/System.out(29882):   <ftypeid>14</ftypeid>
	10-12 11:31:08.305: I/System.out(29882):   <uid>1</uid>
	10-12 11:31:08.305: I/System.out(29882):   <uname>admin</uname>
	10-12 11:31:08.305: I/System.out(29882):   <utime>2013-09-16T17:18:03.72</utime>
	10-12 11:31:08.305: I/System.out(29882):   <ReplyList>
	10-12 11:31:08.305: I/System.out(29882):     <Reply>
	10-12 11:31:08.305: I/System.out(29882):       <id>38</id>
	10-12 11:31:08.305: I/System.out(29882):       <reply>¹þ¹þ</reply>
	10-12 11:31:08.305: I/System.out(29882):       <uid>39</uid>
	10-12 11:31:08.305: I/System.out(29882):       <uname>admin2</uname>
	10-12 11:31:08.305: I/System.out(29882):       <utime>2013-09-16T11:28:24.897</utime>
	10-12 11:31:08.305: I/System.out(29882):       <Delstate>0</Delstate>
	10-12 11:31:08.305: I/System.out(29882):     </Reply>*/

	
    private static final String CONTENTINFO="contentInfo";
    private static final String THEMETOREPLY="ThemeToReply";
    private static final String REPLYLIST="ReplyList";
    private static final String REPLY="Reply";
    private static final String ID="id";
    private static final String REPLY_LITTLE="reply";
    private static final String UID="uid";
    private static final String UNAME="uname";
    private static final String UTIME="utime";
    private static final String DELSTATE="Delstate";
    
    public static PostsDetailEntry parseXmlPostsDetail(InputStream in){
    	
    	PostsDetailEntry mPostsDetailEntry=null;
    	List<PostsReciveEnrty>  postsReciveEnrties=null;
    	PostsReciveEnrty mReciveEnrty=null;
    	XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					mPostsDetailEntry = new PostsDetailEntry();
					
					break;
				case XmlPullParser.START_TAG:
					 if(CONTENTINFO.equals(xpp.getName())){
						 
						 mPostsDetailEntry.setContentInfo(xpp.nextText());
						}else if(REPLYLIST.equals(xpp.getName())){
							postsReciveEnrties=new ArrayList<PostsReciveEnrty>();
					   }
					 
					 if(postsReciveEnrties!=null){
						 if(REPLY.equals(xpp.getName())){
							 mReciveEnrty=new PostsReciveEnrty();
						 }
						 if(mReciveEnrty!=null){
							if(ID.equals(xpp.getName())){
								 mReciveEnrty.setReplyId(xpp.nextText());
						    }else if(REPLY_LITTLE.equals(xpp.getName())){
						    	 mReciveEnrty.setReplyMsg(xpp.nextText());
						    } else if(UID.equals(xpp.getName())){
						    	 mReciveEnrty.setReplyUid(xpp.nextText());
						    } else if(UNAME.equals(xpp.getName())){
						    	 mReciveEnrty.setReplyUname(xpp.nextText());
						    } else if(UTIME.equals(xpp.getName())){
						    	 mReciveEnrty.setReplyUtime(xpp.nextText());
						    } else if(DELSTATE.equals(xpp.getName())){
						    	 mReciveEnrty.setReplyDelstate(xpp.nextText());
						    } 
							 
						 }
					 }
					
					break;
				case XmlPullParser.END_TAG:
					if(REPLY.equals(xpp.getName())){
						postsReciveEnrties.add(mReciveEnrty);
						mReciveEnrty=null;
					}
					if(THEMETOREPLY.equals(xpp.getName())){
						mPostsDetailEntry.setPostsReciveEnrties(postsReciveEnrties);
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
    	
    	return mPostsDetailEntry;
    }
    

}
