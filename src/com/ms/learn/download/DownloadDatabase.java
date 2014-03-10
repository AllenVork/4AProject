

package com.ms.learn.download;

import java.util.List;

import com.ms.learn.bean.OtherVideo;
import com.ms.learn.bean.VideoHistoryEntry;


/**
 * �����������ݵ����ݿ�
 */
public interface DownloadDatabase {
	
	/**
	 * @param entry
	 * @return true if an entry already exists in the database
	 */
	public boolean addToLibrary(OtherVideo entry);
	
	/**
	 *����״̬,��������Ϊ1
	 * 
	 * @param entry
	 * @param downloaded
	 */
	public void setStatus(OtherVideo  entry, boolean downloaded); 
	
	/**
	 * ��ѯ���Ƿ����ع�
	 * 
	 * @param track
	 */
	public boolean trackAvailable(String id);
	
	//��ӻ����޸����ݵ��ۿ���¼
	public boolean addToVideoHistory(VideoHistoryEntry  historyEntry);
	
	
	//��ȡ���еĹۿ���¼����
	public List<VideoHistoryEntry> getHistoryEntries();
	
}
