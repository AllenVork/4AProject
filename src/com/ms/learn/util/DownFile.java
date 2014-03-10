package com.ms.learn.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.Toast;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.bean.OfficeDetailItem;
import com.ms.learn.net.ConnectNetAsyncTask;

public class DownFile {
	NotificationManager mNotifyManager;
	Builder mBuilder;
	private URL url = null;
	private InputStream inputStream = null;
	private int downMusicSize = 0;
	private int MusicSize = 0;
	Context mContext;
	OfficeDetailItem mOfficeItem;
	String externalStorageFileName=ShareData.FILEDOWNLOAD;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				//上传下载的次数
				List<NameValuePair> params_setDown = new ArrayList<NameValuePair>();
				params_setDown.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
				params_setDown.add(new BasicNameValuePair("function","SetDowCount"));
				params_setDown.add(new BasicNameValuePair("id",mOfficeItem.getId()));
				params_setDown.add(new BasicNameValuePair("dowCount",mOfficeItem.getDownCount()));
				new SetdDownCount(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_setDown, ConnectNetAsyncTask.SETDOWNCOUNT).execute();
				
				Toast.makeText(mContext, "下载完成", 3000).show();
				
				break;
			case 1:
				Toast.makeText(mContext, "已下载", 3000).show();
				break;
			case -1:
				Toast.makeText(mContext, "正在下载", 3000).show();
				break;
			default:
				break;
			}
		}

	};

	public   boolean downLoadFile(final OfficeDetailItem officeItem, final Context context,final String path) {
		
		mOfficeItem=officeItem;
		if(!FileUtil.sdcardIsExit()){
			return false;
		}
		mContext=context;
		mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setContentTitle(officeItem.getFileName()).setSmallIcon(
				R.drawable.default_small);

		// Start a lengthy operation in a background thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 下载
				int i = downFile(officeItem.getFileUrl(),	officeItem.getFileName(),path);
				if (i == 1) {
					mHandler.obtainMessage(1).sendToTarget();
				} else {
					mBuilder.setContentText("下载完成").setProgress(0,
							0, false);
					// Removes the progress bar
					mNotifyManager.cancel(0);
					mHandler.obtainMessage(0).sendToTarget();
				}

			}
		}
		).start();
		
		return true;
	}
	
	

	private File  downLoad(String fileName,InputStream input,String path){
		File file=null;
		int downSize=0;
		int i=1;
		OutputStream output = null;
		
		File dirFile = new File(externalStorageFileName+path);   
        if(!dirFile.exists()){   
            dirFile.mkdirs();   
        }   
		
		try {
			
			 file = new File( dirFile,fileName);
			if(!file.exists()){
			    file.createNewFile();
			}
			output = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			while((downSize=input.read(buffer)) != -1){
				output.write(buffer,0,downSize);
				downMusicSize=downMusicSize+downSize;
				
				if(downMusicSize*100/MusicSize==10*i&&i<=10){
					mBuilder.setProgress(100, downMusicSize*100/MusicSize, false);
					mNotifyManager.notify(0, mBuilder.build());
					i++;
				}
			}
			//清缓存，将流中的数据保存到文件中
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally{
			try {
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}	
	
	private  int downFile(String urlStr,  String fileName,String path){
	
	  String split=urlStr.substring(urlStr.lastIndexOf("."));
		
		try{
			
			File file = new File(externalStorageFileName+path,fileName+split);
			if( file.exists()){
			  return 1;
			}else{
				//提示正在下载
				mHandler.obtainMessage(-1).sendToTarget();
				//下载
				HttpURLConnection urlConn = null;

				try {
					url = new URL(urlStr);
					urlConn = (HttpURLConnection) url.openConnection();
					MusicSize=urlConn.getContentLength();
					inputStream = urlConn.getInputStream();
					File resultFile = downLoad( fileName+split, inputStream,path);
					
					if(resultFile == null){
						return -1;
					}
			    } catch (MalformedURLException e) {  
			    	e.printStackTrace();  
			    } catch (IOException e) {  
			    	e.printStackTrace();  
			    }
				
				
			}
		} catch(Exception e){
			e.printStackTrace();
			return -1;
		} finally{
			try{
				//inputStream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return 0;
	}


  
   private class SetdDownCount extends ConnectNetAsyncTask{

	public SetdDownCount(Context context, String url,List<NameValuePair> params, int flag) {
		super(context, url, params, flag);
	}

	@Override
	public void doResult(String result) {
		System.out.println("++++上传++++"+result);
		/*113服务器异常
		211（成功）
		112(失败)*/
		
	}
	   
   }
	
	
}
