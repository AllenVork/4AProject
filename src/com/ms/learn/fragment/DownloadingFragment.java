package com.ms.learn.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.adapter.DownloadJobAdapter;
import com.ms.learn.download.DownloadInterface;
import com.ms.learn.download.DownloadJob;

public class DownloadingFragment  extends Fragment{
	ListView listView;
	private DownloadInterface mDownloadInterface;//文件下载接口
	ArrayList<DownloadJob> jobs = null;
	  private Handler mHandler;
	
	 private Runnable mUpdateTimeTask = new Runnable() {
         public void run() {
         	updateListView();
         	mHandler.postDelayed(this, 1000);
         }
 };

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view =getView();
		listView=(ListView) view.findViewById(R.id.lv_downLoading);
		DownloadJobAdapter adapter = new DownloadJobAdapter(getActivity());
		if(jobs!=null){
			adapter.setList(jobs);
			listView.setAdapter(adapter);
		}
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDownloadInterface = ALearnApplication.getInstance().getDownloadInterface();//获取单例对象
		// 显示正在下载的文件
		jobs = mDownloadInterface.getQueuedDownloads();
		mHandler = new Handler();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
       return inflater.inflate(R.layout.downloading_frament, container, false);
	}
	
	

	@Override
	public void onResume() {
		mHandler.postDelayed(mUpdateTimeTask, 1000);
		super.onResume();
	}

	@Override
	public void onPause() {
		mHandler.removeCallbacks(mUpdateTimeTask);
		super.onPause();
	}

	private void updateListView(){
		DownloadJobAdapter adapter = (DownloadJobAdapter)listView.getAdapter();
		adapter.notifyDataSetChanged();
	}
	
}
