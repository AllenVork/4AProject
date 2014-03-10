

package com.ms.learn.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.ms.learn.bean.VideoHistoryEntry;

/**
 * 封装对OtherVideo数据操作
 */
public class VideoHistoryDatabaseBuilder extends DatabaseBuilder<VideoHistoryEntry> {

	@Override
	public VideoHistoryEntry build(Cursor query) {
		int videoName = query.getColumnIndex("video_Name");
		int videoHadSeeTime= query.getColumnIndex("video_HadSeeTime");
		int videoId = query.getColumnIndex("video_Id");
		int videoWhen = query.getColumnIndex("video_When");
		int videoDescri = query.getColumnIndex("video_Descri");
		int videoUrl=query.getColumnIndex("video_Url");
		
		VideoHistoryEntry historyEntry = new VideoHistoryEntry();
		historyEntry.setVideoID(query.getString(videoId));
		historyEntry.setVideoName(query.getString(videoName));
		historyEntry.setVideoWhen(query.getString(videoWhen));
		historyEntry.setVideoHadSeeTime(query.getString(videoHadSeeTime));
		historyEntry.setVideo_Descri(query.getString(videoDescri));
		historyEntry.setVideoUrl(query.getString(videoUrl));
		return historyEntry;
	}

	@Override
	public ContentValues deconstruct(VideoHistoryEntry historyEntry) {
		ContentValues values = new ContentValues();
		values.put("video_Id", historyEntry.getVideoID());
		values.put("video_Name", historyEntry.getVideoName());
		values.put("video_HadSeeTime", historyEntry.getVideoHadSeeTime());
		values.put("video_When", historyEntry.getVideoWhen());
		values.put("video_Descri", historyEntry.getVideo_Descri());
		values.put("video_Url", historyEntry.getVideoUrl());
		return values;
	}

}
