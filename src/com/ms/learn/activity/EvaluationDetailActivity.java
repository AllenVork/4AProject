package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.EvaluationDetailListAdapter;
import com.ms.learn.bean.EvaluationInfoEntry;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlEvaluationInfo;

public class EvaluationDetailActivity extends Activity {
	
    private Context mContext=EvaluationDetailActivity.this;
	ListView listView;
	
	EvaluationDetailListAdapter mEvaluationDetailListAdapter  ;
	List<EvaluationInfoEntry> evaluationInfoEntries;
	private TextView tv_title;
	StringBuilder sbResult=new StringBuilder();
	List<boolean[]> reList;
	Button btCommit;
	String tId;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//无title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.evaluationlist_fragment);
		initUI();
	}

	private void initUI() {
		
		
		
		tv_title=(TextView) findViewById(R.id.tv_showEvaluationTitle);
		listView=(ListView) findViewById(R.id.lv_evaluationInfo);
		doResult();
		findViewById(R.id.bt_EvaluationBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EvaluationDetailActivity.this.finish();
				
			}
		});
		
        findViewById(R.id.bt_Finish).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					commitResult();
			}
		});
		
		
	}
	
	private void doResult() {
		
		String result=getIntent().getExtras().getString("result");
		tId=getIntent().getExtras().getString("tId");
		tv_title.setText(getIntent().getExtras().getString("title").toString());
		ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
		evaluationInfoEntries =ParseXmlEvaluationInfo.parseXmlEvaluationInfo(stream);
		if(evaluationInfoEntries.size()!=0){
			 mEvaluationDetailListAdapter=new EvaluationDetailListAdapter(evaluationInfoEntries, mContext);
			 listView.setAdapter(mEvaluationDetailListAdapter);
		}else {
			ShowToast.ShowTos(mContext, R.string.noExam);
			EvaluationDetailActivity.this.finish();
		}
		
	}

	class GetTestsInfoListByTid extends ConnectNetAsyncTask {

		public GetTestsInfoListByTid(Context context, String url, List<NameValuePair> params,int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			
			//处理结果
			if(result!=null){
				if("112".equals(result)) {
					ShowToast.ShowTos(mContext, R.string.commitSuccess);
				}else{
					ShowToast.ShowTos(mContext, R.string.commitfail);
				}
				
                
			}
			
		}
		
	}
	
	
	private void commitResult(){
		   reList=mEvaluationDetailListAdapter.getReList();
		   StringBuilder builder=new StringBuilder();
		   for(int i=0;i<reList.size();i++){
			   boolean [] result=reList.get(i);
			   if(result[1]){
				   builder.append("a");
			   }
			   if(result[0]){
				   sbResult.append("true|");
			   }else{
				   sbResult.append("false|");
			   }
			 
		   }
		   if(builder.toString().trim().length()==0){
			   ShowToast.ShowTos(mContext, R.string.pleaseExam);
			   return;
		   }
		
			if(CheckConnectNet.isNetworkConnected(mContext)){
				
				String commitResuly=sbResult.toString();
				String resultList=commitResuly.substring(0, commitResuly.lastIndexOf("|"));
				List<NameValuePair> params_GetResult = new ArrayList<NameValuePair>();
				params_GetResult.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
				params_GetResult.add(new BasicNameValuePair("function","GetResult"));
				params_GetResult.add(new BasicNameValuePair("answerList",resultList));
				params_GetResult.add(new BasicNameValuePair("tid",tId));
				new GetRsult(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_GetResult, ConnectNetAsyncTask.GETRESULT).execute();
			}else{
				ShowToast.ShowTos(mContext, R.string.noNetwork);
			}
		
	}
		
			

	
	

	//提交答题结果
	private class GetRsult  extends ConnectNetAsyncTask{

		public GetRsult(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			if(result.equals("113")){
				Toast.makeText(mContext, "提交失败，请再次尝试", 3000).show();
				
			}else{
				if(result.startsWith("http")){
					
					Intent intent=new Intent(mContext,ShowEvaluationResultActivity.class);
					intent.putExtra("resultUrl", result);
					startActivity(intent);
					EvaluationDetailActivity.this.finish();
				}
			}
			
			
		}
		
	}


}
