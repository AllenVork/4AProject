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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.activity.ExamInfoActivity;
import com.ms.learn.adapter.ExamListAdapter;
import com.ms.learn.bean.ExamListEntry;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlExamList;

public class ExamFragment  extends Fragment{
	ListView listView;
	private List<ExamListEntry>  examListEntries;
	ExamListAdapter mExamListAdapter;
	public static boolean isDid=false;
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view =getView();
		
		listView=(ListView) view.findViewById(R.id.lv_examList);
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
       return inflater.inflate(R.layout.examlist_fragment, container, false);
	}

	class GetExam extends ConnectNetAsyncTask {

		public GetExam(Context context, String url, List<NameValuePair> params,	int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			//处理结果
			if(result!=null){
				
				ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
				examListEntries=ParseXmlExamList.parseXmlExamList(stream);
				mExamListAdapter =new ExamListAdapter(examListEntries, getActivity());
				listView.setAdapter(mExamListAdapter);
			}
			
		}
		
	}
	
   private OnItemClickListener clickListener=new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		
		ExamListEntry examListEntry=mExamListAdapter.getItem(arg2);
		if(examListEntry.getExamGrade().equals("-1")){
			    String id=examListEntry.getId();
		        Intent intent=new Intent(getActivity(),ExamInfoActivity.class);
		        intent.putExtra("id", id);
		        intent.putExtra("title", examListEntry.getExamName());
		        startActivity(intent);
		}else{
			//the grade had done
			ShowToast.ShowTos(getActivity(), R.string.haddothisexma);
		}
       
        
	}
};


  @Override
  public void onResume() {
	  if(isDid){
		  getData();
	  }
	super.onResume();
  
  }
  
  private void getData(){
	  if(CheckConnectNet.isNetworkConnected(getActivity())){
			List<NameValuePair> params_Exam = new ArrayList<NameValuePair>();
			params_Exam.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
			params_Exam.add(new BasicNameValuePair("function","GetQuesList"));
			params_Exam.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
			new GetExam(getActivity(), ShareData.LANSHAOQI_ADDRESS_DOPOST, params_Exam,ConnectNetAsyncTask.GETEXAM).execute();
			
			}else{
				ShowToast.ShowTos(getActivity(), R.string.noNetwork);
			}
  }
  
	
}
