package com.ms.learn.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.OfficeDownAdapter;
import com.ms.learn.bean.DownFileItem;
import com.ms.learn.bean.OfficeDownLoadEntry;
import com.ms.learn.util.FileUtil;

public class OfficeDownLoadActivity extends Activity {
	private String downLoadFile=ShareData.FILEDOWNLOAD;
	private ExpandableListView listView;
	private OfficeDownAdapter officeDownAdapter;
	List<OfficeDownLoadEntry> officeDownLoadEntrys;
	List<DownFileItem> downFileItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.officedownload_activity);
		initUI();
	}

	private void initUI() {
		listView=(ExpandableListView) findViewById(R.id.lv_officedown);
		listView.setOnChildClickListener(childClickListener);
		listView.setGroupIndicator(null);
		
		findViewById(R.id.bt_officedownBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				OfficeDownLoadActivity.this.finish();
				
			}
		});
		new FileDownLoadFile().execute();
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				final int groupPos = (Integer)arg1.getTag(R.id.tv_abouttitle);      
	            final int childPos = (Integer)arg1.getTag(R.id.tv_categoryname);
	            System.out.println("+++++++groupPos+++++childPos++"+childPos+"+++++++++="+groupPos);
	            
	            if(childPos==-1){
	            	//parent
	            	
	            	String parentPath=officeDownLoadEntrys.get(groupPos).getParenFilePath();
	            	deleteDownloadFile(parentPath);
	            	
	            	
	            }else{
	            	//child
	               String child=officeDownLoadEntrys.get(groupPos).getDownFileItems().get(childPos).getFilePath();
	               deleteDownloadFile(child);
	            
	            
	            }
				return false;
			}
		});
		
		
	}
	
	private void deleteDownloadFile(final String file){
		
		new AlertDialog.Builder(OfficeDownLoadActivity.this).setTitle("确定删除吗？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				File childFile=new File(file);
		         FileUtil.deleteFile(childFile);
		         new FileDownLoadFile().execute();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		}).create().show();
		
		
		 
		
	}

	class FileDownLoadFile extends AsyncTask<Void, Void, List<OfficeDownLoadEntry>>{

		@Override
		protected List<OfficeDownLoadEntry> doInBackground(Void... params) {
			File parentFile=new File(downLoadFile);
			if(parentFile.exists()){
				officeDownLoadEntrys=new ArrayList<OfficeDownLoadEntry>();
		        File[] files= parentFile.listFiles();
		        if(files!=null){
		        	for(int i=0;i<files.length;i++){
		        		OfficeDownLoadEntry loadEntry=new OfficeDownLoadEntry();
		        		loadEntry.setParenFileName(files[i].getName());
		        		loadEntry.setParenFilePath(files[i].getAbsolutePath());
		        		File[] childFiles=files[i].listFiles();
		        		
		        	
		        		if(childFiles!=null){
		        		   downFileItems=new ArrayList<DownFileItem>();
		        		    for(int j=0;j<childFiles.length;j++){
		        			     DownFileItem downFileItem=new DownFileItem();
		        			     String fileName=childFiles[j].getName();
		        			     downFileItem.setFileName(fileName);
		        			     
		        			      String filePath=childFiles[j].getPath();
		        			      downFileItem.setFilePath(filePath);
		        			     
			        		      long time=childFiles[j].lastModified();
			        		      SimpleDateFormat   formatter    =  new   SimpleDateFormat("yyyy.MM.dd HH:mm:ss");       
			            	      Date    curDate    =   new    Date(time);//获取当前时间       
			            	      String    strTime  =    formatter.format(curDate);
			            	      downFileItem.setFileTime(strTime);
			            	      downFileItems.add(downFileItem);
		        		  }
		        		    loadEntry.setDownFileItems(downFileItems);
		        		
		        		}else{
		        			
		        		}
		        		
		        		officeDownLoadEntrys.add(loadEntry);
		        	}
		        	
		        }else {
		        	return null;
		        }
				
			}else {
				return null;
			}
			
			return officeDownLoadEntrys;
		}

		@Override
		protected void onPostExecute(List<OfficeDownLoadEntry> result) {
			super.onPostExecute(result);
			if(result!=null){
				officeDownAdapter=new OfficeDownAdapter(OfficeDownLoadActivity.this, result);
				listView.setAdapter(officeDownAdapter);
			}else {
				//处理 为空的情况
				Toast.makeText(OfficeDownLoadActivity.this, "没有相关下载的文件", 3000).show();
			}
		
			
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
	}
	
	private OnChildClickListener childClickListener=new OnChildClickListener() {
		
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			
			//use other app to open file 
			DownFileItem downFileItem=officeDownAdapter.getChild(groupPosition,childPosition);
			String path=downFileItem.getFilePath();
			File file =new File(path);
			Uri url=Uri.fromFile(file);
			
			Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
			intent.addCategory("android.intent.category.DEFAULT"); 
		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			intent.setDataAndType(url, getFileTyte(downFileItem.getFileName()));
			startActivity(intent);
			
			return true;
		}
	};
	
	public  String getFileTyte(String filename){
		
		String TextType="text/*";
		String pictureType="image/*";
		String videoType="video/*";
		String audioType="audio/*";
		String excelType="application/vnd.ms-excel";
		String wordType="application/msword";
		String pptType="application/vnd.ms-powerpoint";
		
		String pdfType="application/pdf";
		String unknowType="*/*";
	 
	  if(filename.endsWith(".gif")||filename.endsWith(".jpg")||filename.endsWith(".png")||filename.endsWith(".GIF")||filename.endsWith(".JPG")||filename.endsWith(".PNG"))
	        return pictureType;
	  if(filename.endsWith(".mp3")){
		  return audioType;
	  }
	  if(filename.endsWith(".mp4")||filename.endsWith(".rmvb")||filename.endsWith(".rm"))
	        return videoType;
	  if(filename.endsWith(".txt")||filename.endsWith(".html"))
	        return TextType;
	  
	  if(filename.endsWith(".pdf"))
	        return pdfType;
	  
	  if(filename.endsWith(".doc"))
	        return wordType;
	  if(filename.endsWith(".excel"))
	        return excelType;
	  if(filename.endsWith(".ppt"))
	        return pptType;
	  
	  
	  return unknowType;
	 }  
	
	
	
}
