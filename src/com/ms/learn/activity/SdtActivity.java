package com.ms.learn.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.ms.learn.R;
import com.ms.learn.util.ShowToast;

public class SdtActivity extends Activity {
	private Button bt_back,bt_openShoudian;
	boolean isOpen=false,hasFlashLight=false;
	 Camera m_Camera;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shoudiantong_activity);
		initUi();
	}

	@SuppressLint("NewApi")
	private void initUi() {

		findViewById(R.id.bt_shoudiantongBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SdtActivity.this.finish();
			}
		});
		bt_openShoudian=(Button) findViewById(R.id.bt_open);
		bt_openShoudian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//以下代码判断手机是否带闪光灯
			 FeatureInfo[] feature=SdtActivity.this.getPackageManager().getSystemAvailableFeatures();
		     for (FeatureInfo featureInfo : feature) {
				 if (PackageManager.FEATURE_CAMERA_FLASH.equals(featureInfo.name)) {
				   hasFlashLight=true;
			        break;
				    }
				}
				
				  /*  m_Camera = Camera.open();  
	                Camera.Parameters mParameters;  
	                mParameters = m_Camera.getParameters();  
	                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);  
	                m_Camera.setParameters(mParameters);  
	                m_Camera.startPreview();*/
		     System.out.println("+++++++++++++on"+hasFlashLight);
		     
		     if(hasFlashLight){
		  
		    	 turnOnOrOff();
		     }else {
		    	 
		    	 ShowToast.ShowTos(SdtActivity.this, R.string.nolight);
		     }
				
				
			}
		});
		
	}
	
	public void turnOnOrOff()  
    {  
        if(!isOpen)  
        {  
        	isOpen = true;  
            try  
            {  
                m_Camera = Camera.open();  
                Camera.Parameters mParameters= m_Camera.getParameters();  
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);  
                m_Camera.setParameters(mParameters);  
                m_Camera.startPreview();
                bt_openShoudian.setBackgroundResource(R.drawable.shoudiantong_on);
                m_Camera.autoFocus(new AutoFocusCallback() {
                	public void onAutoFocus(boolean success, Camera camera) {
                		
                	}
                });
            }catch(Exception ex){
            	
            }  
        }else {
        	isOpen = false;  
            try  
            {  
                Camera.Parameters mParameters;  
                mParameters = m_Camera.getParameters();  
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);  
                m_Camera.setParameters(mParameters); 
                m_Camera.stopPreview();
                m_Camera.release();  
                bt_openShoudian.setBackgroundResource(R.drawable.shoudiantong_off);
                
            }catch(Exception ex){}  
        }  
        
        
    }

	@Override
	protected void onPause() {
		super.onPause();
		if(m_Camera!=null){
		   // m_Camera.stopPreview();
			m_Camera.release();
		}
	
	}  
	
	

}
