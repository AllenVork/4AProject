package com.ms.learn.net;

import java.util.List;

import org.apache.http.NameValuePair;


public interface ConnectServer {

	  //��¼
       String login(String url, List<NameValuePair> params); 
       
       //ע��
       String register(String url, List<NameValuePair> params); 
       
       //��ȡ�ֻ���Ƶ����Ϣ
       String getRecommendTitle(String url,List<NameValuePair> params);
       
       //��ȡ��Ƶ�б����Ϣ
       String getRecommendVideoList(String url,List<NameValuePair> params);
       
       //��ȡ��Ƶ����ϸ��Ϣ
       String getVideoDetailInfo(String url,List<NameValuePair> params);
       
       //��ȡ���۵���ϸ��Ϣ
       String getCommendInfo(String url,List<NameValuePair> params);
       
       //�ύ����
       String sendCommend(String url,List<NameValuePair> params);
       
       //ɾ������
       String deleteCommend(String url,List<NameValuePair> params);
       
       //��ȡ��Ƶ���еķ���
       String getAllVideoKind(String url,List<NameValuePair> params);
       
       //��ȡ������Ϣ
       String getNews(String url,List<NameValuePair> params);
       
       //��ȡ��Ƶ����б���Ϣ
       String getVideoListByCategory(String url,List<NameValuePair> params);
       
       //��ȡ��Ƶ����б���Ϣ
       String getNewsDtaiById(String url,List<NameValuePair> params);
       
       //�޸�����
       String  changePwd(String url,List<NameValuePair> params);
       
       //��ȡ��Դ����б�
       String  getOfficeCategory(String url,List<NameValuePair> params);
       
       //��ȡ�ĵ�����ϸ��Ϣ����б�
       String  getOfficeDetailById(String url,List<NameValuePair> params);
       
       //�ϴ����صĴ���
       String  setDowCount(String url,List<NameValuePair> params);
       
       //��ȡ��������
       String  getExam(String url,List<NameValuePair> params);
       
       //��ȡ��������
       String  getReserch(String url,List<NameValuePair> params);
       
       //��ȡ��������
       String  getEvaluation(String url,List<NameValuePair> params);
       
       //��ȡ��������
       String  getExamInfo(String url,List<NameValuePair> params);
       
       //��ȡ��������
       String  getGetQuestionnaireInfo(String url,List<NameValuePair> params);
       
       //���Գɼ�¼��
       String  getGradExam(String url,List<NameValuePair> params);
       
       //¼��ѹ�����ԵĽ��
       String  getResult(String url,List<NameValuePair> params);
       
       //�ϴ���Ƶ���Ž���
       String  setVideoHistory(String url,List<NameValuePair> params);
       
       //��ȡ��̳�����
       String  getForumType(String url,List<NameValuePair> params);
       
       //��ȡ���ӵ����
       String  getThemeList(String url,List<NameValuePair> params);
       
       //��ȡ�ҵ����ӵ��б�
       String  getThemeListByUid(String url,List<NameValuePair> params);
       
       //ɾ������
       String  deleteReplyForum(String url,List<NameValuePair> params); 
       
       //ɾ����̳������
       String  deleteTheme(String url,List<NameValuePair> params); 
       
       //��ȡ�����µĻظ�
       String  getThemeReplyList(String url,List<NameValuePair> params); 
       
       //��������
       String  setTheme(String url,List<NameValuePair> params); 
       
       //�ظ�����
       String  setReply(String url,List<NameValuePair> params); 
       
       //��������
       String  getThemeListByWhere(String url,List<NameValuePair> params); 
       
     //������Ƶ
       String  getVideoByWD(String url,List<NameValuePair> params); 
       
       //��ȡ���͵���ʷ
       String   getPutMnager(String url,List<NameValuePair> params); 
       
       //��ȡ���Ե��б�
       String   getTestsList(String url,List<NameValuePair> params); 
       
       //��ȡ��ϸ���Ծ���Ϣ
       String   getTestsInfoListByTid(String url,List<NameValuePair> params);
       
       //ɾ���ظ�
       String   deleteReply(String url,List<NameValuePair> params); 
       
       
       //ɾ���ظ�
       String  exit(String url,List<NameValuePair> params); 
       
       //���°汾
       String  getVersionCode(String url,List<NameValuePair> params); 
      
       
}
