

package com.ms.learn.download;

/**
 * ��������״̬��
 */
public interface DownloadJobListener {
	
	/**
	 * �������
	 */
	public void downloadEnded(DownloadJob job);
	
	/**
	 * ��ʼ����
	 */
	public void downloadStarted();

}
