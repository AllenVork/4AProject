package com.ms.learn.util;

import android.content.Context;
import android.widget.Toast;


public class ShowToast {
	
	public static void ShowTos(Context context,int text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	

}
