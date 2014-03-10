package com.ms.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.activity.PostsDetailActivity;
import com.ms.learn.activity.ReceivePostListActivity;
import com.ms.learn.bean.PostsReciveEnrty;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;

public  class PostsDetailAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<PostsReciveEnrty> postsReciveEnrtys=new ArrayList<PostsReciveEnrty>();
    Context context;
    private int positio;

    public PostsDetailAdapter(Context context,  List<PostsReciveEnrty> postsReciveEnrtys) {
        mInflater = LayoutInflater.from(context);
        this.postsReciveEnrtys=postsReciveEnrtys;
        this.context=context;

    }

    public int getCount() {
        return postsReciveEnrtys.size();
    }
    public Object getItem(int position) {
        return postsReciveEnrtys.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.postdetailitem_layout, parent ,false);

            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_postsTilte);
            holder.tv_userName = (TextView) convertView.findViewById(R.id.tv_postsUser);
            holder.tv_userTime = (TextView) convertView.findViewById(R.id.tv_postsTime);
            holder.bt_delete=(Button) convertView.findViewById(R.id.bt_deletePost);
           // holder.imgV = (RemoteImageView) convertView.findViewById(R.id.img_about);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PostsReciveEnrty postsReciveEnrty=postsReciveEnrtys.get(position);
        holder.tv_title.setText(postsReciveEnrty.getReplyMsg());
        String time =postsReciveEnrty.getReplyUtime();
        
        holder.tv_userTime.setText(time.replace("T", " ").substring(0, time.lastIndexOf(":")+3));
        holder.tv_userName.setText(postsReciveEnrty.getReplyUname());
        if(postsReciveEnrty.getReplyUid().equals(ALearnApplication.getInstance().getUserInfo().getUserId())){
        	holder.bt_delete.setVisibility(View.VISIBLE);
        	holder.bt_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					positio=position;
					//©ирти╬ЁЩ
					List<NameValuePair> params_Delete = new ArrayList<NameValuePair>();
					params_Delete.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
					params_Delete.add(new BasicNameValuePair("function","DeleteReply"));
					params_Delete.add(new BasicNameValuePair("rid",postsReciveEnrty.getReplyId()));
					new DeletePost(context, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_Delete, ConnectNetAsyncTask.DELETEREPLY).execute();
					
				}
			});
        }
       // holder.imgV.setDefaultImage(R.drawable.default_small);
        //обтьм╪ф╛
      //  holder.imgV.setImageUrl(galleryItems.get(position).getVideoImagUrl());
       
        return convertView;
    }

    static class ViewHolder {
        TextView tv_title,tv_userName,tv_userTime;
        Button bt_delete;
       
    }
    
    class DeletePost extends ConnectNetAsyncTask {

		public DeletePost(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			if("211".equals(result)){
				ShowToast.ShowTos(context, R.string.deletesuccess);
				ReceivePostListActivity.notifiDatachange(positio);
			}else {
				ShowToast.ShowTos(context, R.string.deletefail);
			}
			
		}
    	
    }
}