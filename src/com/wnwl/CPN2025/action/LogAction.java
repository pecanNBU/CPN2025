package com.wnwl.CPN2025.action;

import com.wnwl.CPN2025.bhh.*;
import com.wnwl.CPN2025.hdao.LogLoginDAO;
import com.wnwl.CPN2025.hdao.LogOperDAO;
import com.wnwl.CPN2025.hdao.LogShowDAO;
import com.wnwl.CPN2025.hdao.UserInfoDAO;
import com.wnwl.CPN2025.service.LogService;
import com.wnwl.CPN2025.service.UserService;
import com.wnwl.CPN2025.util.DtUtil;
import com.wnwl.CPN2025.util.PageBean;

import java.util.*;

/**
 *
 * Simple to Introduction
 * @ProjectName:    建设工程颗粒物与噪声在线监测系统
 * @Package:        com.wnwl.CPN2025.action
 * @ClassName:      日志操作类
 * @Description:    登录日志、浏览日志、操作日志
 * @Author:         Jenny
 * @CreateDate:     2016.7.22
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
 */
public class LogAction extends BaseAction {
    private LogService logService;
    private UserService userService;
    private Byte nodeState;
    private int jumpPage;
    private int pageSize;
    private PageBean pageBean;
    private ArrayList<Object> rows;
    private HashMap<String, Object> map;
    private long count;
    private Integer logId;

    /**
     *@Title: showLog
     *@Description: 打开日志管理页面
     *@param
     *@return showLog.jsp
     *@throws
     */
    public String showLogs(){
        return SUCCESS;
    }

