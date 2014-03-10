package com.ms.learn.widgets;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.RejectedExecutionException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ListView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.util.FileUtil;
import com.ms.learn.util.ImageCache;

/**
 * extends ImageView��ͬʱʵ��ͼƬ����
 */
public class RemoteImageView extends ImageView{
	private Context mContext;
	
	/**
	 * ����Դ���
	 */
	private static int MAX_FAILURES = 5;

	public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		mContext=context;
	}

	public RemoteImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		mContext=context;
	}

	public RemoteImageView(Context context) {
		super(context);
		init();
		mContext=context;
	}


	private void init(){
	}
	
	/**
	 * ͼƬ����
	 */
	private String mUrl;
	
	/**
	 *���� ��ǰ����ͼƬ�����ӣ�
	 */
	private String mCurrentlyGrabbedUrl;
	
	/**
	 * ��������ʧ�ܴ���
	 */
	private int mFailure;

	/**
	 * ��listview��λ��
	 */
	private int mPosition;

	/**
	 * ��ҪͼƬ��Listview
	 */
	private ListView mListView;
	
	/**
	 * Ĭ�ϵ�ͼƬ
	 */
	private Integer mDefaultImage;

	/**
	 *��������·��
	 * 
	 * @param url 
	 */
	public void setImageUrl(String url){
		
		//�ж����ػ�������
		if(mListView == null && mCurrentlyGrabbedUrl != null && mCurrentlyGrabbedUrl.equals(url)){
			return;
		}
		
		if(mUrl != null && mUrl.equals(url)){
			mFailure++;//��¼���ش���
			if(mFailure > MAX_FAILURES){//����֮����Ҫ����
				
				loadDefaultImage();
				return;
			}
		} else {
			mUrl = url;
			mFailure = 0;
		}
		
		//����ģʽ����ȡ����ͼƬ
		ImageCache imageCache =ALearnApplication.getInstance().getImageCache();
		
		if(imageCache.isCached(url)){//������ֱ�Ӽ���			
			Drawable drawable = new BitmapDrawable(imageCache.get(url));			
			this.setBackgroundDrawable(drawable);
		}
		else {
			try{
				//����ͼƬ
				new DownloadTask().execute(url);
			} catch (RejectedExecutionException e) {			
			}
		} 
	}
	
	/**
	 * @param resid
	 */
	public void setDefaultImage(Integer resid){
		mDefaultImage = resid;
	}
	
	/**
	 * ����Ĭ�ϵ�ͼƬ
	 */
	private void loadDefaultImage(){
		if(mDefaultImage != null)
			//setImageResource(mDefaultImage);
		setBackgroundResource(mDefaultImage);
	}
	
	/**
	 *����ͼƬ�������ӣ�ͼƬ����Listview�е�λ��
	 * 
	 * @param url eg. http://random.com/abz.jpg
	 * @param position ListView position where the image is nested
	 * @param listView ListView to which this image belongs
	 */
	public void setImageUrl(String url, int position, ListView listView){
		mPosition = position;
		mListView = listView;
		setImageUrl(url);
	}

	/**
	 * �첽����ͼƬ
	 * 
	 */
	class DownloadTask extends AsyncTask<String, Void, String>{
		
		private String mTaskUrl;

		@Override
		public void onPreExecute() {
			loadDefaultImage();
			super.onPreExecute();
		}

		//���SD���Ļ����д��ڸ�ͼƬ����֮ȡ���ŵ�ALearnApplication���ͼƬ���棨weakHashMap)��
		//�������URL����Ȼ��ŵ�SD��weakHashMap��
		@Override
		public String doInBackground(String... params) {

			mTaskUrl = params[0];
			Bitmap bmp = null;
			//���Լ��Ļ�������ȡ
			String fileName=mTaskUrl.substring(mTaskUrl.lastIndexOf("/"));
			if(FileUtil.hasFileExit(mContext, fileName)){
				bmp=FileUtil.getBitmap(mContext, fileName);
				if(bmp != null){
					ALearnApplication.getInstance().getImageCache().put(mTaskUrl, bmp);//����ͼƬ
				  } else {
				 }
			}else{
				InputStream stream = null;
				URL imageUrl;

				try {
					imageUrl = new URL(mTaskUrl);
					try {
						stream = imageUrl.openStream();
						bmp = BitmapFactory.decodeStream(stream);
						try {
							if(bmp != null){
								ALearnApplication.getInstance().getImageCache().put(mTaskUrl, bmp);//����ͼƬ
								//���浽sdcard��������
								FileUtil.saveBitmapCache(mContext,bmp , fileName);
							} else {
							}
						} catch (NullPointerException e) {
						}
					} catch (IOException e) {
					} finally {
						try {
							if(stream != null){
								stream.close();
							}
						} catch (IOException e) {}
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			
			return mTaskUrl;
		}

		@Override
		public void onPostExecute(String url) {
			super.onPostExecute(url);
			
			// target url may change while loading
			if(!mTaskUrl.equals(mUrl))
				return;
			
			Bitmap bmp = ALearnApplication.getInstance().getImageCache().get(url);
			if(bmp == null){
				RemoteImageView.this.setImageUrl(url);//��������ͼƬ
			} else {
				// if image belongs to a list update it only if it's visible
				if(mListView != null)
					if(mPosition < mListView.getFirstVisiblePosition() || mPosition > mListView.getLastVisiblePosition())
						return;
				
				Drawable drawable = new BitmapDrawable(bmp); 
				RemoteImageView.this.setBackgroundDrawable(drawable);
				//RemoteImageView.this.setImageBitmap(bmp);
				mCurrentlyGrabbedUrl = url;
			}
		}
	};
}
