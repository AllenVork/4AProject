package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.bean.OtherVideo;
import com.ms.learn.bean.VideoDetailInfo;
import com.ms.learn.bean.VideoHistoryEntry;
import com.ms.learn.download.DownloadDatabase;
import com.ms.learn.fragment.TabFragment;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.service.DownloadService;
import com.ms.learn.util.Helper;
import com.ms.learn.util.ShowToast;
import com.ms.learn.widgets.ProgrDialog;
import com.ms.learn.xml.ParseXmlVideoDetail;

public class VideoDetailActivity extends FragmentActivity {
	private  Context mContext=VideoDetailActivity.this;
	private static VideoView videoView;
	private SeekBar mSeekBar;
	private Button bt_pasue, bt_big,bt_download;
	private TextView tv_totalTime, tv_duration;
	private static TextView tv_videoTitle;
	private static ProgrDialog mpDialog;
	private LinearLayout mBtLayout;
	private String videoID;
	VideoDetailInfo mVideoDetailInfo = new VideoDetailInfo();
	private Context context = VideoDetailActivity.this;
	private Animation mFadeInAnimation;//����
	private Animation mFadeOutAnimation;
	private boolean isGone=false;
	private final int CHANGEPROGRESS=110;
	public static  final int CHANGEVIDEO=130;
	private FrameLayout mFrameLayout;
	private LinearLayout detaillayout;
	int  frameLayoutHeght;
	private static List<OtherVideo> otherVideos=new ArrayList<OtherVideo>();
	static OtherVideo mRemenberOtherVideo;
	private static int remenberComplite=0;
	static int duration;
	static int progress;
	private int hadseeTime=-1;
	private int remberDuration;
	//���ݿ�
	DownloadDatabase databaseImpl=DownloadService.getDownloadDatabase();

   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.videodetail_activity);
		initUI();
	
	}

	private void initUI() {
		mFrameLayout=(FrameLayout) findViewById(R.id.layout_videoAll);
		detaillayout=(LinearLayout) findViewById(R.id.videoDetail_layout);
		videoView = (VideoView) findViewById(R.id.video_detail);
		videoView.setOnCompletionListener(videoOnComple);
		videoView.setOnPreparedListener(videoOnPrepared);
		videoView.setOnErrorListener(videoOnErrorListener);
		videoView.setOnTouchListener(videoOnTouch);
		// ��ȡ���ݹ�����ֵ
		videoID = getIntent().getExtras().getString("videoID");
		String str_hadseeTime=getIntent().getExtras().getString("hadseetime");
		if(str_hadseeTime!=null){
            String[] my =str_hadseeTime.split(":");
		    int hour =Integer.parseInt(my[0]);
			int min =Integer.parseInt(my[1]);
		    int sec =Integer.parseInt(my[2]);
		    hadseeTime =hour*3600+min*60+sec;
			System.out.println("ʱ��ת����ĺ�����Ϊ��"+hadseeTime);

		}	 
		
		System.out.println("++++++videoID+++++++"+videoID);
		
		List<NameValuePair> params_DetailVideo = new ArrayList<NameValuePair>();
		params_DetailVideo.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
		params_DetailVideo.add(new BasicNameValuePair("function","GetVideoInfo"));
		params_DetailVideo.add(new BasicNameValuePair("vid", videoID));
		
		new GetVideoDetailInfoTask(context, ShareData.LANSHAOQI_ADDRESS_DOPOST,params_DetailVideo, ConnectNetAsyncTask.GETREVIDEODETAIL_INFO).execute();

		mSeekBar = (SeekBar) findViewById(R.id.sk_time);
		mSeekBar.setOnSeekBarChangeListener(SeekBarListener);
		bt_pasue = (Button) findViewById(R.id.bt_pause);
		bt_pasue.setOnClickListener(btListener);
		bt_big = (Button) findViewById(R.id.bt_changeBig);
		bt_big.setOnClickListener(btListener);
		
		bt_download=(Button) findViewById(R.id.bt_download);
		bt_download.setOnClickListener(btListener);

		mpDialog = (ProgrDialog) findViewById(R.id.pro_load);
		mpDialog.setText(R.string.loadingVideo);
		mpDialog.setVisibility(View.GONE);

		tv_totalTime = (TextView) findViewById(R.id.tv_totalTime);
		tv_duration = (TextView) findViewById(R.id.tv_finishTime);
		
		mBtLayout=(LinearLayout) findViewById(R.id.showButton);
		tv_videoTitle=(TextView) findViewById(R.id.tv_videoTitle);
		//����
		mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		mFadeInAnimation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation animation) {

				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						if(mFadeInAnimation.hasEnded())
							mBtLayout.startAnimation(mFadeOutAnimation);
					}

				}, 4000);//��ʱΪ4�룬������
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// nothing here
			}

			@Override
			public void onAnimationStart(Animation animation) {
				setMediaVisible();//��ʾ����
			}
		});

		mFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		mFadeOutAnimation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation animation) {
				setMediaGone();//���ؽ���
				isGone=true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// nothing here
			}

			@Override
			public void onAnimationStart(Animation animation) {
				setFadeOutAnimation();
			}

		});
		mBtLayout.startAnimation(mFadeInAnimation);
	}

	private OnCompletionListener videoOnComple = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			// ������ϣ���һ����صĽ�Ŀ
         //   mp.stop();
			System.out.println("+++++++++++++++"+mp.getDuration());
			//�����¼
			remenberVideoHistory(mRemenberOtherVideo, Helper.secondsToStringUpload(mp.getDuration()/1000),"yes");
			mRemenberOtherVideo=null;
			
             ++remenberComplite;
            if(otherVideos.size()-1>=remenberComplite){
            	 mp.reset();
            	 //��¼���ڲ��ŵ���Ƶ
            	 mRemenberOtherVideo=otherVideos.get(remenberComplite);
            	 
            	videoView.setVideoURI(Uri.parse(otherVideos.get(remenberComplite).getVideoUrl()));
            	//��ʱ����
            	OtherVideo otherVideo=otherVideos.get(remenberComplite);
            	otherVideo.setVideoTitle(mVideoDetailInfo.getTitleName());
            	ALearnApplication.getInstance().setmOtherVideo(otherVideo);
            	tv_videoTitle.setText(otherVideo.getVideoName());
                mpDialog.setVisibility(View.VISIBLE);
            }else{
            	  // videoView=null;
            }
         
         
		}
	};

	private OnPreparedListener videoOnPrepared = new OnPreparedListener() {
		@Override
		public void onPrepared(MediaPlayer mp) {
		    
		    //duration=videoView.getDuration();
			duration=mp.getDuration();
			// �������Ľ���
			mSeekBar.setMax(duration);
			// ����׼��ϣ����ž��ǲ���
			mpDialog.setVisibility(View.GONE);
			tv_totalTime.setText(Helper.secondsToString(duration/1000));
			//videoView.start();
			mp.start();
			if(hadseeTime!=-1){
				mp.seekTo(hadseeTime*1000);
				hadseeTime=-1;
			}
			mHandler.removeCallbacks(mUpdateTimeTask);
			mHandler.postDelayed(mUpdateTimeTask, 500);
		}
	};
	private OnErrorListener videoOnErrorListener = new OnErrorListener() {

		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			VideoDetailActivity.this.finish();
			mpDialog.setVisibility(View.GONE);
			mp.pause();
			mp.stop();
			mp.release();
			return false;
		}
	};

	
	private OnSeekBarChangeListener SeekBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// ���¼��ز���
			//mpDialog.setVisibility(View.VISIBLE);
			videoView.seekTo(seekBar.getProgress());
			videoView.setOnPreparedListener(videoOnPrepared);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

		}
	};
	
	private OnTouchListener videoOnTouch =new VideoView.OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				if(isGone){
					isGone=false;
					setMediaVisible();
					setFadeInAnimation();
					mBtLayout.startAnimation(mFadeInAnimation);
				}else {
					isGone=true;
					setMediaGone();
					setFadeOutAnimation();
				}
			}
			return false;
		}
	};

	private OnClickListener btListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_pause:
				if (videoView.isPlaying()) {
				      videoView.pause();
					   bt_pasue.setBackgroundResource(R.drawable.play);
				} else {
					if (videoView != null) {
						videoView.start();
					     bt_pasue.setBackgroundResource(R.drawable.pasue);
					}

				}

				break;
			case R.id.bt_changeBig:
				
				 if( VideoDetailActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
				    {//����
					     //��ȡ�߶ȣ���������Ļ������ʹ��
					      frameLayoutHeght=mFrameLayout.getMeasuredHeight();
					      VideoDetailActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					      bt_big.setBackgroundResource(R.drawable.to_small);
				    }
				 
				  if(VideoDetailActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
				    {
					  VideoDetailActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					  
					  bt_big.setBackgroundResource(R.drawable.tobig);
				    }
				    
				break;
			case R.id.bt_download:
				//������Ƶ
				if(mRemenberOtherVideo.getVideoAllowDn().endsWith("2")){
					Helper.addToDownloads(VideoDetailActivity.this,ALearnApplication.getInstance().getmOtherVideo());
				}else {
					Toast.makeText(VideoDetailActivity.this, "��������", 3000).show();
				}
				
				break;

			default:
				break;
			}

		}
	};

	class GetVideoDetailInfoTask extends ConnectNetAsyncTask {

		public GetVideoDetailInfoTask(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			// ��������,��resultת��Ϊstream����������
			System.out.println("+++++++++++++++++++++"+result);
			
			ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
			mVideoDetailInfo = ParseXmlVideoDetail.parseXmlVideoDetail(stream);
			
			if(mVideoDetailInfo.getOtherVideos()!=null&&mVideoDetailInfo.getOtherVideos().size()!=0){
				otherVideos=mVideoDetailInfo.getOtherVideos();
				//��ʱ��������
				ALearnApplication.getInstance().setmVideoDetailInfo(mVideoDetailInfo);
				

				FragmentManager fm = getSupportFragmentManager();
				TabFragment tabFragment = (TabFragment) fm.findFragmentById(R.id.fragment_tab);
				tabFragment.gotoDetailView();
				
				mpDialog.setVisibility(View.VISIBLE);
				videoView.setVideoURI(Uri.parse(mVideoDetailInfo.getOtherVideos().get(0).getVideoUrl()));
				//��¼��ǰ���ŵ���Ƶ��Ϣ
				mRemenberOtherVideo=mVideoDetailInfo.getOtherVideos().get(0);
	          //��ʱ����
				OtherVideo otherVideo=otherVideos.get(0);
				otherVideo.setVideoTitle(mVideoDetailInfo.getTitleName());
	            ALearnApplication.getInstance().setmOtherVideo(otherVideo);
			    tv_videoTitle.setText(mVideoDetailInfo.getOtherVideos().get(0).getVideoName());
			}else{
				VideoDetailActivity.this.finish();
				ShowToast.ShowTos(mContext, R.string.getdataerro);
			}
		
		}
	}
			
			private void setMediaVisible() {
				  mBtLayout.setVisibility(View.VISIBLE);
				  tv_videoTitle.setVisibility(View.VISIBLE);
			}
			
			
			private void setMediaGone() {
				  mBtLayout.setVisibility(View.GONE);
				  tv_videoTitle.setVisibility(View.GONE);
			}
			
			private void setFadeOutAnimation(){
				tv_videoTitle.setAnimation(mFadeOutAnimation);
				mBtLayout.setAnimation(mFadeOutAnimation);
			}
			
          private void setFadeInAnimation(){
        	  tv_videoTitle.setAnimation(mFadeInAnimation);
				mBtLayout.setAnimation(mFadeInAnimation);
			}
			 
     public Handler  mHandler=new Handler(){
    	 
		@Override
		 public void handleMessage(Message msg) {
			 if(msg.what==CHANGEPROGRESS){
				 if(videoView!=null){
					 progress=videoView.getCurrentPosition();
					 remberDuration=progress;
					 tv_duration.setText(Helper.secondsToString(progress/1000));
					 int seebarProgress=progress;
					 mSeekBar.setProgress(seebarProgress);
				 }
				
			 }else if(msg.what==CHANGEVIDEO){
				 
				
				 
			 }
			 super.handleMessage(msg);
			
	 	}
       };
	  
  private  Runnable mUpdateTimeTask = new Runnable() { 
	  public void run() {
	    if(videoView!=null){ 
	        	 	//������Ƶ���Ž��� 
	        	  mHandler.obtainMessage(CHANGEPROGRESS).sendToTarget();
	   	          mHandler.postDelayed(this, 1000);//1����ִ��һ��
     
	  } 
	  } 
  };
 
    @Override
  protected void onPause() {
    videoView.pause();
	super.onPause();
   }

	@Override
	protected void onResume() {
		if(videoView!=null){
			 videoView.start();
		}
		
		super.onResume();
	}

	@Override
   public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	  if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
	    {//����
		    detaillayout.setVisibility(View.GONE);
		    DisplayMetrics metric = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metric);
	        int width = metric.widthPixels;  // ��Ļ��ȣ����أ�
	        int height = metric.heightPixels;  // ��Ļ�߶ȣ����أ�
	        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();
	        layoutParams.height=height;
	        layoutParams.width=width;
		    mFrameLayout.setLayoutParams(layoutParams);
		
	    }
    if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
    {//����
    	    detaillayout.setVisibility(View.VISIBLE);
    	    DisplayMetrics metric = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metric);
	        int width = metric.widthPixels;  // ��Ļ��ȣ����أ�
	        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();
	        layoutParams.height=frameLayoutHeght;
	        layoutParams.width=width;
		    mFrameLayout.setLayoutParams(layoutParams);
    }
}
    //��¼��Ƶ�����ݿ�
    private  void remenberVideoHistory(OtherVideo otherVideo,String hadSeeTime,String Complite_yes){
    	//���㲥�ŵİٷֱ�
    	if(progress==0){
    	   return	;
    	}
    	System.out.println("+++++"+progress);
        double hadSeePersent=progress/(double)duration*100;
        String strHadsee=String.valueOf(hadSeePersent);
    	String strPersent=strHadsee.substring(0,strHadsee.indexOf(".") );
    	
    	List<NameValuePair> params_SetHistory = new ArrayList<NameValuePair>();
    	params_SetHistory.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
    	params_SetHistory.add(new BasicNameValuePair("function","SetVideoHistory"));
    	params_SetHistory.add(new BasicNameValuePair("vitemid", otherVideo.getVideoID()));
    	params_SetHistory.add(new BasicNameValuePair("uid", ALearnApplication.getInstance().getUserInfo().getUserId()));
    	params_SetHistory.add(new BasicNameValuePair("watchTime", hadSeeTime));
    	params_SetHistory.add(new BasicNameValuePair("percentage", strPersent));
    	new SetVideoHistory(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_SetHistory, ConnectNetAsyncTask.SETVIDEOHISTORY).execute();
    	
    	SimpleDateFormat   formatter    =  new   SimpleDateFormat("yyyy.MM.dd HH:mm:ss");       
    	Date    curDate    =   new    Date(System.currentTimeMillis());//��ȡ��ǰʱ��       
    	String  str    =    formatter.format(curDate);
    	VideoHistoryEntry historyEntry=new VideoHistoryEntry();
    	historyEntry.setVideoID(otherVideo.getVideoID());
    	System.out.println("++++++videoID+++++++"+otherVideo.getVideoID());
    	historyEntry.setVideoName(otherVideo.getVideoName());
    	historyEntry.setVideoHadSeeTime(hadSeeTime);
    	historyEntry.setVideoWhen(Complite_yes);
    	historyEntry.setVideo_Descri(otherVideo.getVideoDecr());
    	historyEntry.setVideoUrl(otherVideo.getVideoVid());
    	databaseImpl.addToVideoHistory(historyEntry);
    }


	@Override
	protected void onStop() {
		
		//��¼����
		if(mRemenberOtherVideo!=null){
			System.out.println("++++++Helper.secondsToString(progress/1000)++++++"+remberDuration/1000);
			remenberVideoHistory(mRemenberOtherVideo, Helper.secondsToStringUpload(remberDuration/1000),"no");
			
		}
		mHandler.removeCallbacks(mUpdateTimeTask);
		
		super.onStop();
	}
    
    private class SetVideoHistory extends ConnectNetAsyncTask{

		public SetVideoHistory(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			System.out.println("result+++++videoHistory+++++"+result);
			
		}
    	
    }
    
    public static void changeVideo(int select){
    	
    	//�����¼
		//remenberVideoHistory(mRemenberOtherVideo, Helper.secondsToString(duration/1000));
		
		remenberComplite=select;
    	 //��¼���ڲ��ŵ���Ƶ
		otherVideos=ALearnApplication.getInstance().getmVideoDetailInfo().getOtherVideos();
		
		System.out.println("+++++++++++otherVideos+++"+otherVideos.size());
		OtherVideo otherVideo=otherVideos.get(select);
		otherVideo.setVideoTitle(ALearnApplication.getInstance().getmVideoDetailInfo().getTitleName());
        videoView.setVideoURI(Uri.parse(otherVideo.getVideoUrl()));
    	//��ʱ����
    	ALearnApplication.getInstance().setmOtherVideo(otherVideo);
    	tv_videoTitle.setText(otherVideo.getVideoName());
        mpDialog.setVisibility(View.VISIBLE);
        mRemenberOtherVideo=otherVideo;
    	
    }
          
}
