package com.ms.learn.net;

import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.util.ShowToast;
import com.ms.learn.widgets.ShowDialog;

//第一个参数是params代表要传进来的参数，第二个代表进度条显示单位，第三个代表返回类型
public abstract class ConnectNetAsyncTask extends AsyncTask<Void, Void, String> {

	private String url;
	private List<NameValuePair> params;
	public static final int LOGIN = 1;
	public static final int REGISTER = 2;
	public static final int GETRECOMMEND_TITLE = 3;
	public static final int GETRECOMMEND_VIDEOLIST = 4;
	public static final int GETREVIDEODETAIL_INFO = 5;
	public static final int GETCOMMENDDATA = 6;
	public static final int SENDCOMMEND = 7;
	public static final int DELETECOMMEND = 8;
	public static final int GETALLVIDEOSKIND = 9;
	public static final int GETNEWS = 10;
	public static final int GETVIDEOLISTBYCATEGORY = 11;
	public static final int GETNEWSDETAILBYID = 12;
	public static final int CHANGEPWD = 13;
	public static final int GETOFFICECATEGORY = 14;
	public static final int GETOFFICEDETAILBYID = 15;
	public static final int GETEXAM = 16;
	public static final int GETRESERCH = 17;
	public static final int GETEVALUATION = 18;
	public static final int GETEXAMINFO = 19;
	public static final int GETQUESTIONNAIREINFO = 20;
	public static final int GETGRADEXAM = 21;
	public static final int GETRESULT = 22;
	public static final int SETDOWNCOUNT = 23;
	public static final int SETVIDEOHISTORY = 24;
	public static final int GETFORUMTYPE = 25;
	public static final int GETTHEMELIST = 26;
	public static final int DELETETHEME = 27;
	public static final int GETTHEMELISTBYUID = 28;
	public static final int DELETEREPLYFORUM = 29;
	public static final int GETTHEMEREPLYLIST = 30;
	public static final int SETTHEME = 31;
	public static final int SETREPLY = 32;
	public static final int GETTHEMELISTBYWHERE = 33;
	public static final int GETVIDEOBYWD = 34;
	public static final int GETPUTMNAGER = 35;
	public static final int GETTESTSLIST = 36;
	public static final int GETTESTSINFOLISTBYTID = 37;
	public static final int DELETEREPLY = 38;
	public static final int EXIT = 39;
	public static final int GETVERSIONCODE = 40;
	private int flag;
	ConnectServer mConnectServer;
	Dialog dialog;
	private Context mContext;

	public ConnectNetAsyncTask(Context context, String url,
			List<NameValuePair> params, int flag) {
		this.url = url;
		this.params = params;
		this.flag = flag;
		this.mContext = context;
		mConnectServer = new ConnectServerIml();
	}

	@Override
	protected void onPreExecute() {
		// 在开始之前显示进度条对话框
		super.onPreExecute();
		// 设置显示的信息
		if (flag == GETCOMMENDDATA || flag == SETDOWNCOUNT
				|| flag == SETVIDEOHISTORY || flag == EXIT
				|| GETVERSIONCODE == flag) {

		} else {
			creatDialog(flag);
			dialog = ShowDialog.createLoadingDialog(mContext);
			dialog.show();
		}
	}

