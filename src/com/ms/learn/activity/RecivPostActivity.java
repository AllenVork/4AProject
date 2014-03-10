package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.fragment.MostnewsFragment;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.PictureUtil;
import com.ms.learn.util.ShowToast;
import com.ms.learn.widgets.ProgrDialog;

public class RecivPostActivity extends Activity {
	
	private Context mContext=RecivPostActivity.this;
	private Button bt_takePictrue, bt_pictrue, bt_cancelPictrue,
			bt_commitPictrue, bt_sendpostBack, bt_sendpostComplite;
	private EditText ed_title, ed_content;
	private ImageView[] imageViews = new ImageView[4];
	// ImageView iv_one,iv_two,iv_three,iv_four;
	private String photoPath;
	private static final int MAXSIZE_W = 140;
	private static final int MAXSIZE_H = 110;
	private static final String FTP_NAME =ShareData.FTP_NAME;
	private static final String FTP_PASSWORD =ShareData.FTP_PASSWORD;

	private static final String FTP_IP = ShareData.FTP_IP;
	private static final String FTP_PORT = ShareData.FTP_PORT;
	private int takePhotoTimes = -1;
	String[] strPitrueUrl = new String[4];
	String[] strUploadPicture = new String[4];
	int[] progressBar = new int[4];
	ProgrDialog[] progrDialogs = new ProgrDialog[4];
	TextView[] tv_Uploadsuccess = new TextView[4];
	String path;
	String id;
    boolean isFinishPost=false;
	private final String photoFile = Environment.getExternalStorageDirectory()+ "/DCIM/Camera";
	private static final int CAMERA_RESULT = 0x14;
	private static final int LOCAL_PICTURE = 0x15;
	private static FTPClient mFtpClient;
	
