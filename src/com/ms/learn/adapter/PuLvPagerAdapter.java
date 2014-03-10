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
import com.ms.learn.activity.VideoDetailActivity;
import com.ms.learn.bean.VideoInfo;
import com.ms.learn.widgets.RemoteImageView;

public class PuLvPagerAdapter extends PagerAdapter {

	private List<View> views = null;
	List<VideoInfo> pagerVideoInfos = null;
	Context context;

	public PuLvPagerAdapter(Context context, List<View> views,
			List<VideoInfo> pagerVideoInfos) {
		this.views = views;
		this.pagerVideoInfos = pagerVideoInfos;
		this.context = context;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (View) arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));

	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		if (position == 0) {
			RemoteImageView mImageView = (RemoteImageView) views.get(position);
			mImageView.setImageUrl(pagerVideoInfos.get(position)
					.getVideoPagerImagUrl());
			if (pagerVideoInfos.size() > 1) {
				RemoteImageView mImageView_next = (RemoteImageView) views
						.get(position + 1);
				mImageView_next.setImageUrl(pagerVideoInfos.get(position + 1)
						.getVideoPagerImagUrl());
			}
		}
		RemoteImageView mImageView = (RemoteImageView) views.get(position);
		mImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, VideoDetailActivity.class);
				intent.putExtra("videoID", pagerVideoInfos.get(position)
						.getVideoID());
				context.startActivity(intent);
			}
		});

		((ViewPager) container).addView(views.get(position), 0);

		return views.get(position);
	}
}
