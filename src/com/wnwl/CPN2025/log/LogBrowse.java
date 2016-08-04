package com.wnwl.CPN2025.log;

import com.wnwl.CPN2025.bhh.LogShow;
import com.wnwl.CPN2025.service.LogService;
import com.wnwl.CPN2025.service.UserService;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * 日志管理类
 * Created by Jenny on 2016/7/19.
 *
 */
public class LogBrowse extends logBase{
    public UserService userService;
    public LogService logService;

    /**
     *@Title: LogBrowse
     *@Description: 声明环绕通知,此处用于存储访问日志(show*)
     *@param pjp
     *@return Object
     *@throws Throwable
     */
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ServletActionContext.getRequest();
        long begin = System.nanoTime();
        //System.out.println("进入方法---环绕通知");
        Object o = pjp.proceed();
        long end = System.nanoTime();
        //System.out.println("退出方法---环绕通知");
        int dtLast = (int) (end-begin) / 1000000;   //执行时长(ms)
        //System.err.println("执行时长:"+dtLast);
        Integer userId = userService.findLoginId();
        String ip = getIpAddress(request);
        //Object[] parames = pjp.getArgs();   //获取目标方法体参数 - 在操作执行前获取
        String packages = pjp.getThis().getClass().getName();   //包名
        String methodName = pjp.getSignature().getName();       //方法名
        String methodRemark = getMethodRemark(pjp, methodName); //方法中文备注
        if (packages.indexOf("$$EnhancerByCGLIB$$") > -1) {     //CGLIB动态生成的类
            try {
                packages = packages.substring(0,packages.indexOf("$$"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String moduleName = packages.split("\\.")[packages.split("\\.").length-1];
        String moduleRemark = getModuleRemark(pjp);
        String url = packages + "." + methodName;               //完整链接
        LogShow logShow = new LogShow();
        logShow.setUserId(userId);
        logShow.setIp(ip);
        logShow.setMethodName(methodName);
        logShow.setMethodRemark(methodRemark);
        logShow.setModuleName(moduleName);
        logShow.setModuleRemark(moduleRemark);
        logShow.setUrl(url);
        logShow.setDtLast(dtLast);
        Long dt = System.currentTimeMillis() / 1000;
        logShow.setDt(dt.intValue());
        logService.getLogShowDAO().saveLog(logShow);
        return o;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }
}