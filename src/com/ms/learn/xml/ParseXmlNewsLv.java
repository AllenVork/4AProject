package com.ms.learn.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ms.learn.bean.News;
import com.ms.learn.bean.NewsItem;

public class ParseXmlNewsLv {
	
    private static final String NEWSTOP="newstop";
    private static final String NEWINFO="newInfo";
    private static final String NEWSLISTS="newlist";
    private static final String ID="id";
    private static final String TIMEINFO="timeInfo";
    private static final String IMG="img";
    private static final String TITLE="title";
    private static final String DESCRIBE="describe";
    
    public static News  parseXmlNewsLv(InputStream in){
    	
    	List< NewsItem> newsLvItems=null;
    	NewsItem  newsItem=null;
    	List< NewsItem> newsPager=null;
    	News news=null;
    	
    	XmlPullParser xpp = Xml.newPullParser();
		try {
			xpp.setInput(in, "UTF-8");
			int event = xpp.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					news = new News();					
					break;
				case XmlPullParser.START_TAG:
					 if(NEWSTOP.equals(xpp.getName())){
						 newsPager= new ArrayList<NewsItem>();
						}
					 if(newsPager!=null){
						 if(NEWINFO.equals(xpp.getName())){
							 newsItem=new NewsItem();
						   }else if(ID.equals(xpp.getName())){
							   newsItem.setNewsId(xpp.nextText());
						   }else if(TIMEINFO.equals(xpp.getName())){
							   newsItem.setNewsTime(xpp.nextText());
						   }else if(IMG.equals(xpp.getName())){
							   newsItem.setNewsImag(xpp.nextText());
						   }else if(TITLE.equals(xpp.getName())){
							   newsItem.setNewsTitle(xpp.nextText());
						   }else if(DESCRIBE.equals(xpp.getName())){
							   newsItem.setNewsContent(xpp.nextText());
						   }
					 }
					if(NEWSLISTS.equals(xpp.getName())){
						newsLvItems=new ArrayList<NewsItem>();
					}
					if(newsLvItems!=null){
						 if(NEWINFO.equals(xpp.getName())){
							 newsItem=new NewsItem();
						   }else if(ID.equals(xpp.getName())){
							   newsItem.setNewsId(xpp.nextText());
						   }else if(TIMEINFO.equals(xpp.getName())){
							   newsItem.setNewsTime(xpp.nextText());
						   }else if(IMG.equals(xpp.getName())){
							   newsItem.setNewsImag(xpp.nextText());
						   }else if(TITLE.equals(xpp.getName())){
							   newsItem.setNewsTitle(xpp.nextText());
						   }else if(DESCRIBE.equals(xpp.getName())){
							   newsItem.setNewsContent(xpp.nextText());
						   }
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(newsPager!=null){
						if(NEWINFO.equals(xpp.getName())){
							newsPager.add(newsItem);
							newsItem=null;
						}
					}
					if(NEWSTOP.equals(xpp.getName())){
                        news.setNewsPagers(newsPager);
                        newsPager=null;
					}
					if(newsLvItems!=null){
						if(NEWINFO.equals(xpp.getName())){
							newsLvItems.add(newsItem);
							newsItem=null;
						}						
					}
					if(NEWSLISTS.equals(xpp.getName())){
						news.setNewsLvItems(newsLvItems);
						newsLvItems=null;
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
    	
    	return news;
    }
    

}
