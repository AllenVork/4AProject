

package com.ms.learn.download;

import java.util.List;

import com.ms.learn.bean.OtherVideo;
import com.ms.learn.bean.VideoHistoryEntry;


/**
 * 保存下载数据的数据库
 */
public interface DownloadDatabase {
	
	/**
	 * @param entry
	 * @return true if an entry already exists in the database
	 */
	public boolean addToLibrary(OtherVideo entry);
	
	/**
	 *设置状态,下载设置为1
	 * 
	 * @param entry
	 * @param downloaded
	 */
	public void setStatus(OtherVideo  entry, boolean downloaded); 
	
	/**
	 * 查询此是否下载过
	 * 
	 * @param track
	 */
	public boolean trackAvailable(String id);
	
	//添加或者修改数据到观看记录
	public boolean addToVideoHistory(VideoHistoryEntry  historyEntry);
	
	
	//获取所有的观看记录数据
	public List<VideoHistoryEntry> getHistoryEntries();
	
}
