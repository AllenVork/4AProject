package com.ms.learn.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ScollHoGridView extends GridView {
	private boolean haveScrollbar = true;

	public ScollHoGridView(Context context) {
		super(context);
	}

	public ScollHoGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScollHoGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * �����Ƿ���ScrollBar����Ҫ��ScollView����ʾʱ��Ӧ������Ϊfalse�� Ĭ��Ϊ true
	 * 
	 * @param haveScrollbars
	 */
	public void setHaveScrollbar(boolean haveScrollbar) {
		this.haveScrollbar = haveScrollbar;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
		// MeasureSpec.AT_MOST);
		// super.onMeasure(widthMeasureSpec, expandSpec);

		if (haveScrollbar == false) {
			int expandSpec = MeasureSpec.makeMeasureSpec(
					Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
