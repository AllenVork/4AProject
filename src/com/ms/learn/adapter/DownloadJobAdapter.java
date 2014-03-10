

package com.ms.learn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.OtherVideo;
import com.ms.learn.download.DownloadJob;

/**
 *DownloadActivityÖÐµÄadapter
 */
public class DownloadJobAdapter extends ArrayListAdapter<DownloadJob> {

	public DownloadJobAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row=convertView;

		ViewHolder holder;

		if (row==null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			row=inflater.inflate(R.layout.download_row, null);

			holder = new ViewHolder();

			holder.videoName = (TextView)row.findViewById(R.id.videoDownName);
			holder.videoProgressText = (TextView)row.findViewById(R.id.videoDownProgress);
			holder.progressBar = (ProgressBar)row.findViewById(R.id.videoDownProgressBar);

			row.setTag(holder);
		}
		else{
			holder = (ViewHolder) row.getTag();
		}

		OtherVideo  otherVideo = mList.get(position).getOtherVideo();
		holder.videoName.setText(otherVideo.getVideoName());

		if(mList.get(position).getProgress() == 100){
			holder.progressBar.setVisibility(View.GONE);
			holder.videoProgressText.setVisibility(View.GONE);
		} else {
			holder.progressBar.setVisibility(View.VISIBLE);
			holder.progressBar.setMax(100);
			holder.progressBar.setProgress(mList.get(position).getProgress());
			holder.videoProgressText.setText(mList.get(position).getProgress()+"%");
		}

		

		return row;
	}


	static class ViewHolder {
		TextView videoName;
		TextView videoProgressText;
		ProgressBar progressBar;
	}

}
