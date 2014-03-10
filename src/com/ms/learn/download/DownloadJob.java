

package com.ms.learn.download;

import com.ms.learn.bean.OtherVideo;


/**
 * ����������Ϣ
 */
public class DownloadJob {

	private OtherVideo mOtherVideo;
	private String mDestination;
	
	private DownloadTask mDownloadTask;
	private DownloadJobListener mListener;
	
	private int mProgress;
	private int mTotalSize;
	private int mDownloadedSize;
	
	private int mStartId;
	
	private String mFormat;
	
	public OtherVideo getOtherVideo() {
		return mOtherVideo;
	}

	public void setOtherVideo(OtherVideo otherVideo) {
		mOtherVideo = otherVideo;
	}

	public String getDestination() {
		return mDestination;
	}

	public void setDestination(String destination) {
		mDestination = destination;
	}

	public DownloadJob(OtherVideo otherVideo, String destination, int startId, String downloadFormat){
		mOtherVideo = otherVideo;
		mDestination = destination;
		mProgress = 0;
		mStartId = startId;
		mFormat = downloadFormat;
	}

	public void start(){
		mDownloadTask = new DownloadTask(this);//���صĽ���
		mDownloadTask.execute();
	}

	public void pause(){
		// TODO DownloadTask.pause()
	}

	public void resume(){
		// TODO DownloadTask.resume()
	}

	public void cancel(){
		// TODO DownloadTask.cancel()
	}
	
	public void setListener(DownloadJobListener listener){
		mListener = listener;
	}
	
	public int getProgress(){
		return mProgress;
	}
	
	public void setProgress(int progress){
		mProgress = progress;
	}
	
	public void setTotalSize(int totalSize) {
		this.mTotalSize = totalSize;
	}

	public int getTotalSize() {
		return mTotalSize;
	}

	//�����ļ����ؽ���
	public void setDownloadedSize(int downloadedSize) {
		this.mDownloadedSize = downloadedSize;
		mProgress = (mDownloadedSize*100)/mTotalSize;
	}

	public int getDownloadedSize() {
		return mDownloadedSize;
	}
  //��ʼ����
	public void notifyDownloadStarted(){
		if(mListener != null)
			mListener.downloadStarted();
		mProgress = 0;
	}
	//�������
	public void notifyDownloadEnded(){
		if(mListener != null)
			mListener.downloadEnded(this);
		mProgress = 100;
	}

	public void setStartId(int mStartId) {
		this.mStartId = mStartId;
	}

	public int getStartId() {
		return mStartId;
	}

	public void setFormat(String mFormat) {
		this.mFormat = mFormat;
	}

	public String getFormat() {
		return mFormat;
	}

}
