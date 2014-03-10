package com.ms.learn.fragment;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.LvCommendAdapter;
import com.ms.learn.bean.CommendItem;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.widgets.ProgrDialog;
import com.ms.learn.xml.ParseXmlCommend;

public class CommentVideoFragment  extends Fragment{
	private ListView listView;
	private ProgrDialog progrDialog;
	private LvCommendAdapter commendAdapter;
	private List<CommendItem> commendItems=new ArrayList<CommendItem>();
	public  static final int SHOWDIALOG=1;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view=getView();
		listView=(ListView) view.findViewById(R.id.lv_commend);
		listView.setVisibility(View.GONE);
		progrDialog=(ProgrDialog) view.findViewById(R.id.comend_load);
		
		   String id= ALearnApplication.getInstance().getmVideoDetailInfo().getVideoID();
		   List<NameValuePair> params_commend = new ArrayList<NameValuePair>();
		   params_commend.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
		   params_commend.add(new BasicNameValuePair("function","GetVideoRev"));
		   params_commend.add(new BasicNameValuePair("vid",id));
		   params_commend.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
		   new GetComendData(getActivity(), ShareData.LANSHAOQI_ADDRESS_DOPOST, params_commend, ConnectNetAsyncTask.GETCOMMENDDATA).execute();
	}
	
	

	@Override
	public void onStart() {
		 
		super.onStart();
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
       return inflater.inflate(R.layout.commen_fragment, container, false);
	}

	class GetComendData extends  ConnectNetAsyncTask{
	   public GetComendData(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
			
		}
	   
		@Override
		public void doResult(String result) {
		
    	    progrDialog.setVisibility(View.GONE);
    	    if(result!=null){
    	    	ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
    			commendItems=ParseXmlCommend.parseXmlCommend(stream);
               	commendAdapter=new LvCommendAdapter(getActivity(), commendItems);
               	listView.setAdapter(commendAdapter);
               	listView.setVisibility(View.VISIBLE);
    	    }
			
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
 
	
}
