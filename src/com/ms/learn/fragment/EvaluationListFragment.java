package com.ms.learn.fragment;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.activity.EvaluationDetailActivity;
import com.ms.learn.adapter.TestListAdapter;
import com.ms.learn.bean.TestEntry;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.widgets.ProgrDialog;
import com.ms.learn.xml.ParseXmlEvaluationInfo;
import com.ms.learn.xml.ParseXmlTest;

public class EvaluationListFragment  extends Fragment{
	private ListView listView;
	private ProgrDialog progrDialog;
	private TestListAdapter mTestListAdapter;
	private List<TestEntry> testEntrys=new ArrayList<TestEntry>();
	public  static final int SHOWDIALOG=1;
	String title;
	String tId;
	public  static boolean hadDone=false;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view=getView();
		listView=(ListView) view.findViewById(R.id.lv_commend);
		listView.setVisibility(View.GONE);
		listView.setOnItemClickListener(itemClickListener);
		progrDialog=(ProgrDialog) view.findViewById(R.id.comend_load);
		progrDialog.setVisibility(View.GONE);
		
		
	}
	
	

	@Override
	public void onStart() {
		 
		super.onStart();
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
       return inflater.inflate(R.layout.commen_fragment, container, false);
	}

	class GetTestsList extends ConnectNetAsyncTask{
	   public GetTestsList(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
			
		}
	   
		@Override
		public void doResult(String result) {
			
			System.out.println("+++++++"+result);
		
		    progrDialog.setVisibility(View.GONE);
		    if(result!=null&&!"113".equals(result)){
		    	ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
				testEntrys=ParseXmlTest.parseXmlTest(stream);
				mTestListAdapter=new TestListAdapter(getActivity(), testEntrys);
	           	listView.setAdapter(mTestListAdapter);
	           	listView.setVisibility(View.VISIBLE);
		    }
			
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
 
	private OnItemClickListener itemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			

			 tId=testEntrys.get(arg2).gettId().toString();
			 title=testEntrys.get(arg2).gettName();
			if(CheckConnectNet.isNetworkConnected(getActivity())){
				List<NameValuePair> params_Research = new ArrayList<NameValuePair>();
				params_Research.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
				params_Research.add(new BasicNameValuePair("function","GetTestsInfoListByTid"));
				params_Research.add(new BasicNameValuePair("tid",tId));
				new GetTestsInfoListByTid(getActivity(), ShareData.LANSHAOQI_ADDRESS_DOPOST, params_Research, ConnectNetAsyncTask.GETTESTSINFOLISTBYTID).execute();
			}else{
				ShowToast.ShowTos(getActivity(), R.string.noNetwork);
			}			
			
			
			
		}
	};
	
	class GetTestsInfoListByTid extends ConnectNetAsyncTask {

		public GetTestsInfoListByTid(Context context, String url, List<NameValuePair> params,int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			
			//处理结果
			if(result!=null){

				Intent intent=new Intent(getActivity(),EvaluationDetailActivity.class);
				intent.putExtra("result", result);
				intent.putExtra("title", title);
				intent.putExtra("tId", tId);
				startActivity(intent);
                
			}
			
		}
		
	}

	@Override
	public void onResume() {
		if(hadDone){
			getData();
			hadDone=false;
		}
		super.onResume();
	}
	
	private void getData(){
		List<NameValuePair> params_GetTestsList = new ArrayList<NameValuePair>();
		params_GetTestsList.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
	    params_GetTestsList.add(new BasicNameValuePair("function","GetTestsList"));
		new GetTestsList(getActivity(), ShareData.LANSHAOQI_ADDRESS_DOPOST, params_GetTestsList, ConnectNetAsyncTask.GETTESTSLIST).execute();
	}
	
}
