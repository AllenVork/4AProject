

package com.ms.learn.db;

import com.ms.learn.bean.OtherVideo;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * 封装对OtherVideo数据操作
 */
public class OtherVideoDatabaseBuilder extends DatabaseBuilder<OtherVideo> {

	@Override
	public OtherVideo build(Cursor query) {
		int columnName = query.getColumnIndex("video_name");
		int columnUrl = query.getColumnIndex("video_url");
		int columnId = query.getColumnIndex("video_id");
		
		OtherVideo otherVideo = new OtherVideo();
		otherVideo.setVideoID(query.getString(columnId));
		otherVideo.setVideoName(query.getString(columnName));
		otherVideo.setVideoUrl(query.getString(columnUrl));
		return otherVideo;
	}

	@Override
	public ContentValues deconstruct(OtherVideo otherVideo) {
		ContentValues values = new ContentValues();
		values.put("video_id", otherVideo.getVideoID());
		values.put("video_name", otherVideo.getVideoName());
		values.put("video_url", otherVideo.getVideoUrl());
		return values;
	}

}
