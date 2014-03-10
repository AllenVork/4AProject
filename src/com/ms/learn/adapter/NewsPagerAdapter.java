package com.ms.learn.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.ms.learn.activity.RecommendActivity;
import com.ms.learn.activity.ShowNewsActivity;
import com.ms.learn.activity.VideoDetailActivity;
import com.ms.learn.bean.NewsItem;
import com.ms.learn.bean.VideoInfo;
import com.ms.learn.widgets.RemoteImageView;

public class NewsPagerAdapter extends PagerAdapter {

	private List<View> views = null; 
	List<NewsItem>  newsPager=null;
	Context context;
	
	public NewsPagerAdapter(Context context,List<View> views,List<NewsItem>  newsPager) { 
		this.views = views; 
		this.newsPager=newsPager;
		this.context=context;
		} 
	
	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==(View)arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	    ((ViewPager) container).removeView(views.get(position)); 
		
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		
		if(position==0){
			RemoteImageView mImageView=(RemoteImageView) views.get(position);
			mImageView.setImageUrl(newsPager.get(position).getNewsImag());
			//每一次不仅下载当前的图片，也将下一个也下下来
			if(views.size()>=2){
				RemoteImageView mImageView_next=(RemoteImageView) views.get(position+1);
				mImageView_next.setImageUrl(newsPager.get(position+1).getNewsImag());
			}			
		}
		RemoteImageView mImageView=(RemoteImageView) views.get(position);
		mImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,ShowNewsActivity.class);
				intent.putExtra("newsID", newsPager.get(position).getNewsId());
				context.startActivity(intent);
			}
		});
		
		((ViewPager) container).addView(views.get(position), 0); 
	
		return views.get(position); 
	}


	

	
}
