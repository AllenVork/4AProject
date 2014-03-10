package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.activity.ShowNewsActivity.DemoJavaScriptInterface;
import com.ms.learn.activity.ShowNewsActivity.MyWebChromeClient;
import com.ms.learn.adapter.PostsDetailAdapter;
import com.ms.learn.bean.MostNewsPostsEntry;
import com.ms.learn.bean.PostsDetailEntry;
import com.ms.learn.bean.PostsReciveEnrty;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlPostsDetail;

public class PostsDetailActivity extends Activity {
	private Context mContext=PostsDetailActivity.this;
	private TextView tv_Dtailtitle,tv_postTitle,tv_user,tv_time;
	
	private Button bt_Reply,bt_showReplayList;
	static PostsDetailEntry  mPostsDetailEntry;
	private WebView mWebView;
	public  static List<PostsReciveEnrty> postsReciveEnrtys=new ArrayList<PostsReciveEnrty>();
	String html;
	 private  Handler mHandler = new Handler();
	
	MostNewsPostsEntry mostNewsPostsEntry;
	
	private  ImageGetter imageGetter=new Html.ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {              
				Drawable drawable=null;
				URL url;
				try{
					url=new URL(source);
					drawable=Drawable.createFromStream(url.openStream(),"");
			        
				}catch (Exception e) {
					e.printStackTrace();
					
					return null;
				}
				 drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				return drawable;
			}
		};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.postsdetail_activity);
		initUI();
	}

	private void initUI() {
		tv_Dtailtitle=(TextView) findViewById(R.id.tv_postsDetailTitle);
		tv_postTitle=(TextView) findViewById(R.id.tv_postsTilte);
		tv_user=(TextView) findViewById(R.id.tv_postsUser);
		tv_time=(TextView) findViewById(R.id.tv_postsTime);
		mWebView=(WebView) findViewById(R.id.web_content);
		
		mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "");

        mWebView.setWebViewClient(new MyWebChromeClient());
       
		
		bt_Reply=(Button) findViewById(R.id.bt_postRecieve);
		
		bt_Reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(PostsDetailActivity.this,RecivPostActivity.class);
				intent.putExtra("tid", mostNewsPostsEntry.getId());
				startActivity(intent);
				
				//showSendCommendDialog();
			}
		});
	;
		findViewById(R.id.bt_postsDetail).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
                PostsDetailActivity.this.finish();
				
			}
		});
		
		mostNewsPostsEntry=(MostNewsPostsEntry) getIntent().getExtras().get("mostNewsPostsEntry");
		tv_Dtailtitle.setText(R.string.posttitle);
		
		findViewById(R.id.bt_postsDetail).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		      PostsDetailActivity.this.finish();
				
			}
		});
		
		bt_showReplayList=(Button)findViewById(R.id.bt_showReceiveList);
		bt_showReplayList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(postsReciveEnrtys!=null){
					if(postsReciveEnrtys.size()!=0){
						Intent intent=new Intent(PostsDetailActivity.this,ReceivePostListActivity.class);
						startActivity(intent);
					}
				}
				
		      PostsDetailActivity.this.finish();
				
			}
		});
		
		
		List<NameValuePair> params_GetThemeReplyList = new ArrayList<NameValuePair>();
		params_GetThemeReplyList.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
		params_GetThemeReplyList.add(new BasicNameValuePair("function","GetThemeReplyList"));
		params_GetThemeReplyList.add(new BasicNameValuePair("id",mostNewsPostsEntry.getId()));
		params_GetThemeReplyList.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
		new GetThemeReplyList(PostsDetailActivity.this, ShareData.LANSHAOQI_ADDRESS_DOPOST,params_GetThemeReplyList, ConnectNetAsyncTask.GETTHEMEREPLYLIST).execute();
	}

	private class GetThemeReplyList extends ConnectNetAsyncTask{

		public GetThemeReplyList(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
          if(result!=null||!"113".equals(result)){
        	  
        	  tv_postTitle.setText(mostNewsPostsEntry.getTitle());
        	  
        	  String time =mostNewsPostsEntry.getUserTime();
              
        	  tv_time.setText("时间:"+time.replace("T", " ").substring(0, time.lastIndexOf(":")+3));
        	  tv_user.setText("楼主:"+mostNewsPostsEntry.getUserName()); 
        	  
        	  ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
        	  mPostsDetailEntry=ParseXmlPostsDetail.parseXmlPostsDetail(stream);
        	  postsReciveEnrtys =mPostsDetailEntry.getPostsReciveEnrties();
        	  
        	  bt_showReplayList.setText("查看回复("+postsReciveEnrtys.size()+")");
        	  if(postsReciveEnrtys.size()==0){
        		  bt_showReplayList.setClickable(false);
        	  }
        	 // mWebView.setText(Html.fromHtml(mPostsDetailEntry.getContentInfo(), imageGetter,null));
        	 html=mPostsDetailEntry.getContentInfo().replace("&lt;", "<").replace("&gt;", ">");
        	 html="<html>"+html+"</html>";
        	 System.out.println("++++++++html+++++++"+html);
            
            new  TextTask().execute();
          
        	  
          }	
		}
		
	}
	
	 private void showSendCommendDialog(){
	   	 LayoutInflater factory = LayoutInflater.from(mContext);
	        final View textEntryView = factory.inflate(R.layout.edit_dialog, null,false);
	      new AlertDialog.Builder(mContext)
	            .setTitle(R.string.say)
	            .setView(textEntryView)
	            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                	EditText editText=(EditText) textEntryView.findViewById(R.id.ed_commend);
	                	String text=editText.getText().toString();
	                	if(text.length()>500||text.length()==0){
	                		if(text.length()>500){
		                		ShowToast.ShowTos(mContext,R.string.limitmore);
		                		text=null;
		                	}else if(text.length()==0){
		                		ShowToast.ShowTos(mContext,R.string.pleasEnterMsg);
		                	}
	                	}else{
	                		//发送评回复	                		
	                		/*reply(回复的内容（请使用url编码后发送）)
	                		tid（要回复帖子的id）
	                		uid（用户的id*/
	                	List<NameValuePair> params_send = new ArrayList<NameValuePair>();
	                	params_send.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
	                	params_send.add(new BasicNameValuePair("function","SetReply"));
	                	params_send.add(new BasicNameValuePair("tid",mostNewsPostsEntry.getId()));
	                	params_send.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
	                	try {
							params_send.add(new BasicNameValuePair("reply",URLEncoder.encode(text, "UTF-8")));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
	            		
	            		new SetReply(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_send, ConnectNetAsyncTask.SETREPLY).execute();
	            		
	                }}
	            })
	            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {

	                     dialog.cancel();
	                }
	            })
	            .create().show();
	   	
	    }
	 
	 private class SetReply extends ConnectNetAsyncTask{

		public SetReply(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			if("211".equals(result)){
				ShowToast.ShowTos(mContext,R.string.replySuccess);
			}else if("112".equals(result)){
				ShowToast.ShowTos(mContext,R.string.replyFail);
			}else if("113".equals(result)){
				ShowToast.ShowTos(mContext, R.string.replySuccess);
			}
			
		}
		 
	 }
	   
	
	 
	 final class DemoJavaScriptInterface {

	        DemoJavaScriptInterface() {
	        }

	        /**
	         * This is not called on the UI thread. Post a runnable to invoke
	         * loadUrl on the UI thread.
	         */
	        public void clickOnAndroid() {
	            mHandler.post(new Runnable() {
	                public void run() {
	                    mWebView.loadUrl("javascript:wave()");
	                }
	            });

	        }
	    }
	 
	 final class MyWebChromeClient extends WebViewClient {

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			handler.proceed();
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			 view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			
		}
	
	    }
	 
	 class TextTask extends AsyncTask<Void, Integer, Void>{
		  
			String str_html;
			@Override
			protected Void doInBackground(Void... arg0) {
			
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

		        WebSettings webSettings = mWebView.getSettings();
		        webSettings.setSavePassword(false);
		        webSettings.setSaveFormData(false);
		        webSettings.setJavaScriptEnabled(true);
		        webSettings.setBlockNetworkImage(false);
				webSettings.setDefaultFontSize(18);
				webSettings.setSupportZoom(true);

		        mWebView.setWebViewClient(new MyWebChromeClient());
		        mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "");
		        mWebView.loadDataWithBaseURL("file://" + Environment.getExternalStorageDirectory(), html, "text/html", "utf-8", null);  
					
			}
			
		}
}
