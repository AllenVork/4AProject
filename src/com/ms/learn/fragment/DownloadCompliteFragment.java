package com.ms.learn.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DownloadCompliteFragment  extends Fragment{
	
	private String downLoadVideoFile=ShareData.VIEDEODOWNLOAD;
	private ExpandableListView listView;
	private OfficeDownAdapter officeDownAdapter;
	List<OfficeDownLoadEntry> officeDownLoadEntrys;
	List<DownFileItem> downFileItems;
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view =getView();
		listView=(ExpandableListView) view.findViewById(R.id.lv_downVideocomplite);
		listView.setGroupIndicator(null);
	    listView.setOnItemLongClickListener(itemLongClickListener);
	    listView.setOnChildClickListener(childClickListener);
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//遍历文件夹
		
		new FileDownLoadFile().execute();
       
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
       return inflater.inflate(R.layout.downloadcomplite_frament, container, false);
	}

	
	
	@Override
	public void onResume() {
		new FileDownLoadFile().execute();
		super.onResume();
		
	}



	class FileDownLoadFile extends AsyncTask<Void, Void, List<OfficeDownLoadEntry>>{

		@Override
		protected List<OfficeDownLoadEntry> doInBackground(Void... params) {
			File parentFile=new File(downLoadVideoFile);
			if(parentFile.exists()){
				officeDownLoadEntrys=new ArrayList<OfficeDownLoadEntry>();
		        File[] files= parentFile.listFiles();
		        
		        if(files!=null){
		        	for(int i=0;i<files.length;i++){
		        		
		        		if(files[i].isDirectory()){
		        			
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
				officeDownAdapter=new OfficeDownAdapter(getActivity(), result);
				listView.setAdapter(officeDownAdapter);
			}else {
				//处理 为空的情况
				Toast.makeText(getActivity(), "没有相关下载的文件", 3000).show();
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
	
private OnItemLongClickListener itemLongClickListener=new OnItemLongClickListener() {
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
};	

	
  private void deleteDownloadFile(final String file){
		
		new AlertDialog.Builder(getActivity()).setTitle("确定删除吗？")
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
  
  public  String getFileTyte(String fileName){
		
		String videoType="video/*";
		String audioType="audio/*";
		String unknowType="*/*";
	    String filename=fileName.toLowerCase();
	  
	  if(filename.endsWith(".mp3")||filename.endsWith(".wav")){
		  
		  return audioType;
	  }
	  
	  if(filename.endsWith(".mp4")||filename.endsWith(".rmvb")||filename.endsWith(".rm")||filename.endsWith(".3gp"))
	       
		  return videoType;
	  
	  return unknowType;
	 }  
	
	
	
}
