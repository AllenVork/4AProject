package com.ms.learn.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ms.learn.R;
import com.ms.learn.widgets.ProgrDialog;

public class ShowErweimaActivity extends Activity {
	private  WebView mWebView;
	 private  Handler mHandler = new Handler();
	 public    String url;
	 private static  final  int UPLOADDATA=123;
	 private ValueCallback<Uri> mUploadMessage;
	 private ProgrDialog mProgrDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.showerweima_activity);
		initUI();
	}

	private void initUI() {
		mProgrDialog=(ProgrDialog) findViewById(R.id.erweimaProgrDialog);
		mWebView=(WebView) findViewById(R.id.erweimabody);
		url=getIntent().getExtras().getString("result");
		new TextTask().execute();
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
	        webSettings.setSupportZoom(false);

	        mWebView.setWebViewClient(new MyWebChromeClient());

	        mWebView.setWebChromeClient(new WebChromeClient()  
	        {  
	               //The undocumented magic method override  
	               //Eclipse will swear at you if you try to put @Override here  
	            // For Android 3.0+
	            public void openFileChooser(ValueCallback<Uri> uploadMsg) {  

	                mUploadMessage = uploadMsg;  
	                Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
	                i.addCategory(Intent.CATEGORY_OPENABLE);  
	                i.setType("image/*");  
	                ShowErweimaActivity.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), UPLOADDATA);  

	               }

	            // For Android 3.0+
	               public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
	               mUploadMessage = uploadMsg;
	               Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	               i.addCategory(Intent.CATEGORY_OPENABLE);
	            //   i.setType("*/*");
	               i.setType("image/*");  
	               ShowErweimaActivity.this.startActivityForResult(
	               Intent.createChooser(i, "File Browser"),
	               UPLOADDATA);
	               }

	            //For Android 4.1
	               public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
	                   mUploadMessage = uploadMsg;  
	                   Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
	                   i.addCategory(Intent.CATEGORY_OPENABLE);  
	                   i.setType("image/*");  
	                   ShowErweimaActivity.this.startActivityForResult( Intent.createChooser( i, "File Chooser" ), UPLOADDATA );

	               }

	        });  


	        mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "");

	        mWebView.loadUrl(url);
			
	/*		newbody.setText(Html.fromHtml(str_html, imageGetter=new Html.ImageGetter() {
				@Override
				public Drawable getDrawable(String source) {              
					Drawable drawable=null;
					URL url;
					try{
						url=new URL(source);
						drawable=Drawable.createFromStream(url.openStream(),"");
				        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				        
					}catch (Exception e) {
						e.printStackTrace();
					}
					return drawable;
				}
			},new Html.TagHandler() {
				
				@Override
				public void handleTag(boolean opening, String tag, Editable output,
						XMLReader xmlReader) {
				 
				}
			}));*/
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
			mProgrDialog.setVisibility(view.GONE);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			mProgrDialog.setVisibility(view.VISIBLE);
			
		}
	
	    }
	 
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode,
	         Intent intent) {
	     if (requestCode == UPLOADDATA) {
	         if (null == mUploadMessage)
	             return;
	         Uri result = intent == null || resultCode != RESULT_OK ? null
	                 : intent.getData();
	         mUploadMessage.onReceiveValue(result);
	         mUploadMessage = null;

	     }
	 }
	 
	 public boolean onKeyDown(int keyCode, KeyEvent event) {       
	        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {       
	            mWebView.goBack();       
	            ShowErweimaActivity.this.finish();
	                   return true;       
	        }       
	        return super.onKeyDown(keyCode, event);       
	    }     
}