	@Override
	protected String doInBackground(Void... para) {

		if (flag == LOGIN) {
			// 实现登录,返回信息
			return mConnectServer.login(url, params);

		} else if (REGISTER == flag) {
			// 实现注册
			return mConnectServer.register(url, params);
		} else if (GETRECOMMEND_TITLE == flag) {
			// 获取视频的轮换图片
			return mConnectServer.getRecommendTitle(url, params);
		} else if (GETRECOMMEND_VIDEOLIST == flag) {
			// 获取视频的列表的信息
			return mConnectServer.getRecommendVideoList(url, params);
		} else if (GETREVIDEODETAIL_INFO == flag) {
			// 获取视频的详细信息
			return mConnectServer.getVideoDetailInfo(url, params);
		} else if (GETCOMMENDDATA == flag) {
			// 获取视频的详细信息
			return mConnectServer.getCommendInfo(url, params);
		} else if (SENDCOMMEND == flag) {
			// 发布评论
			return mConnectServer.sendCommend(url, params);
		} else if (DELETECOMMEND == flag) {
			// 删除自己的评论
			return mConnectServer.deleteCommend(url, params);
		} else if (GETALLVIDEOSKIND == flag) {
			// 获取视频的所有的分类
			return mConnectServer.getAllVideoKind(url, params);
		} else if (GETNEWS == flag) {
			// 获取新闻
			return mConnectServer.getNews(url, params);
		} else if (GETVIDEOLISTBYCATEGORY == flag) {
			// 通过分类获取视频的所有的相关视频
			return mConnectServer.getVideoListByCategory(url, params);
		} else if (GETNEWSDETAILBYID == flag) {
			// 获取新闻的详细信息
			return mConnectServer.getNewsDtaiById(url, params);
		} else if (CHANGEPWD == flag) {
			// 修改密码
			return mConnectServer.changePwd(url, params);
		} else if (GETOFFICECATEGORY == flag) {
			// 获取文档的所有的分类
			return mConnectServer.getOfficeCategory(url, params);
		} else if (GETOFFICEDETAILBYID == flag) {
			// 获取获取文档详细的内容
			return mConnectServer.getOfficeDetailById(url, params);
		} else if (GETEXAM == flag) {
			// 获取考试
			return mConnectServer.getExam(url, params);
		} else if (GETRESERCH == flag) {
			// 获取调研
			return mConnectServer.getReserch(url, params);
		} else if (GETEVALUATION == flag) {
			// 获取视测评
			return mConnectServer.getEvaluation(url, params);
		} else if (GETEXAMINFO == flag) {
			// 获取视测评
			return mConnectServer.getExamInfo(url, params);
		} else if (GETQUESTIONNAIREINFO == flag) {
			// 获取视测评
			return mConnectServer.getGetQuestionnaireInfo(url, params);
		} else if (GETGRADEXAM == flag) {
			// 上传成绩
			return mConnectServer.getGradExam(url, params);
		} else if (GETRESULT == flag) {
			// 上传压力测试的成绩
			return mConnectServer.getResult(url, params);
		} else if (SETDOWNCOUNT == flag) {
			return mConnectServer.setDowCount(url, params);
		} else if (SETVIDEOHISTORY == flag) {

			return mConnectServer.setVideoHistory(url, params);
		} else if (GETFORUMTYPE == flag) {

			return mConnectServer.getForumType(url, params);
		} else if (GETTHEMELIST == flag) {

			return mConnectServer.getThemeList(url, params);
		} else if (GETTHEMELISTBYUID == flag) {

			return mConnectServer.getThemeListByUid(url, params);
		} else if (DELETEREPLYFORUM == flag) {

			return mConnectServer.deleteReplyForum(url, params);
		} else if (DELETETHEME == flag) {

			return mConnectServer.deleteTheme(url, params);
		} else if (GETTHEMEREPLYLIST == flag) {

			return mConnectServer.getThemeReplyList(url, params);
		} else if (SETTHEME == flag) {

			return mConnectServer.setTheme(url, params);
		} else if (SETREPLY == flag) {

			return mConnectServer.setReply(url, params);
		} else if (GETTHEMELISTBYWHERE == flag) {

			return mConnectServer.getThemeListByWhere(url, params);
		} else if (GETVIDEOBYWD == flag) {

			return mConnectServer.getVideoByWD(url, params);
		} else if (GETPUTMNAGER == flag) {

			return mConnectServer.getPutMnager(url, params);
		} else if (GETTESTSLIST == flag) {

			return mConnectServer.getTestsList(url, params);
		} else if (GETTESTSINFOLISTBYTID == flag) {

			return mConnectServer.getTestsInfoListByTid(url, params);
		} else if (DELETEREPLY == flag) {

			return mConnectServer.deleteReply(url, params);
		} else if (EXIT == flag) {

			return mConnectServer.exit(url, params);
		} else if (GETVERSIONCODE == flag) {

			return mConnectServer.getVersionCode(url, params);
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		// 这个result就是doInBackground返回的结果
		if (dialog != null) {
			if (dialog.isShowing()) {
				dialog.cancel();
			}
		}
		System.out.println("result==========" + result);
		// 判断是否为空，同时是否请求超时
		if (result == ShareData.REQUES_TIMEOUT && flag != GETVERSIONCODE) {
			ShowToast.ShowTos(mContext, R.string.requestTimeout);
		} else {
			if (result == null && flag == GETCOMMENDDATA) {
				// 处理请求的结果
				doResult(result);
			} else {
				if (result == null && flag != GETCOMMENDDATA) {
					ShowToast.ShowTos(mContext, R.string.requestTimeout);
				} else {
					doResult(result);
				}
			}
		}
	}

	// 抽象方法处理返回的结果
	public abstract void doResult(String result);

	// 设置显示的信息
	private void creatDialog(int flag) {
		int msgID = 0;
		if (flag == LOGIN) {
			msgID = R.string.logining;
		} else if (REGISTER == flag) {
			msgID = R.string.registering;
		} else if (SENDCOMMEND == flag) {
			msgID = R.string.sendcommend;
		} else {
			msgID = R.string.loading;
		}
		ShowDialog.setTextMsg(msgID);
	}
}
