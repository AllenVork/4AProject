package com.ms.learn.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.Shareprefreence;
import com.ms.learn.util.ShowToast;

public class JgPushRecever extends BroadcastReceiver {

	
	
   private NotificationManager nm;
   private int DOWNLOAD_NOTIFY_ID=0x01;
	@Override
	public void onReceive(Context context, Intent intent) {
		String message=null;
		String title =null;
		
		if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
         
         
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            System.out.println("JPush用户注册成功");
             
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	   System.out.println("接受到推送下来的自定义消息");
        	   Bundle bundle = intent.getExtras();
        	   title=bundle.getString(JPushInterface.EXTRA_TITLE);
               message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
               NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
            	        .setSmallIcon(R.drawable.ic_launcher)
            	        .setContentTitle(title)
            	        .setAutoCancel(true)
            	        .setDefaults(Notification.DEFAULT_ALL);
            	// Creates an explicit intent for an Activity in your app
            	Intent resultIntent = new Intent(context, NoticationActivity.class);
            	resultIntent.putExtra("msg", message);
            	resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            	// Adds the back stack for the Intent (but not the Intent itself)
            	stackBuilder.addParentStack(NoticationActivity.class);
            	// Adds the Intent that starts the Activity to the top of the stack
            	stackBuilder.addNextIntent(resultIntent);
            	PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(  0,PendingIntent.FLAG_UPDATE_CURRENT
            	        );
            	mBuilder.setContentIntent(resultPendingIntent);
            	// mId allows you to update the notification later on.
            	nm.notify(DOWNLOAD_NOTIFY_ID, mBuilder.build());
        	   
        	  
        	   
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            System.out.println("接受到推送下来的通知");
            
 
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("用户点击打开了通知");
            nm.cancel(DOWNLOAD_NOTIFY_ID);
           /* System.out.println("+++++++message++++++++++++"+Shareprefreence.getMsg(context));
     	   Intent intentActivity=new Intent(context,NoticationActivity.class);
     	   intentActivity.putExtra("msg", Shareprefreence.getMsg(context));
     	   intentActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     	   context.startActivity(intentActivity);*/
     	   
     	  
 
        } else if(JPushInterface.EXTRA_MESSAGE.equals(intent.getAction())){
        	 System.out.println("接收推送的消息");
            
        }

	}
	
	

}
