package com.wnwl.CPN2025.hdao.impl;

import com.wnwl.CPN2025.bhh.LogLogin;
import com.wnwl.CPN2025.bhh.UserInfo;
import com.wnwl.CPN2025.hdao.LogLoginDAO;
import com.wnwl.CPN2025.util.BrowserUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

public class LogLoginDAOImpl extends BaseDAOImpl<LogLogin, Integer> implements LogLoginDAO {

	public LogLoginDAOImpl() {
		 super(LogLogin.class);
	}

    /**
     *@Title: LogLoginDAOImpl
     *@Description: 存储登录日志
     *@param userInfo
     *@return String
     *@throws
     */
    public byte logLogin(UserInfo userInfo){
        byte flag = 0;
        HttpServletRequest request = ServletActionContext.getRequest();
        String ip = getIpAddress(request);
        String browser = getBrowser(request);
        LogLogin logLogin = new LogLogin();
        logLogin.setUserId(userInfo.getId());
        logLogin.setIp(ip);
        Long dt = System.currentTimeMillis() / 1000;
        logLogin.setDt(dt.intValue());
        logLogin.setBrowser(browser);
        saveLog(logLogin);
        return flag;
    }

    /**
     *@Title: LogLoginDAOImpl
     *@Description: 添加测试
     *@param a, b, c
     *@return byte
     *@throws
     */
    public byte addTest(int a, String b, boolean c){
        System.err.println("addTest");
        byte flag = 0;
        return flag;
    }

    public byte updateTest(UserInfo userInfo, int a, String b, boolean c){
        System.err.println("updateTest");
        byte flag = 0;
        return flag;
    }

    public byte removeTest(int id){
        System.err.println("removeTest");
        byte flag = 0;
        return flag;
    }

    /**
     *@Title: LogLoginDAOImpl
     *@Description: 获取IP地址
     *@param request
     *@return String
     *@throws
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip="127.0.0.1";
        }
        return ip;
    }

    private String getBrowser(HttpServletRequest request) {
        return BrowserUtil.getBrowserType(request).toString();
    }
	
}
