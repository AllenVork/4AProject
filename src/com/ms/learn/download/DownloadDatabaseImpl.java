

package com.ms.learn.download;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.ms.learn.bean.OtherVideo;
import com.ms.learn.bean.VideoHistoryEntry;
import com.ms.learn.db.OtherVideoDatabaseBuilder;
import com.ms.learn.db.VideoHistoryDatabaseBuilder;


public class DownloadDatabaseImpl implements DownloadDatabase {

	private String mPath;

	private static final String TABLE_LIBRARY = "alean_library";
	private static final String TABLE_VIDEOHISTORY = "video_history";


	/**
	 * Default constructor
	 * 
	 * @param path Database file
	 */
	//创建数据库
	public DownloadDatabaseImpl(String path){
		mPath = path;

		SQLiteDatabase db = getDb();
		if(db == null)
			return;
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_LIBRARY
				+ " ( _id INTEGER UNIQUE, downloaded INTEGER, "
				+ " video_id VARCHAR, video_name VARCHAR, video_url  VARCHAR);");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_VIDEOHISTORY
				+ " ( _id INTEGER UNIQUE, video_Id VARCHAR, video_Name VARCHAR, "
				+ " video_HadSeeTime VARCHAR,video_When VARCHAR,video_Descri VARCHAR,video_Url VARCHAR);");
		db.close();
	}

	private SQLiteDatabase getDb() {
	
		boolean success = (new File(Environment.getExternalStorageDirectory(),"alean")).mkdirs();
		if (success) {
			
		}
		try {
			return SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
		} catch (SQLException e) {
			return null;
		}
	}

	//保存到数据库
	@Override
	public boolean addToLibrary(OtherVideo entry) {
		int row_count=0;
		SQLiteDatabase db = getDb();

		// put playlistentry data the table
		ContentValues values = new ContentValues();

		values.put("downloaded", 0);
		values.putAll(new OtherVideoDatabaseBuilder().deconstruct(entry));

   
     
        String[] whereArgs = {""+entry.getVideoID()};
        
		row_count = db.update(TABLE_LIBRARY, values, "video_id=?", whereArgs);
		if(row_count == 0){
			db.insert(TABLE_LIBRARY, null, values);
		}

		db.close();

		return row_count != 0;
	}

	//更改是否下载的状态
	@Override
	public void setStatus(OtherVideo entry, boolean downloaded) {
		SQLiteDatabase db = getDb();

		ContentValues values = new ContentValues();
		values.put("downloaded", downloaded ? 1 : 0);
		String[] whereArgs = {""+entry.getVideoID()};
		int row_count = db.update(TABLE_LIBRARY, values, "video_id=?", whereArgs);

		if(row_count == 0){
			//失败
		}

		db.close();
	}

	//查询是否
	@Override
	public boolean trackAvailable(String id) {
		SQLiteDatabase db = getDb();
		if(db == null)
			return false;

		String[] selectionArgs = {""+id};
		
		Cursor query = db.query(TABLE_LIBRARY, null, "video_id=? and downloaded>0", selectionArgs, null, null, null);
		boolean value = query.getCount() > 0; 
		query.close();

		db.close();
		return value;
	}

	@Override
	public boolean addToVideoHistory(VideoHistoryEntry historyEntry) {
		SQLiteDatabase db = getDb();

		ContentValues values = new ContentValues();
		values.putAll(new VideoHistoryDatabaseBuilder().deconstruct(historyEntry));

		String[] whereArgs = {""+historyEntry.getVideoID()};
		
		int row_count = db.update(TABLE_VIDEOHISTORY, values, "video_Id=?", whereArgs);
		if(row_count == 0){
			db.insert(TABLE_VIDEOHISTORY, null, values);
		}

		db.close();

		return row_count != 0;
	}

	@Override
	public List<VideoHistoryEntry> getHistoryEntries() {
		
		List<VideoHistoryEntry> historyEntries=new ArrayList<VideoHistoryEntry>();
		SQLiteDatabase db = getDb();
		if(db == null)
		   return null;
		
	  Cursor cursor=	db.query(TABLE_VIDEOHISTORY, null, null, null ,null,null, null);
		
	  for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
		  
		  historyEntries.add(new VideoHistoryDatabaseBuilder().build(cursor));
		  
	  }
		
		
		return historyEntries;
	}

}
