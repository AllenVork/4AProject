package com.ms.learn.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;

public class DetailVideoFragment  extends Fragment{
	private TextView mTextView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view=getView();
		mTextView=(TextView) view.findViewById(R.id.tv_showDetail);
		mTextView.setText(ALearnApplication.getInstance().getmVideoDetailInfo().getVideoDetail());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
       return inflater.inflate(R.layout.detail_fragment, container, false);
	}

	
	
}
