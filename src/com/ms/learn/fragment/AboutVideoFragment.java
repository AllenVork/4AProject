package com.ms.learn.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.activity.VideoDetailActivity;
import com.ms.learn.adapter.LvAboutAdapter;
import com.ms.learn.bean.OtherVideo;

public class AboutVideoFragment  extends Fragment{
	ListView listView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view =getView();
		listView=(ListView) view.findViewById(R.id.lv_showAbout);
		List<OtherVideo> otherVideos=ALearnApplication.getInstance().getmVideoDetailInfo().getOtherVideos();
		System.out.println("otherVideos"+otherVideos.size());
		LvAboutAdapter  lvAboutAdapter=new LvAboutAdapter(getActivity(), otherVideos);
		listView.setAdapter(lvAboutAdapter);
		listView.setOnItemClickListener(lvClick);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
       return inflater.inflate(R.layout.about_fragment, container, false);
	}

	private OnItemClickListener lvClick=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			VideoDetailActivity.changeVideo(arg2);
			
		}
	};
	
}
