package com.ms.learn.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.RecommendListItem;
import com.ms.learn.bean.VideoInfo;
import com.ms.learn.widgets.RemoteImageView;
import com.ms.learn.widgets.ScollHoGridView;

public class ListviewAdpter extends BaseAdapter {

	DisplayMetrics metrics = new DisplayMetrics();;
	private LayoutInflater mLayoutInflater;
	private List<View> views;// viewpager view
	Context context;
	List<RecommendListItem> recommendListItems;// listview的其他item数据
	List<VideoInfo> pagerVideoInfos;// viewpager的数据
	WindowManager manager;
	final int viewType_0 = 0;
	final int viewType_other = 1;
	TextView tv_Title;

	public ListviewAdpter(Context context, List<View> PagerViews,
			List<RecommendListItem> recommendListItems,
			List<VideoInfo> pagerVideoInfos, WindowManager manager) {
		super();
		this.context = context;
		this.views = PagerViews;
		this.recommendListItems = recommendListItems;
		this.pagerVideoInfos = pagerVideoInfos;
		this.mLayoutInflater = LayoutInflater.from(context);
		this.manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
	}

	@Override
	public int getCount() {
		return recommendListItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		return recommendListItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int type = getItemViewType(position);

		if (type == 0) {
			final ImageView[] imgvDots = new ImageView[views.size()];
			// TextView tv_Title;
			View viewFirst = convertView;
			ViewHolderPager viewHolderPager;
			// LinearLayout group;
			if (viewFirst == null
					|| !(viewFirst.getTag() instanceof ViewHolderPager)) {
				viewFirst = mLayoutInflater.inflate(R.layout.lv_firstitem,
						parent, false);
				viewHolderPager = new ViewHolderPager();
				viewHolderPager.mViewPager = (ViewPager) viewFirst
						.findViewById(R.id.reconmment_viewPager);

				// 动态添加点点
				viewHolderPager.group = (LinearLayout) viewFirst
						.findViewById(R.id.viewGroup);
				for (int i = 0; i < views.size(); i++) {
					ImageView imageView = new ImageView(context);
					LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(
							15, 15);
					l.setMargins(2, 2, 2, 2);

					imgvDots[i] = imageView;
					if (i == 0) {
						imgvDots[i].setBackgroundResource(R.drawable.white_dot);
					} else {
						imgvDots[i].setBackgroundResource(R.drawable.dark_dot);
					}

					viewHolderPager.group.addView(imgvDots[i], l);
				}

				tv_Title = (TextView) viewFirst
						.findViewById(R.id.tv_recomtitle);
				tv_Title.setText(pagerVideoInfos.get(0).getVideoTitle());

				// 设置viewpager
				viewHolderPager.mViewPager.setAdapter(new PuLvPagerAdapter(
						context, views, pagerVideoInfos));
				viewHolderPager.mViewPager
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

								if (arg0 != pagerVideoInfos.size() - 1) {
									RemoteImageView mImageView_next = (RemoteImageView) views
											.get(arg0 + 1);
									mImageView_next.setImageUrl(pagerVideoInfos
											.get(arg0 + 1)
											.getVideoPagerImagUrl());
								}
								tv_Title.setText(pagerVideoInfos.get(arg0)
										.getVideoTitle());
							}

							@Override
							public void onPageScrolled(int arg0, float arg1,
									int arg2) {

							}

							@Override
							public void onPageScrollStateChanged(int arg0) {

							}
						});
				viewHolderPager.mViewPager
						.setOnTouchListener(new OnTouchListener() {

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

				viewFirst.setTag(viewHolderPager);
			} else {
				viewHolderPager = (ViewHolderPager) viewFirst.getTag();
			}

			return viewFirst;

		} else if (type == viewType_other) {
			TextView mTextView = null;
			ScollHoGridView mGallery = null;
			ViewHolderGallery holderGallery;

			View view_other = convertView;
			RecommendListItem recommendListItem = recommendListItems
					.get(position);

			if (view_other == null
					|| !(view_other.getTag() instanceof ViewHolderGallery)) {

				view_other = mLayoutInflater.inflate(R.layout.lv_recoitem,
						parent, false);
				// holderGallery=new ViewHolderGallery();
				mTextView = (TextView) view_other
						.findViewById(R.id.tv_videorecommendCategory);
				mGallery = (ScollHoGridView) view_other
						.findViewById(R.id.gallry_recommend);
				// view_other.setTag(holderGallery);
			}

			mTextView.setText(recommendListItem.getCategoryName());
			int size = recommendListItem.getVideoInfos().size();
			DisplayMetrics dm = new DisplayMetrics();

			manager.getDefaultDisplay().getMetrics(dm);
			mGallery.setAdapter(new GalleryAdapter(context, recommendListItem
					.getVideoInfos()));
			float density = dm.density;
			int allWidth = (int) (130 * size * density);
			int itemWidth = (int) (125 * density);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					allWidth + (int) (10 * density), itemWidth
							+ (int) ((45 + 15) * density));
			mGallery.setLayoutParams(params);
			mGallery.setColumnWidth(itemWidth);
			mGallery.setHorizontalSpacing(10);
			mGallery.setStretchMode(GridView.NO_STRETCH);
			mGallery.setNumColumns(size);

			return view_other;
		}
		return null;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return viewType_0;
		} else {
			return viewType_other;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	static class ViewHolderGallery {
		TextView mTextView;
		ScollHoGridView mGallery;
	}

	static class ViewHolderPager {
		LinearLayout group;
		ViewPager mViewPager;
		TextView tv_Title;
	}
}
