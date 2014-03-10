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
 * extends ImageView，同时实现图片下载
 */
public class RemoteImageView extends ImageView{
	private Context mContext;
	
	/**
	 * 最大尝试次数
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
	 * 图片链接
	 */
	private String mUrl;
	
	/**
	 *保存 当前下载图片的链接，
	 */
	private String mCurrentlyGrabbedUrl;
	
	/**
	 * 计数下载失败次数
	 */
	private int mFailure;

	/**
	 * 在listview的位置
	 */
	private int mPosition;

	/**
	 * 需要图片的Listview
	 */
	private ListView mListView;
	
	/**
	 * 默认的图片
	 */
	private Integer mDefaultImage;

	/**
	 *设置下载路径
	 * 
	 * @param url 
	 */
	public void setImageUrl(String url){
		
		//判断下载基本条件
		if(mListView == null && mCurrentlyGrabbedUrl != null && mCurrentlyGrabbedUrl.equals(url)){
			return;
		}
		
		if(mUrl != null && mUrl.equals(url)){
			mFailure++;//记录下载次数
			if(mFailure > MAX_FAILURES){//超过之后不需要下载
				
				loadDefaultImage();
				return;
			}
		} else {
			mUrl = url;
			mFailure = 0;
		}
		
		//单例模式，获取缓存图片
		ImageCache imageCache =ALearnApplication.getInstance().getImageCache();
		
		if(imageCache.isCached(url)){//包含，直接加载			
			Drawable drawable = new BitmapDrawable(imageCache.get(url));			
			this.setBackgroundDrawable(drawable);
		}
		else {
			try{
				//下载图片
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
	 * 加载默认的图片
	 */
	private void loadDefaultImage(){
		if(mDefaultImage != null)
			//setImageResource(mDefaultImage);
		setBackgroundResource(mDefaultImage);
	}
	
	/**
	 *设置图片下载链接，图片所在Listview中的位置
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
	 * 异步下载图片
	 * 
	 */
	class DownloadTask extends AsyncTask<String, Void, String>{
		
		private String mTaskUrl;

		@Override
		public void onPreExecute() {
			loadDefaultImage();
			super.onPreExecute();
		}

		//如果SD卡的缓存中存在该图片，则将之取出放到ALearnApplication类的图片缓存（weakHashMap)中
		//否则根据URL下载然后放到SD和weakHashMap中
		@Override
		public String doInBackground(String... params) {

			mTaskUrl = params[0];
			Bitmap bmp = null;
			//从自己的缓存区获取
			String fileName=mTaskUrl.substring(mTaskUrl.lastIndexOf("/"));
			if(FileUtil.hasFileExit(mContext, fileName)){
				bmp=FileUtil.getBitmap(mContext, fileName);
				if(bmp != null){
					ALearnApplication.getInstance().getImageCache().put(mTaskUrl, bmp);//缓存图片
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
								ALearnApplication.getInstance().getImageCache().put(mTaskUrl, bmp);//缓存图片
								//保存到sdcard缓存区中
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
				RemoteImageView.this.setImageUrl(url);//重新下载图片
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
