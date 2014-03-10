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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.ResearchInfoListAdapter;
import com.ms.learn.bean.ResearchInfoEntry;
import com.ms.learn.fragment.ResearchFragment;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlResearchInfo;

public class ResearchInfoActivity extends FragmentActivity {
	private Context mContext=ResearchInfoActivity.this;
	private ListView mListView;
	private TextView tvTitle;
	String id;
	private Button btFinish,btBack;
	private List<ResearchInfoEntry> researchInfoEntrys;
	private ResearchInfoListAdapter researchInfoListAdapter; 
	public static List<boolean[]> reList=new ArrayList<boolean[]>();
	public static boolean isDone=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//无title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.researchinfo_activity);
		initUI();
	}

	private void initUI() {
		
		mListView=(ListView) findViewById(R.id.lv_ReserchInfo);
		btBack=(Button) findViewById(R.id.bt_ReserchInfoBack);
		btBack.setOnClickListener(btClickListener);
		btFinish=(Button) findViewById(R.id.bt_ReserchInfoFinish);
		btFinish.setOnClickListener(btClickListener);
		
	    id=getIntent().getExtras().getString("id");
		tvTitle=(TextView) findViewById(R.id.ReserchInfo_title);
		String title=getIntent().getExtras().getString("title");
		System.out.println("+++title++++++++===================================="+title);
		tvTitle.setText(title);

		if(CheckConnectNet.isNetworkConnected(mContext)){
			List<NameValuePair> params_ResearchInfo = new ArrayList<NameValuePair>();
			params_ResearchInfo.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
			params_ResearchInfo.add(new BasicNameValuePair("function","GetQuestionnaireInfo"));
			params_ResearchInfo.add(new BasicNameValuePair("id",id));
			new GetResearchInfo(ResearchInfoActivity.this, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_ResearchInfo,ConnectNetAsyncTask.GETQUESTIONNAIREINFO).execute();
			}else{
				ShowToast.ShowTos(mContext, R.string.noNetwork);
			}

	}
	
	  class GetResearchInfo extends ConnectNetAsyncTask{

		public GetResearchInfo(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			
			ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
			researchInfoEntrys=ParseXmlResearchInfo.parseXmlExamInfo(stream);
			researchInfoListAdapter=new ResearchInfoListAdapter(researchInfoEntrys, mContext);
			mListView.setAdapter(researchInfoListAdapter);
			
		}
		
	}
	  private OnClickListener btClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v==btBack){
				
				ResearchInfoActivity.this.finish();
			}else if(v==btFinish){
				if(isDone){
					commitResult();
				}else {
					Toast.makeText(ResearchInfoActivity.this, "请答题", 3000).show();
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
			if(result.equals("113")){
				System.out.println("服务器");
				
			}else if(result.equals("112")){
				ShowToast.ShowTos(mContext, R.string.commitfail);
				
			}else if(result.equals("211")){
				ShowToast.ShowTos(mContext, R.string.commitSuccess);
				btFinish.setClickable(false);
				ResearchFragment.isFinish=true;
				
				
			}
           				
		}
		
	} 
	
	private void commitResult(){
		//提交答题结果
		StringBuilder sbResult=new StringBuilder();
		  int score=0;

			//提交答题结果
	       String[] resAbcd={"a","b","c","d"};
		   for(int i=0;i<reList.size();i++){
			boolean[] res=reList.get(i);
			StringBuilder checkBRes  =new StringBuilder() ;
			   for(int j=0;j<res.length;j++)
			   {
				 if(res[j])
				 {
					   checkBRes.append(resAbcd[j]);
				     }else{
					   checkBRes.append("");
				   }
			   }
			   sbResult.append(checkBRes.toString()+"|");
			   
		   }	
		   
		   if(CheckConnectNet.isNetworkConnected(mContext)){
			   //上传数据
			   List<NameValuePair> params_Grad = new ArrayList<NameValuePair>();
			   params_Grad.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
			   params_Grad.add(new BasicNameValuePair("function","SetGrade"));
			   params_Grad.add(new BasicNameValuePair("qid",id));
			   params_Grad.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
			   params_Grad.add(new BasicNameValuePair("grade",""));
			  
			   String resR=sbResult.toString().substring(0, sbResult.toString().lastIndexOf("|"));
			   params_Grad.add(new BasicNameValuePair("gradeList",resR));
			   System.out.println("++++++++++"+id+"+++"+score+"++++"+resR+"uid"+ALearnApplication.getInstance().getUserInfo().getUserId());
			   new GetGradTask(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_Grad, ConnectNetAsyncTask.GETGRADEXAM).execute();
			   
		   }else{
			   ShowToast.ShowTos(mContext, R.string.noNetwork);
		   }
		sbResult=null;
		
	}

}
