

package com.ms.learn.download;

import java.util.ArrayList;

import com.ms.learn.bean.OtherVideo;


/**
 *文件 下载接口
 */
public interface DownloadInterface {
	
	/**
	 * Add an entry to the download queue
	 * 
	 * @param entry
	 */
	public void addToDownloadQueue(OtherVideo entry);
	
	/**
	 * Returns tracks path (if is available locally, on the SD Card)
	 * 
	 * @param entry
	 * @return
	 */
	public String getTrackPath(OtherVideo entry);
	
	/**
	 * Returns complete and queued downloads
	 * 
	 * @return
	 */
	public ArrayList<DownloadJob> getAllDownloads();
	
	/**
	 * Returns queued downloads
	 * 
	 * @return
	 */
	public ArrayList<DownloadJob> getQueuedDownloads();
	
	/**
	 * Returns completed downloads
	 * 
	 * @return
	 */
	public ArrayList<DownloadJob> getCompletedDownloads();

}
