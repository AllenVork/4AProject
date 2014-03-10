package com.ms.learn.net;

import java.util.List;

import org.apache.http.NameValuePair;

public class ConnectServerIml implements ConnectServer {

	// µÇÂ¼
	@Override
	public String login(String url, List<NameValuePair> params) {

		if (isCheckUrl(url)) {
			return HttpMethod.doPost(url, params);
		}
		return null;
	}

	// ×¢²á
	@Override
	public String register(String url, List<NameValuePair> params) {

		if (isCheckUrl(url)) {
			return HttpMethod.doPost(url, params);
		}
		return null;
	}

	private boolean isCheckUrl(String url) {
		if (url != null) {
			return true;
		}
		return false;
	}


	@Override
	public String getRecommendVideoList(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getRecommendTitle(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getVideoDetailInfo(String url, List<NameValuePair> params) {
		
		return HttpMethod.doPost(url, params);
	}

	
	@Override
	public String getCommendInfo(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String sendCommend(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String deleteCommend(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getAllVideoKind(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getNews(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getVideoListByCategory(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getNewsDtaiById(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String changePwd(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getOfficeCategory(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getOfficeDetailById(String url, List<NameValuePair> params) {
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getExam(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getReserch(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getEvaluation(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getExamInfo(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getGetQuestionnaireInfo(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getGradExam(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getResult(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String setDowCount(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String setVideoHistory(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getForumType(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getThemeList(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getThemeListByUid(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String deleteReplyForum(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String deleteTheme(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getThemeReplyList(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String setTheme(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String setReply(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getThemeListByWhere(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getVideoByWD(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getPutMnager(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getTestsList(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String getTestsInfoListByTid(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String deleteReply(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	@Override
	public String exit(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return  HttpMethod.doPost(url, params);
	}

	@Override
	public String getVersionCode(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub
		return HttpMethod.doPost(url, params);
	}

	
}
