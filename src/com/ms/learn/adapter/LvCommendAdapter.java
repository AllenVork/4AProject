package com.ms.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.bean.CommendItem;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;

public  class LvCommendAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    List<CommendItem> commendItems;
    Context context;
    String uID=ALearnApplication.getInstance().getUserInfo().getUserId();

    public LvCommendAdapter(Context mContext,  List<CommendItem> commendItems) {

        this.commendItems=commendItems;
        this.context=mContext;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);  
        commendItems.add(0,new CommendItem());

    }

    public int getCount() {
        return commendItems.size();
    }
    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
    	
    	if(0==position){
    		View view=convertView;
    		 if(view==null){
    			 view = mInflater.inflate(R.layout.edit_commend,parent,false);
    		 }
    		 view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// new CommentVideoFragment().mHandler.obtainMessage(CommentVideoFragment.SHOWDIALOG).sendToTarget();
					showSendCommendDialog();
				}
			});
    		return view;
    	}else {
    		 ViewHolder holder;

    	        if (convertView == null) {
    	            convertView = mInflater.inflate(R.layout.commenditem,parent,false);
    	            holder = new ViewHolder();
    	            holder.tv_commendMsg = (TextView) convertView.findViewById(R.id.tv_showMsg);
    	            holder.tv_commendTime = (TextView) convertView.findViewById(R.id.tv_commendTime);
    	            holder.bt_deleteCommend = (Button) convertView.findViewById(R.id.bt_deleteComm);
    	            holder.tv_commendUsername=(TextView) convertView.findViewById(R.id.tv_commendUser);

    	            convertView.setTag(holder);
    	        } else {
    	            holder = (ViewHolder) convertView.getTag();
    	        }
    	        
    	       final   CommendItem commendItem=commendItems.get(position);
    	       System.out.println("commendItem"+commendItem.getuTime());
    	        //ÏÂÔØÍ¼Æ¬
    	        holder.tv_commendMsg .setText(commendItem.getCommendMsg());
    	        holder.tv_commendTime .setText(commendItem.getuTime());
    	        holder.tv_commendUsername.setText(commendItem.getuName());
    	        if(commendItem!=null){
    	        	
    	        	if(commendItem.getuID().equals(uID)){
    	    	    	   holder.bt_deleteCommend.setVisibility(View.VISIBLE);
    	    	       }
    	        }
    	       
    	       holder.bt_deleteCommend.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
                       //É¾³ýÆÀÂÛ
						List<NameValuePair> params_Deletecommend = new ArrayList<NameValuePair>();
						params_Deletecommend.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
						params_Deletecommend.add(new BasicNameValuePair("function","DelVideoRev"));
						params_Deletecommend.add(new BasicNameValuePair("id",commendItem.getID()));
						
					    new DeleteCommend(context, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_Deletecommend, ConnectNetAsyncTask.DELETECOMMEND).execute();
					  for(int i=1;i<commendItems.size();i++){
						  if(commendItems.get(i).getID().equals(commendItem.getID())){
							  commendItems.remove(i);
							  LvCommendAdapter.this.notifyDataSetChanged();
							  break;
						  }
					  } 
					}
				});
    	        
    	        return convertView;
    	}
    }

    static class ViewHolder {
        TextView tv_commendMsg,tv_commendUsername,tv_commendTime;
        Button bt_deleteCommend;
    }
    private void showSendCommendDialog(){
   	 LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.edit_dialog, null,false);
      new AlertDialog.Builder(context)
            .setTitle(R.string.say)
            .setView(textEntryView)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	EditText editText=(EditText) textEntryView.findViewById(R.id.ed_commend);
                	String text=editText.getText().toString();
                	if(text.length()>500){
                		ShowToast.ShowTos(context,R.string.limitmore);
                		text=null;
                	}else{
                		//·¢ËÍÆÀÂÛ
                	List<NameValuePair> params_send = new ArrayList<NameValuePair>();
                	params_send.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
                	params_send.add(new BasicNameValuePair("function","SetVideoRev"));
                	params_send.add(new BasicNameValuePair("vid",ALearnApplication.getInstance().getmVideoDetailInfo().getVideoID()));
                	params_send.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
                	params_send.add(new BasicNameValuePair("txt",text));
            		
            		new SendCommendTask(context, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_send, ConnectNetAsyncTask.SENDCOMMEND).execute();
            		
                }}
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                     dialog.cancel();
                }
            })
            .create().show();
   	
    }
    
    //·¢ËÍÆÀÂÛ
    class SendCommendTask extends ConnectNetAsyncTask{

		public SendCommendTask(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			if(result.length()==3){
				int key =Integer.valueOf(result);
				switch (key) {
				case 211:
					ShowToast.ShowTos(context, R.string.commendsuccess);
					break;
               case 112:
               	ShowToast.ShowTos(context, R.string.commendfail);
					break;
               case 113:
	               System.out.println("·þÎñÆ÷´íÎó!!!!!!!!");
	              break;

				default:
					break;
				}			
			}
           
		}
   	
   }
    
    //É¾³ýÆÀÂÛ
    class DeleteCommend extends ConnectNetAsyncTask {

		public DeleteCommend(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
             int resultData=Integer.valueOf(result);
             switch (resultData) {
			 case 211:
				 //success
				ShowToast.ShowTos(context, R.string.deletecommendsuccess);
				break;
			 case 112:
			    //fail
			   ShowToast.ShowTos(context, R.string.deletecommendfail);
					break;
			 case 113:
					System.out.println("server's erro");
					break;
			default:
				break;
			}
			
		}
    	
    }
  
}