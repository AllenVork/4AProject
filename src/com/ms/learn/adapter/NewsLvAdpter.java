package com.ms.learn.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.NewsItem;
import com.ms.learn.widgets.RemoteImageView;

public class NewsLvAdpter extends BaseAdapter {

	DisplayMetrics metrics = new DisplayMetrics();
	private LayoutInflater mLayoutInflater;
	private List<View> views;// viewpager view
	Context context;
	List<NewsItem> newsLvList;// listview的其他item数据
	List<NewsItem> newsPagers;// viewpager的数据
	private NewsPagerAdapter newsPagerAdapter;

	public NewsLvAdpter(Context context, List<View> PagerViews,
			List<NewsItem> newsLvLists, List<NewsItem> newsPager) {
		super();
		this.context = context;
		this.views = PagerViews;
		this.newsLvList = newsLvLists;
		// 添加空白项填补viewpager
		newsLvList.add(0, new NewsItem());
		this.newsPagers = newsPager;
		this.mLayoutInflater = LayoutInflater.from(context);
		this.newsPagerAdapter = new NewsPagerAdapter(context, views, newsPagers);
	}

	@Override
	public int getCount() {
		return newsLvList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return newsLvList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("position" + position);
		// 設置listView的index=0的item爲ViewPager
		if (position == 0) {
			View Item_pager = null;
			ViewPager mViewPager = null;
			LinearLayout group = null;
			final ImageView[] imgvDots = new ImageView[views.size()];
			final TextView tv_Title;

			if (Item_pager == null) {
				Item_pager = mLayoutInflater.inflate(R.layout.newslv_firstitem,
						parent, false);
			}

			mViewPager = (ViewPager) Item_pager
					.findViewById(R.id.news_viewPager);
			tv_Title = (TextView) Item_pager
					.findViewById(R.id.tv_newsPagertitle);
			tv_Title.setText(newsPagers.get(0).getNewsTitle());

			// 动态添加点点
			group = (LinearLayout) Item_pager.findViewById(R.id.newsviewGroup);
			for (int i = 0; i < views.size(); i++) {
				ImageView imageView = new ImageView(context);

				LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(10,
						10);
				l.setMargins(5, 5, 5, 5);
				// imageView.setLayoutParams(new LayoutParams(20,20));
				// imageView.setPadding(10, 10, 10, 10);

				imgvDots[i] = imageView;
				if (i == 0) {
					imgvDots[i].setBackgroundResource(R.drawable.white_dot);
				} else {
					imgvDots[i].setBackgroundResource(R.drawable.dark_dot);
				}
				group.addView(imgvDots[i], l);
			}

			// 设置viewpager,这个newsPagerAdapter里面有对每个page的点击事件
			mViewPager.setAdapter(newsPagerAdapter);
			mViewPager
					.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

						@Override
						public void onPageSelected(int arg0) {
							// 显示改变下方的点点
							for (int i = 0; i < imgvDots.length; i++) {

								imgvDots[arg0]
										.setBackgroundResource(R.drawable.white_dot);
								if (arg0 != i) {
									imgvDots[i]
											.setBackgroundResource(R.drawable.dark_dot);
								}
							}
							tv_Title.setText(newsPagers.get(arg0)
									.getNewsTitle());

							if (arg0 != newsPagers.size() -1) {
								RemoteImageView mImageView_next = (RemoteImageView) views
										.get(arg0 + 1);
								mImageView_next.setImageUrl(newsPagers.get(
										arg0 + 1).getNewsImag());
							}
						}

						@Override
						public void onPageScrolled(int arg0, float arg1,
								int arg2) {

						}

						@Override
						public void onPageScrollStateChanged(int arg0) {

						}
					});

			mViewPager.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					PointF downP = new PointF();
					PointF curP = new PointF();
					int act = arg1.getAction();
					if (act == MotionEvent.ACTION_DOWN
							|| act == MotionEvent.ACTION_MOVE
							|| act == MotionEvent.ACTION_UP) {
						((ViewGroup) arg0)
								.requestDisallowInterceptTouchEvent(true);
						if (downP.x == curP.x && downP.y == curP.y) {
							return false;
						}
					}
					return false;
				}
			});

			return Item_pager;

		} else if (position >= 1) {
			View lv_newsView = null;
			TextView mTv_title, mTv_content, mTv_time;
			if (lv_newsView == null) {
				lv_newsView = mLayoutInflater.inflate(R.layout.lv_newsitem,
						parent, false);
			}
			NewsItem news = newsLvList.get(position);
			mTv_title = (TextView) lv_newsView.findViewById(R.id.tv_newsTitle);
			mTv_content = (TextView) lv_newsView
					.findViewById(R.id.tv_newsContent);
			// mTv_time=(TextView) lv_newsView.findViewById(R.id.tv_newsTime);

			mTv_title.setText(news.getNewsTitle());
			mTv_content.setText(news.getNewsContent());
			String strTime = news.getNewsTime();
			// mTv_time.setText(news.getNewsTime().replace("T",
			// " ").substring(0, strTime.indexOf(".")));
			return lv_newsView;
		}

		return null;
	}

}
