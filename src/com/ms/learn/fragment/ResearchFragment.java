package com.ms.learn.fragment;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.activity.ResearchInfoActivity;
import com.ms.learn.adapter.ResearchListAdapter;
import com.ms.learn.bean.ResearchListEntry;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlReseachList;

public class ResearchFragment  extends Fragment{
	ListView listView;
	private ResearchListAdapter  mResearchListAdapter;
	List<ResearchListEntry> listEntries;
	public static boolean isFinish=false;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view =getView();
		
		listView=(ListView) view.findViewById(R.id.lv_reserchList);
		
		getData();
		listView.setOnItemClickListener(clickListener);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
       return inflater.inflate(R.layout.researchlist_fragment, container, false);
	}
	
	
   class GetResearch extends ConnectNetAsyncTask{

	public GetResearch(Context context, String url, List<NameValuePair> params,	int flag) {
		super(context, url, params, flag);
	}

	@Override
	public void doResult(String result) {
		System.out.println("+++++++++++++++"+result);
		if(result!=null){
			ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
			listEntries =ParseXmlReseachList.parseXmlReseachList(stream);
			mResearchListAdapter=new ResearchListAdapter(listEntries, getActivity());
			listView.setAdapter(mResearchListAdapter);
		}
		
	}
	   
   }
   
   private OnItemClickListener  clickListener=new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(listEntries.get(arg2).getState().equals("0")){
			String id=listEntries.get(arg2).getId();
			Intent intent=new Intent(getActivity(),ResearchInfoActivity.class);
			intent.putExtra("id", id);
			intent.putExtra("title", listEntries.get(arg2).getResearchName());
			startActivity(intent);
		}else{
			ShowToast.ShowTos(getActivity(), R.string.haddone);
		}
		
	}
};

  @Override
    public void onResume() {
	  if(isFinish){
		  getData();
		  isFinish=false;
	  }
	  super.onResume();
	  
    }
	

	private void getData(){
		if(CheckConnectNet.isNetworkConnected(getActivity())){
			List<NameValuePair> params_Research = new ArrayList<NameValuePair>();
			params_Research.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
			params_Research.add(new BasicNameValuePair("function","GetQuestionnaireList"));
			params_Research.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
			new GetResearch(getActivity(), ShareData.LANSHAOQI_ADDRESS_DOPOST, params_Research, ConnectNetAsyncTask.GETRESERCH).execute();
		}else{
			ShowToast.ShowTos(getActivity(), R.string.noNetwork);
		}
	}
}
