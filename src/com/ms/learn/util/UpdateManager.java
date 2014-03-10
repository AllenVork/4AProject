package com.ms.learn.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.bean.VersionMsg;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.xml.ParseXmlGetVersionCode;

public class UpdateManager {
	private static final String TAG = "Update";
	private static final String UPDATE_SAVENAME = "4alean.apk";
	private static final String SAVEPATH = ShareData.FILEDOWNLOAD;
	private static final String PACKETNAME = "com.ms.learn";
	String apkFile;
	VersionMsg msg;
	
	private Context mContext;
	private String updateMsg = "检测到新版本，下载更新？";

	private Dialog noticeDialog;
	private Dialog downloadDialog;
	private ProgressBar mProgress;

	private static final int DOWN_UPDATE = 1;
	private static final int DOWN_OVER = 2;

	private int progress;
	private Thread downLoadThread;
	private boolean interceptFlag = false;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	public void checkUpdateInfo() {
		//从服务器获取版本更新信息
		getServerVerCode();
	}

	private int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(PACKETNAME, 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
			verCode=-1;
		}
		return verCode;
	}

	private String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(PACKETNAME, 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verName;
	}

	private String getAppName(Context context) {
		String verName = context.getResources().getText(R.string.app_name).toString();
		return verName;
	}

	private void getServerVerCode() {		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("code", "372102b2070309eb"));
			params.add(new BasicNameValuePair("function", "GetVersionCode"));
			new GetVersionCode(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params, ConnectNetAsyncTask.GETVERSIONCODE).execute();
			
	}
	
    private void notNewVersionShow() {
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本已是最新版，无需更新!");
        Dialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("软件更新").setMessage(sb.toString())
                        .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                int which) {
                                                        dialog.dismiss();
                                                }

                                        }).create();
        dialog.show();
}

	private void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setMessage(updateMsg);
		builder.setPositiveButton("下载", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");

		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.updatedialog_layout, null);
		mProgress = (ProgressBar)v.findViewById(R.id.pro_download);

		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();
		downloadApk();
	}

	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(msg.getvUrl());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(SAVEPATH);
				if (!file.exists()) {
					file.mkdir();
				}
				apkFile = SAVEPATH + UPDATE_SAVENAME;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						mHandler.sendEmptyMessage(DOWN_OVER);
						downloadDialog.dismiss();
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	private void installApk() {
		File apkfile = new File(SAVEPATH + UPDATE_SAVENAME);
		if (!apkfile.exists()) {
			return;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);
	}
	
	  private class GetVersionCode extends ConnectNetAsyncTask{

			public GetVersionCode(Context context, String url,List<NameValuePair> params, int flag) {
				super(context, url, params, flag);
			}

			@Override
			public void doResult(String result) {
				if(result.startsWith("<?")){
					ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
					msg=ParseXmlGetVersionCode.parseXml(stream);
					if(msg.getvCode()!=null){
						//下载APKmsg.getvCode()
						System.out.println("================"+getVerName(mContext));
						if(!getVerName(mContext).equals(msg.getvCode())){
							showNoticeDialog() ;
						}						
					}					
				}				
			}	    	   
	       }

}
