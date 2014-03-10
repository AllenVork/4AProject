package com.ms.learn.net;

import java.util.List;

import org.apache.http.NameValuePair;


public interface ConnectServer {

	  //登录
       String login(String url, List<NameValuePair> params); 
       
       //注册
       String register(String url, List<NameValuePair> params); 
       
       //获取轮换视频的信息
       String getRecommendTitle(String url,List<NameValuePair> params);
       
       //获取视频列表的信息
       String getRecommendVideoList(String url,List<NameValuePair> params);
       
       //获取视频的详细信息
       String getVideoDetailInfo(String url,List<NameValuePair> params);
       
       //获取评论的详细信息
       String getCommendInfo(String url,List<NameValuePair> params);
       
       //提交评论
       String sendCommend(String url,List<NameValuePair> params);
       
       //删除评论
       String deleteCommend(String url,List<NameValuePair> params);
       
       //获取视频所有的分类
       String getAllVideoKind(String url,List<NameValuePair> params);
       
       //获取新闻信息
       String getNews(String url,List<NameValuePair> params);
       
       //获取视频类别列表信息
       String getVideoListByCategory(String url,List<NameValuePair> params);
       
       //获取视频类别列表信息
       String getNewsDtaiById(String url,List<NameValuePair> params);
       
       //修改密码
       String  changePwd(String url,List<NameValuePair> params);
       
       //获取资源类别列表
       String  getOfficeCategory(String url,List<NameValuePair> params);
       
       //获取文档的详细信息类别列表
       String  getOfficeDetailById(String url,List<NameValuePair> params);
       
       //上传下载的次数
       String  setDowCount(String url,List<NameValuePair> params);
       
       //获取考试内容
       String  getExam(String url,List<NameValuePair> params);
       
       //获取调研内容
       String  getReserch(String url,List<NameValuePair> params);
       
       //获取测评内容
       String  getEvaluation(String url,List<NameValuePair> params);
       
       //获取考试内容
       String  getExamInfo(String url,List<NameValuePair> params);
       
       //获取调研内容
       String  getGetQuestionnaireInfo(String url,List<NameValuePair> params);
       
       //考试成绩录入
       String  getGradExam(String url,List<NameValuePair> params);
       
       //录入压力测试的结果
       String  getResult(String url,List<NameValuePair> params);
       
       //上传视频播放进度
       String  setVideoHistory(String url,List<NameValuePair> params);
       
       //获取论坛的类别
       String  getForumType(String url,List<NameValuePair> params);
       
       //获取帖子的类表
       String  getThemeList(String url,List<NameValuePair> params);
       
       //获取我的帖子的列表
       String  getThemeListByUid(String url,List<NameValuePair> params);
       
       //删除帖子
       String  deleteReplyForum(String url,List<NameValuePair> params); 
       
       //删除论坛的主题
       String  deleteTheme(String url,List<NameValuePair> params); 
       
       //获取主题下的回复
       String  getThemeReplyList(String url,List<NameValuePair> params); 
       
       //发新贴子
       String  setTheme(String url,List<NameValuePair> params); 
       
       //回复帖子
       String  setReply(String url,List<NameValuePair> params); 
       
       //搜索帖子
       String  getThemeListByWhere(String url,List<NameValuePair> params); 
       
     //搜索视频
       String  getVideoByWD(String url,List<NameValuePair> params); 
       
       //获取推送的历史
       String   getPutMnager(String url,List<NameValuePair> params); 
       
       //获取测试的列表
       String   getTestsList(String url,List<NameValuePair> params); 
       
       //获取详细的试卷信息
       String   getTestsInfoListByTid(String url,List<NameValuePair> params);
       
       //删除回复
       String   deleteReply(String url,List<NameValuePair> params); 
       
       
       //删除回复
       String  exit(String url,List<NameValuePair> params); 
       
       //更新版本
       String  getVersionCode(String url,List<NameValuePair> params); 
      
       
}
