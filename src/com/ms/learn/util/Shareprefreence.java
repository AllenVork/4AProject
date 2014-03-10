package com.ms.learn.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Shareprefreence {
	
	public static  void putMsg(Context context,String msg){
		
		SharedPreferences msgInfo =context.getSharedPreferences("user_info", 0);  
		msgInfo.edit().putString("msg", msg).commit(); 
		
	}
	
	public static String getMsg(Context context){
		SharedPreferences msgInfo= context.getSharedPreferences("user_info", 0);  
        return msgInfo.getString("msg", "");
	}

}
