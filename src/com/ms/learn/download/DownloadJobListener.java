

package com.ms.learn.download;

/**
 * 监听下载状态类
 */
public interface DownloadJobListener {
	
	/**
	 * 下载完成
	 */
	public void downloadEnded(DownloadJob job);
	
	/**
	 * 开始下载
	 */
	public void downloadStarted();

}
