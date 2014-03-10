package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.ExamInfoListAdapter;
import com.ms.learn.bean.ExamInfoEntry;
import com.ms.learn.fragment.ExamFragment;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlExamInfo;

public class ExamInfoActivity extends FragmentActivity {
	private Context mContext=ExamInfoActivity.this;
	private ListView mListView;
	private List<ExamInfoEntry> examInfoEntries;
	private ExamInfoListAdapter examInfoListAdapter; 
	private TextView tvTitle ,tvExam,tv_score;;
	private Button btFinish,btBack;
	private View viewHeader;
	String id;
	int score=0;
	String finish_score; 
	 List<boolean[]> reList;
	//public static List<boolean[]> reList=new ArrayList<boolean[]>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//无title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.examinfo_activity);
		initUI();
	}

	private void initUI() {
		
		
		mListView=(ListView) findViewById(R.id.lv_ExamInfo);
		viewHeader=LayoutInflater.from(ExamInfoActivity.this).inflate(R.layout.examheader_layout, mListView,false);
		
		btBack=(Button) findViewById(R.id.bt_examInfoBack);
		btBack.setOnClickListener(btClickListener);
		btFinish=(Button) findViewById(R.id.bt_examInfoFinish);
		btFinish.setOnClickListener(btClickListener);
		
	    id=getIntent().getExtras().getString("id");
		tvTitle=(TextView) findViewById(R.id.examInfo_title);
		tvTitle.setText("开始考试");
		
		tvExam=(TextView)viewHeader.findViewById(R.id.tv_showExamTitle);
		String title=getIntent().getExtras().getString("title");
		tvExam.setText(title);
		tv_score=(TextView) viewHeader.findViewById(R.id.examInfo_score);
		
		mListView.addHeaderView(viewHeader);
		
		
		if(CheckConnectNet.isNetworkConnected(mContext)){
			List<NameValuePair> params_Exam = new ArrayList<NameValuePair>();
			params_Exam.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
			params_Exam.add(new BasicNameValuePair("function","GetQuesInfo"));
			params_Exam.add(new BasicNameValuePair("id",id));
			new GetExamInfo(ExamInfoActivity.this, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_Exam,ConnectNetAsyncTask.GETEXAMINFO).execute();
			
			}else{
				ShowToast.ShowTos(mContext, R.string.noNetwork);
			}

	}
	
	  class GetExamInfo extends ConnectNetAsyncTask{

		public GetExamInfo(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
			examInfoEntries=ParseXmlExamInfo.parseXmlExamInfo(stream);
			examInfoListAdapter=new ExamInfoListAdapter(examInfoEntries, mContext);
			mListView.setAdapter(examInfoListAdapter);
			
		}
		
	}
	  
	  private OnClickListener btClickListener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(v==btBack){
					ExamInfoActivity.this.finish();
				}else if(v==btFinish){
				   StringBuilder sbResult=new StringBuilder();
				   StringBuilder sbDoit=new StringBuilder();
				   String answer = null;
				   reList=examInfoListAdapter.getReList();
					//提交答题结果
			       String[] resAbcd={"a","b","c","d"};
				   for(int i=0;i<reList.size();i++){
				        boolean[] res=reList.get(i);
				        StringBuilder checkBRes =new StringBuilder();
					    for(int j=0;j<res.length-1;j++)
					    {
						    if(res[j])
						     {
							   checkBRes.append(resAbcd[j]);
						     }else{
							    checkBRes.append("");
						     }
					    }
					    
					    if(res[4]){
					    	sbDoit.append("1");
					    }
					   answer= checkBRes.toString();
					   System.out.println("+++++++answer+++++++"+answer);
					 
					   System.out.println("+++++++examInfoEntries.+++++++"+examInfoEntries.get(i).getAnswer());
					   if(answer.trim().toLowerCase().equals(examInfoEntries.get(i).getAnswer().toString().toLowerCase())){
						   
							   score=score+1;
							   System.out.println("+++++++score+++++++"+score);
						  
						   
						   
						  
					   }
					  
					   sbResult.append(answer+"|");
					   
				   }
				   String f_scroe=(float)score/examInfoEntries.size()*100+"";
				  finish_score=f_scroe.substring(0,f_scroe.indexOf("."));
				  System.out.println("finish_score============"+finish_score);
				   
				   
				   if(sbDoit.toString().length()!=0){
					   if(CheckConnectNet.isNetworkConnected(mContext)){
						  
						   List<NameValuePair> params_Grad = new ArrayList<NameValuePair>();
						   params_Grad.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
						   params_Grad.add(new BasicNameValuePair("function","SetGrade"));
						   params_Grad.add(new BasicNameValuePair("qid",id));
						   params_Grad.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
						   params_Grad.add(new BasicNameValuePair("grade",finish_score));
						  
						   String resR=sbResult.toString().substring(0, sbResult.toString().lastIndexOf("|"));
						   params_Grad.add(new BasicNameValuePair("gradeList",resR));
						   System.out.println("++++++++++"+id+"+++"+score+"++++"+resR+"uid"+ALearnApplication.getInstance().getUserInfo().getUserId());
						   new GetGradTask(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_Grad, ConnectNetAsyncTask.GETGRADEXAM).execute();
						   
					   }else{
						   ShowToast.ShowTos(mContext, R.string.noNetwork);
					   }
					  sbResult=null;
					 // reList.clear();
				   }else {
					   ShowToast.ShowTos(ExamInfoActivity.this, R.string.pleaseExam);
				   }
				   
				}
			}
		};
		
		private class GetGradTask  extends ConnectNetAsyncTask{

			public GetGradTask(Context context, String url,List<NameValuePair> params, int flag) {
				super(context, url, params, flag);
			}

			@Override
			public void doResult(String result) {
				if(result.equals("113")||result.equals("112")){
					ShowToast.ShowTos(ExamInfoActivity.this, R.string.commitscorefail);
					
				}else if(result.equals("211")){
					
					ShowToast.ShowTos(ExamInfoActivity.this, R.string.commitscore);
					 //上传数据
					   tv_score.setVisibility(View.VISIBLE);
					   btFinish.setVisibility(View.GONE);
					   tv_score.setText("总分："+finish_score+"分");
					   tv_score.setVisibility(View.VISIBLE);
					  
					   examInfoListAdapter.setIsVisible(true);
					   ExamFragment.isDid=true;
				}
               				
			}
			
		} 
		
		
}
