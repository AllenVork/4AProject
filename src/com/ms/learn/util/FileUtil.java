package com.ms.learn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileUtil {


	public static void saveCache(Context mContext,InputStream is ,String fileName) {
	    File file = new File(mContext.getExternalCacheDir(), fileName);

	    try {
	       
	        OutputStream os = new FileOutputStream(file);
	        byte[] data = new byte[is.available()];
	        is.read(data);
	        os.write(data);
	        is.close();
	        os.close();
	    } catch (IOException e) {
	        Log.w("saveCache", "Error writing " + file, e);
	    }
	}

	//�ж��ļ��Ƿ����
	public static  boolean hasFileExit(Context mContext ,String fileName) {
	  
	    File file = new File(mContext.getExternalCacheDir(), fileName);
	    if (file != null) {
	        return file.exists();
	    }
	     return false;
	}

	//��ȡͼƬ�ļ�
	public  static Bitmap getBitmap(Context mContext,String fileName){
		if(hasFileExit(mContext, fileName)){
			try {
				FileInputStream	fis = new FileInputStream(new File(mContext.getExternalCacheDir(), fileName));
			    return BitmapFactory.decodeStream(fis);
			} catch (FileNotFoundException e) {
				return null;
			}//�ļ�������
		}
		
		return null;
		
	}
	//����ͼƬ
	public static void saveBitmapCache(Context mContext,Bitmap bitmap ,String fileName) throws IOException {
		//��ͼƬ�ŵ�SD���Ļ������У�����ж�ظ�Ӧ��ʱ����Ҳһ��ɾ��
	    File file = new File(mContext.getExternalCacheDir(), fileName);
	    if(!file.exists()){
	    	file.createNewFile();
	    }
	    try {	       
	        OutputStream os = new FileOutputStream(file);
	        bitmap.compress(CompressFormat.JPEG, 100, os);
	        os.flush();
	        os.close();
	    } catch (IOException e) {
	        Log.w("saveCache", "Error writing " + file, e);
	    }
	}
	
	//�����ļ�
	 public static File createSDFile(String fileName) throws IOException{
			File file = new File( fileName);
			if(!file.exists()){
			    System.out.println("++++file+++++++++++++++"+file.getName());
			file.createNewFile();
			}
			return file;
		}
	 //�ж�sdcard�Ƿ����
	 public static boolean sdcardIsExit(){
		 return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	 }
	 
	 /**
	     * �ݹ�ɾ���ļ����ļ���
	     * @param file    Ҫɾ���ĸ�Ŀ¼
	     */
	    public static void deleteFile(File file){
	        if(file.isFile()){
	            file.delete();
	            return;
	        }
	        if(file.isDirectory()){
	            File[] childFile = file.listFiles();
	            if(childFile == null || childFile.length == 0){
	                file.delete();
	                return;
	            }
	            for(File f : childFile){
	                 deleteFile(f);
	            }
	            file.delete();
	        }
	    }
}