    /**
     *@Title: showLogLogins
     *@Description: 打开登录日志页面
     *@param
     *@return 返回showLogLogin.jsp
     *@throws
     */
    public String showLogLogin(){
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(),httpRequest.getRequestURI());
        return SUCCESS;
    }

    /**
     *@Title: json_logLogin
     *@Description: 获取登录日志的所有值
     *@param
     *@return rows, pagebean
     *@throws
     */
    public String json_logLogin(){
        pageBean = PageBean.getInstance();
        String cond = " id > 0 order by dt desc";
        LogLoginDAO logLoginDAO = logService.getLogLoginDAO();
        count = logLoginDAO.getCountbyCond(cond);
        pageBean.setPageBean(count, jumpPage, pageSize, "");
        List<LogLogin> logLogins = logLoginDAO.getList(jumpPage, pageSize, cond);
        rows = transRows_logLogins(logLogins);
        return SUCCESS;
    }
    /**
     *@Title: transRows_logLogins
     *@Description: 解析结合,根据页面格式返回对应数据
     *@param logLogins 取到的实体类集合
     *@return rows
     *@throws
     */
    private ArrayList<Object> transRows_logLogins(List<LogLogin> logLogins){
        List<Object> datas;
        rows = new ArrayList<Object>();
        UserInfo userInfo;
        UserInfoDAO userInfoDAO = userService.getUserInfoDAO();
        int userId;
        for(LogLogin logLogin:logLogins){
            datas = new ArrayList<Object>();
            datas.add(logLogin.getId());
            userId = logLogin.getUserId();
            userInfo = userInfoDAO.findById(userId);
            datas.add(userInfo.getUserName());
            datas.add(userInfo.getLoginName());
            datas.add(logLogin.getIp());
            datas.add(DtUtil.transDtToStr(logLogin.getDt()));
            datas.add(logLogin.getBrowser());
            rows.add(datas);
        }
        return rows;
    }

    /**
     *@Title: showLogShow
     *@Description: 打开浏览日志页面
     *@param
     *@return 返回showLogShow.jsp
     *@throws
     */
    public String showLogShow(){
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(),httpRequest.getRequestURI());
        return SUCCESS;
    }

    /**
     *@Title: json_logLogin
     *@Description: 获取登录日志的所有值
     *@param
     *@return rows, pagebean
     *@throws
     */
    public String json_logShow(){
        pageBean = PageBean.getInstance();
        String cond = " id > 0 order by dt desc";
        LogShowDAO logShowDAO = logService.getLogShowDAO();
        count = logShowDAO.getCountbyCond(cond);
        pageBean.setPageBean(count, jumpPage, pageSize, "");
        List<LogShow> logShows = logShowDAO.getList(jumpPage, pageSize, cond);
        rows = transRows_logShows(logShows);
        return SUCCESS;
    }
    /**
     *@Title: transRows_logLogins
     *@Description: 解析结合,根据页面格式返回对应数据
     *@param logShows 取到的实体类集合
     *@return rows
     *@throws
     */
    private ArrayList<Object> transRows_logShows(List<LogShow> logShows){
        List<Object> datas;
        rows = new ArrayList<Object>();
        UserInfo userInfo;
        UserInfoDAO userInfoDAO = userService.getUserInfoDAO();
        int userId;
        for(LogShow logShow:logShows){
            datas = new ArrayList<Object>();
            datas.add(logShow.getId());
            userId = logShow.getUserId();
            datas.add(logShow.getModuleName()+"."+logShow.getMethodName());
            datas.add(logShow.getModuleRemark()+" → "+logShow.getMethodRemark());
            userInfo = userInfoDAO.findById(userId);
            datas.add(userInfo.getUserName());
            datas.add(DtUtil.transDtToStr(logShow.getDt()));
            datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail("+logShow.getId()+");'>详细信息</a>");
            rows.add(datas);
        }
        return rows;
    }

    /**
     *@Title: showDetailLogShow
     *@Description: 获取浏览日志的详情
     *@param
     *@return map
     *@throws
     */
    public String showDetailLogShow(){
        LogShow logShow = logService.getLogShowDAO().findById(logId);
        int userId = logShow.getUserId();
        UserInfo userInfo = userService.getUserInfoDAO().findById(userId);
        map = new HashMap<String, Object>();
        map.put("moduleName", logShow.getModuleName());
        map.put("moduleRemark", logShow.getModuleRemark());
        map.put("methodName", logShow.getMethodName());
        map.put("methodRemark", logShow.getMethodRemark());
        map.put("loginName", userInfo.getLoginName());
        map.put("ip", logShow.getIp());
        map.put("dt", DtUtil.transDtToStr(logShow.getDt()));
        map.put("dtLast", logShow.getDtLast()+" ms");
        map.put("url", logShow.getUrl());
        return SUCCESS;
    }
    
    /**
     *@Title: showLogOper
     *@Description: 打开浏览日志页面
     *@param
     *@return 返回showLogOper.jsp
     *@throws
     */
    public String showLogOper(){
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(),httpRequest.getRequestURI());
        return SUCCESS;
    }

    /**
     *@Title: json_logLogin
     *@Description: 获取登录日志的所有值
     *@param
     *@return rows, pagebean
     *@throws
     */
    public String json_logOper(){
        pageBean = PageBean.getInstance();
        String cond = " id > 0 order by dt desc";
        LogOperDAO logOperDAO = logService.getLogOperDAO();
        count = logOperDAO.getCountbyCond(cond);
        pageBean.setPageBean(count, jumpPage, pageSize, "");
        List<LogOper> logOpers = logOperDAO.getList(jumpPage, pageSize, cond);
        rows = transRows_logOpers(logOpers);
        return SUCCESS;
    }
    /**
     *@Title: transRows_logLogins
     *@Description: 解析结合,根据页面格式返回对应数据
     *@param logOpers 取到的实体类集合
     *@return rows
     *@throws
     */
    private ArrayList<Object> transRows_logOpers(List<LogOper> logOpers){
        List<Object> datas;
        rows = new ArrayList<Object>();
        UserInfo userInfo;
        UserInfoDAO userInfoDAO = userService.getUserInfoDAO();
        int userId;
        for(LogOper logOper:logOpers){
            datas = new ArrayList<Object>();
            datas.add(logOper.getId());
            datas.add(logOper.getOperType());
            datas.add(logOper.getTbName());
            datas.add(logOper.getTbRemark());
            userId = logOper.getUserId();
            userInfo = userInfoDAO.findById(userId);
            datas.add(userInfo.getLoginName());
            datas.add(logOper.getDtLast());
            datas.add(DtUtil.transDtToStr(logOper.getDt()));
            datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail("+logOper.getId()+");'>详细信息</a>");
            rows.add(datas);
        }
        return rows;
    }

    /**
     *@Title: showDetailLogOper
     *@Description: 获取操作日志的详情
     *@param
     *@return map
     *@throws
     */
    public String showDetailLogOper(){
        LogOper logOper = logService.getLogOperDAO().findById(logId);
        int userId = logOper.getUserId();
        UserInfo userInfo = userService.getUserInfoDAO().findById(userId);
        map = new HashMap<String, Object>();
        map.put("tbName", logOper.getTbName());
        map.put("tbRemark", logOper.getTbRemark());
        map.put("operType", logOper.getOperType());
        map.put("loginName", userInfo.getLoginName());
        map.put("userName", userInfo.getUserName());
        map.put("ip", logOper.getIp());
        map.put("dt", DtUtil.transDtToStr(logOper.getDt()));
        map.put("dtLast", logOper.getDtLast()+" ms");
        rows = new ArrayList<Object>();
        String cond = "logId="+logOper.getId();
        List<LogOperCont> logOperConts = logService.getLogOperContDAO().findSelfObjects(cond);
        if(logOperConts!=null&&logOperConts.size()>0){
            byte type = logOper.getType();
            Object[] contDatas;
            for(LogOperCont logOperCont:logOperConts){
                if(type==2) {
                    contDatas = new Object[4];
                    contDatas[0] = logOperCont.getFdName();
                    contDatas[1] = logOperCont.getFdRemark();
                    contDatas[2] = logOperCont.getValOld();
                    contDatas[3] = logOperCont.getValNew();
                }
                else {
                    contDatas = new Object[3];
                    contDatas[0] = logOperCont.getFdName();
                    contDatas[1] = logOperCont.getFdRemark();
                    contDatas[2] = logOperCont.getValOld();
                }
                rows.add(contDatas);
            }
        }
        map.put("contDatas", rows);
        return SUCCESS;
    }
    
    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Byte getNodeState() {
        return nodeState;
    }

    public void setNodeState(Byte nodeState) {
        this.nodeState = nodeState;
    }

    public void setJumpPage(int jumpPage) {
        this.jumpPage = jumpPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public PageBean getPageBean() {
        return pageBean;
    }

    public ArrayList<Object> getRows() {
        return rows;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }
}