	private static FTPClient getFtpInstans(){
		
		if(mFtpClient==null){
			 mFtpClient=new FTPClient();
		}
		
		return mFtpClient;
		
	}
	String tId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sendposts_fragment);
		initUI();
		tId=getIntent().getExtras().getString("tid");
	}

	private void initUI() {
		id = getIntent().getExtras().getString("id");
		bt_takePictrue = (Button) findViewById(R.id.bt_takePictrue);
		bt_takePictrue.setOnClickListener(btClick);
		bt_pictrue = (Button) findViewById(R.id.bt_Pictrue);
		bt_pictrue.setOnClickListener(btClick);
		bt_cancelPictrue = (Button) findViewById(R.id.bt_cancelPictrue);
		bt_cancelPictrue.setOnClickListener(btClick);
		bt_commitPictrue = (Button) findViewById(R.id.bt_commitPictrue);
		bt_commitPictrue.setOnClickListener(btClick);

		bt_sendpostBack = (Button) findViewById(R.id.bt_sendpostBack);
		bt_sendpostBack.setOnClickListener(btClick);
		bt_sendpostComplite = (Button) findViewById(R.id.bt_sendpostComplite);
		//bt_sendpostComplite.setOnClickListener(btClick);
		bt_sendpostComplite.setVisibility(View.GONE);

		ed_title = (EditText) findViewById(R.id.ed_sendPostTitle);
		ed_title.setVisibility(View.GONE);
		ed_content = (EditText) findViewById(R.id.ed_sendPostContent);

		imageViews[0] = (ImageView) findViewById(R.id.iv_one);
		imageViews[1] = (ImageView) findViewById(R.id.iv_two);
		imageViews[2] = (ImageView) findViewById(R.id.iv_three);
		imageViews[3] = (ImageView) findViewById(R.id.iv_four);

		progrDialogs[0] = (ProgrDialog) findViewById(R.id.ProgrDialog_1);
		progrDialogs[1] = (ProgrDialog) findViewById(R.id.ProgrDialog_2);
		progrDialogs[2] = (ProgrDialog) findViewById(R.id.ProgrDialog_3);
		progrDialogs[3] = (ProgrDialog) findViewById(R.id.ProgrDialog_4);

		tv_Uploadsuccess[0] = (TextView) findViewById(R.id.tv_showUploadSuccess_1);
		tv_Uploadsuccess[1] = (TextView) findViewById(R.id.tv_showUploadSuccess_2);
		tv_Uploadsuccess[2] = (TextView) findViewById(R.id.tv_showUploadSuccess_3);
		tv_Uploadsuccess[3] = (TextView) findViewById(R.id.tv_showUploadSuccess_4);
	}

	

	private View.OnClickListener btClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			if (bt_takePictrue == v) {
				if (takePhotoTimes >=3) {
					ShowToast.ShowTos(RecivPostActivity.this, R.string.more4);
				} else {
					if(isIntentAvailable(RecivPostActivity.this, MediaStore.ACTION_IMAGE_CAPTURE)){
						takePicture();
					}
					
				}

			} else if (bt_pictrue == v) {
				if (takePhotoTimes>= 3) {
					ShowToast.ShowTos(RecivPostActivity.this, R.string.more4);
				}else{
					Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(intent, LOCAL_PICTURE);
				}
				
				

			} else if (bt_cancelPictrue == v) {
				if (takePhotoTimes >= 0) {
					for (int i = 0; i <= takePhotoTimes; i++) {
						/*
						 * BitmapDrawable drawable = (BitmapDrawable)
						 * imageViews[i].getDrawable(); if(drawable != null &&
						 * drawable.getBitmap() != null){
						 * drawable.getBitmap().recycle();
						 * imageViews[i].setImageDrawable(null); }
						 */
						imageViews[i].setImageResource(R.drawable.takepictrue_bg);
						tv_Uploadsuccess[i].setVisibility(View.GONE);
					}
					takePhotoTimes = -1;
					
					
				}

			} else if (bt_commitPictrue == v) {
				if(CheckConnectNet.isNetworkConnected(RecivPostActivity.this)){
					if (takePhotoTimes >= 0) {
						   //提交文字
						   
							bt_commitPictrue.setClickable(false);
							bt_cancelPictrue.setClickable(false);
							//for(int i=0;i<=takePhotoTimes;i++){
								//System.out.println("strPitrueUrl"+strPitrueUrl[i]);
								new UploadTask(strPitrueUrl[0], getFtpInstans(), 0).execute();
								
								
							//}
							bt_takePictrue.setFocusable(false);
							bt_pictrue.setFocusable(false);
							//从第一张图片上传开始
						  //	new UploadTask(strPitrueUrl[0], ftpClient, 0).execute();
							//new UploadThread(strPitrueUrl[0], ftpClient, 0).start();
						
					}else{
						sendPostMsg();
					}

				}else{
					Toast.makeText(RecivPostActivity.this, "网络连接失败", 3000).show();
				}
				
				
			} else if (bt_sendpostBack == v) {
				RecivPostActivity.this.finish();

			} 

		}
	};

	private void takePicture() {
		    File file;
			if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
				 file = new File(photoFile);
			}else{
				ContextWrapper cw = new ContextWrapper(RecivPostActivity.this);
				
				file=new File(cw.getFilesDir().getAbsolutePath()+"/DCIM/Camera");
			}
			
			if (!file.exists()) {
				file.mkdirs();
			}
			SimpleDateFormat timeStampFormat = new SimpleDateFormat(
					"yyyyMMdd_hhmmss");
			String time=timeStampFormat.format(new Date());
			path =file.getAbsolutePath()+"/"+time+".jpg";
			
		
			System.out.println("take path"+path);
			
			Intent intent_take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			intent_take.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(path)) );
			
			startActivityForResult(intent_take, CAMERA_RESULT);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	/*	if (data == null) {
			return;
		}*/
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 2;
		

		if (requestCode == CAMERA_RESULT){
			
			if(resultCode==Activity.RESULT_OK) {
				
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				
				int degree =PictureUtil.readPictureDegree(path);
				Bitmap bitmapOld = BitmapFactory.decodeFile(path,opts);
				 if(bitmapOld!=null){
					 ++takePhotoTimes;
					 strPitrueUrl[takePhotoTimes] = path;
					 bitmap=PictureUtil.rotaingImageView(degree, bitmapOld);
					 //bitmapOld.recycle();
				 }
			}
		}else if (requestCode == LOCAL_PICTURE&&resultCode==Activity.RESULT_OK) {
			Uri uri = data.getData();
			try {
				// 读取uri所在的图片
				// bitmap =
				// MediaStore.Images.Media.getBitmap(this.getContentResolver(),
				// uri);
				
				   String[] filePathColumns={MediaStore.Images.Media.DATA};
				   Cursor c = this.getContentResolver().query(uri, filePathColumns, null,null, null);
				   c.moveToFirst();
				   int columnIndex = c.getColumnIndex(filePathColumns[0]);
				   path= c.getString(columnIndex);
				   c.close();
				   bitmap = BitmapFactory.decodeFile(path, opts);
				   if(bitmap==null){
						 Toast.makeText(RecivPostActivity.this, "无法获取此图片", 3000).show();
				   }
				   if(bitmap!=null){
						 ++takePhotoTimes;
						 strPitrueUrl[takePhotoTimes] = path;
					 }
				
				/*String[] proj = { MediaStore.Images.Media.DATA };
				  CursorLoader loader = new CursorLoader(SendPostsActivity.this, uri, proj, null, null, null);
				  Cursor actualimagecursor = loader.loadInBackground();

				//Cursor actualimagecursor = managedQuery(uri, proj, null, null,	null);

				int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

				if(actualimagecursor!=null){
					 actualimagecursor.moveToFirst();
					 path= actualimagecursor.getString(actual_image_column_index); //图片文件路径
					 System.out.println("imgPath=============="+path);
					 bitmap = BitmapFactory.decodeFile(path, opts);
					 if(bitmap==null){
						 Toast.makeText(SendPostsActivity.this, "无法获取此图片", 3000).show();
					 }
					
				}
				*/

			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		if (bitmap != null) {
			
			int oldW = bitmap.getWidth();
			int oldH = bitmap.getHeight();

			int w = Math.round((float) oldW / MAXSIZE_W); // MAX_SIZE为缩略图最大尺寸
			int h = Math.round((float) oldH / MAXSIZE_H);

			int newW = 0;
			int newH = 0;

			if (w <= 1 && h <= 1) {

				imageViews[takePhotoTimes].setImageBitmap(bitmap);
			} else {
				int i = w > h ? w : h; // 获取缩放比例

				newW = oldW / i;
				newH = oldH / i;
				Bitmap imgThumb = ThumbnailUtils.extractThumbnail(bitmap, newW,newH); // 关键代码！！
				imageViews[takePhotoTimes].setImageBitmap(imgThumb);
			}

			//bitmap.recycle();
			
		}
		

		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
    //FTP上传图片的方式
	@SuppressLint("SimpleDateFormat")
	private int ftpUpload(String path, FTPClient ftpClient, int i) {
		System.out.println("+path++++" + path);
		// 年-月-日-时-分-秒+用户id+发送的是第几张图
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String dateFile2 = dateFormat2.format(new Date());
		String fileName = dateFile2+ ALearnApplication.getInstance().getUserInfo().getUserId() + i + path.substring(path.lastIndexOf("."), path.length());
		 System.out.println("+++++++++++fileName+++++++++++++"+fileName);
		
		int returnMessage = -1;

		try {
			ftpClient.connect(FTP_IP, 21);
			boolean loginResult = ftpClient.login(FTP_NAME, FTP_PASSWORD);
			int returnCode = ftpClient.getReplyCode();
			if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// 如果登录成功
				Date date = new Date(System.currentTimeMillis());
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
				String dateFile = dateFormat.format(date);
				// 设置上传目录
				SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
				String dateFile3 = dateFormat3.format(date);
				
				
				
				String filePath = "/image/" + dateFile + "/" + dateFile3;
				boolean result=ftpClient.changeWorkingDirectory("/image");
				if(result){
					boolean creat=ftpClient.changeWorkingDirectory("/image/"+dateFile);
					System.out.println("=======creat=====+"+creat);
					if(!creat){
						boolean isSuccess=ftpClient.makeDirectory("/image/"+dateFile);
						System.out.println("=======isSuccess=====+"+isSuccess);
						if(isSuccess){
							boolean creatDir=ftpClient.changeWorkingDirectory("/image/"+dateFile+"/"+dateFile3);
							if(!creatDir){
								boolean creatDirSecond=ftpClient.makeDirectory("/image/"+dateFile+"/"+dateFile3);
								System.out.println("=======creatDirSecond=====+"+creatDirSecond);
							
							}
						}
					}else{
						
							boolean creatDir=ftpClient.changeWorkingDirectory("/image/"+dateFile+"/"+dateFile3);
							System.out.println("=======creatDir=====+"+creatDir);
							if(!creatDir){
								boolean creatDirSecond=ftpClient.makeDirectory("/image/"+dateFile+"/"+dateFile3);
								System.out.println("=======creatDirSecond=====+"+creatDirSecond);
							}{
								
							}
					}
				}
				
				
				ftpClient.changeWorkingDirectory(filePath);
				ftpClient.setBufferSize(1024);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
				ftpClient.setControlEncoding("UTF-8");
				ftpClient.enterLocalPassiveMode();
				
				
				
				 BitmapFactory.Options newOpts = new BitmapFactory.Options();  
			        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
			     newOpts.inJustDecodeBounds = true;  
			     Bitmap bitmap = BitmapFactory.decodeFile(path,newOpts);//此时返回bm为空  
			          
			     newOpts.inJustDecodeBounds = false;  
			     int w = newOpts.outWidth;  
			     int h = newOpts.outHeight;  
			        
			     float hh = 300f;
			     float ww = 320f;
			    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
			     int be = 1;//be=1表示不缩放  
			     if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
			            be = (int) (newOpts.outWidth / ww);  
			        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
			            be = (int) (newOpts.outHeight / hh);  
			        }  
			        if (be <= 0)  
			            be = 1;  
			    newOpts.inSampleSize = be;//设置缩放比例  
			        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
			    int degree =PictureUtil.readPictureDegree(path);
			    Bitmap  bitmapOld = BitmapFactory.decodeFile(path, newOpts);  
			    bitmap=PictureUtil.rotaingImageView(degree, bitmapOld);
				//bitmapOld.recycle();
                
				ByteArrayOutputStream baos = new ByteArrayOutputStream();  
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
		        int options = 100;  
		        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
		            baos.reset();//重置baos即清空baos  
		            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
		            options -= 10;//每次都减少10  
		        }  
		        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
				
				
				

				boolean isSuccess=ftpClient.storeFile(fileName, isBm);
				if(isSuccess){
					// 保存上传成功的图片信息
					System.out.println("+++++++++strUploadPicture+++++++++++"+"http:/"+FTP_IP+"/images"+filePath+"/"+fileName);
					strUploadPicture[i] = "http:/"+FTP_IP+"/images"+filePath+"/"+fileName;
					returnMessage = 1; // 上传成功
					System.out.println("true");
				}else{
					returnMessage = -2;
				}
				
				
				path=null;
			} else {// 如果登录失败
				returnMessage = -2;
				System.out.println("false");
			}

		} catch (IOException e) {
			e.printStackTrace();
			returnMessage = -2;
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				returnMessage = -2;
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}

		return returnMessage;

	}


	private class UploadTask extends AsyncTask<integer, Void, Integer> {
		String fileName;
		FTPClient ftpClient;
		int i;

		public UploadTask(String fileName, FTPClient ftpClient,int i) {
			super();
			this.fileName = fileName;
			this.ftpClient = ftpClient;
			this.i = i;
		}

		@Override
		protected Integer doInBackground(integer... params) {
			
			return ftpUpload(fileName, ftpClient, i);
		}

		@Override
		protected void onPostExecute(Integer result) {
			progrDialogs[i].setVisibility(View.GONE);
			tv_Uploadsuccess[i].setVisibility(View.VISIBLE);
			if (result == 1) {
				System.out.println("+++++takePhotoTimes++++++++++"+i);
				 tv_Uploadsuccess[i].setText(R.string.uploadSuccuss);
				 int j=i+1;
				 if(j<=takePhotoTimes){
					 new UploadTask(strPitrueUrl[j], getFtpInstans(), j).execute();
				 }
				
				 
				if(i==takePhotoTimes){
					sendPostMsg();
				}
			} else if (result == -2) {
				//uploadFail
				
				tv_Uploadsuccess[i].setText(R.string.uploadfail);
			}
			
			
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			progrDialogs[i].setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

	}
	


	private class SetTheme extends ConnectNetAsyncTask {

		public SetTheme(Context context, String url,
				List<NameValuePair> params, int flag) {
			super(context, url, params, flag);

		}

		@Override
		public void doResult(String result) {
			
			if ("211".equals(result)) {
				ShowToast.ShowTos(RecivPostActivity.this,
						R.string.sendpostsuccess);
				//MostnewsFragment.isSendPost = true;
				RecivPostActivity.this.finish();
			} else if ("112".equals(result) || "113".equals(result)) {
				ShowToast
						.ShowTos(RecivPostActivity.this, R.string.sendpostFail);
			}

		}

	}
	
	private String madeHtml(String[] picture ,String content){
		
		String html="<div style='width:100%;' font-size:17px;>"+content+"</br></br>";
		
	  for(int i=0;i<picture.length;i++){
		  if(picture[i]!=null){
			  html+="<img src='" +picture[i]+"' style='width:80%;margin-left:10%'/></br></br>" ;
		  }
		   
	  }
	    html+="</div>";
	    
	    System.out.println("html=================="+html);
		
		return html;
		
	}
	
	private  void  sendPostMsg(){
		//String title = ed_title.getText().toString();
		String content = ed_content.getText().toString();
		

		if (content.equals(null)) {
			ShowToast.ShowTos(RecivPostActivity.this,R.string.pleaseenter);
		} else {
			String html= madeHtml(strUploadPicture, content);
			
			List<NameValuePair> params_send = new ArrayList<NameValuePair>();
        	params_send.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
        	params_send.add(new BasicNameValuePair("function","SetReply"));
        	params_send.add(new BasicNameValuePair("tid",tId));
        	params_send.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
        	try {
				params_send.add(new BasicNameValuePair("reply",URLEncoder.encode(html,"UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    		
    		new SetReply(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_send, ConnectNetAsyncTask.SETREPLY).execute();
    		
			
			/*List<NameValuePair> params_Settheme = new ArrayList<NameValuePair>();
			params_Settheme.add(new Bas    icNameValuePair("code",ShareData.REQUEST_CODE));
			params_Settheme.add(new BasicNameValuePair("function","SetTheme"));
			try {
				params_Settheme.add(new BasicNameValuePair("title",URLEncoder.encode(title, "UTF-8")));
				params_Settheme.add(new BasicNameValuePair("contentInfo", URLEncoder.encode(html,"UTF-8")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			params_Settheme.add(new BasicNameValuePair("tyid", id));
			params_Settheme.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
			new SetTheme(RecivPostActivity.this,ShareData.LANSHAOQI_ADDRESS_DOPOST,params_Settheme, ConnectNetAsyncTask.SETTHEME).execute();*/
		}

	
	}
	
	
	private boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list =
	            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
	
	private String getRealPathFromURI(Uri contentUri) {

		   String[] filePathColumns={MediaStore.Images.Media.DATA};
		   Cursor c = this.getContentResolver().query(contentUri, filePathColumns, null,null, null);
		   c.moveToFirst();
		   int columnIndex = c.getColumnIndex(filePathColumns[0]);
		  
	    return  path= c.getString(columnIndex);
	}
	
	
	 private class SetReply extends ConnectNetAsyncTask{

			public SetReply(Context context, String url,List<NameValuePair> params, int flag) {
				super(context, url, params, flag);
			}

			@Override
			public void doResult(String result) {
				if("211".equals(result)){
					ShowToast.ShowTos(mContext,R.string.replySuccess);
					RecivPostActivity.this.finish();
				}else if("112".equals(result)){
					ShowToast.ShowTos(mContext,R.string.replyFail);
				}else if("113".equals(result)){
					ShowToast.ShowTos(mContext, R.string.replySuccess);
				}
				
			}
			 
		 }
}
