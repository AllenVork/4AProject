package com.ms.learn.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.learn.R;


	/**
	 * 自定义progressBar
	 */
	public class ProgrDialog extends LinearLayout {
		
		protected TextView mTextView;
		
		public ProgrDialog(Context context, AttributeSet attrs) {
			super(context, attrs);
			init();
		}

		public ProgrDialog(Context context) {
			super(context);
			init();
		}
		
		/**
		 * Sharable code between constructors
		 */
		private void init(){
			
			LayoutInflater.from(getContext()).inflate(R.layout.progress_bar, this);
			
			ImageView spaceshipImage = (ImageView) findViewById(R.id.ProgressBar);
			// 加载动画
			Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
					getContext(), R.anim.loading_animation);
			spaceshipImage.setAnimation(hyperspaceJumpAnimation);
			
			mTextView = (TextView)findViewById(R.id.ProgressTextView);
		}
		
		//设置内容
		public void setText(int id){
			mTextView.setText(id);
		}

}
