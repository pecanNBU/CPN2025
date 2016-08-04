package com.wnwl.CPN2025.log;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.wnwl.CPN2025.bhh.UserInfo;
import com.wnwl.CPN2025.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
public class LOGService
{
    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public LOGService()
    {
        //System.out.println("Aop");
    }

    //@Before("execution(* com.bpm.project.web.*.*(..))")
    public void logAll(JoinPoint point)throws Throwable
    {
        System.out.println("打印========================");
    }

    //@After("execution(* com.bpm.project.web.*.*(..))")
    public void after()
    {
        System.out.println("after");
    }

    //方法执行的前后调用
    @Around("execution(* com.wnwl.CPN2025.hdao.impl.LogLoginDAOImpl.*(..))||execution(* com.wnwl.CPN2025.hdao.impl.LogLoginDAOImpl.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        System.err.println("arrond");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        Calendar ca = Calendar.getInstance();
        String operDate = df.format(ca.getTime());
        String ip=getIpAddress(request);
        UserInfo user = userService.findLoginUser();
        String loginName;
        String name;
        if(user!=null)
        {
            loginName=user.getLoginName();
            name = user.getUserName();
        }
        else
        {
            loginName="anonymousUser";
            name="匿名用户";
        }

        String monthRemark = getMthodRemark(point);
        String monthName = point.getSignature().getName();
        String packages = point.getThis().getClass().getName();
        if (packages.indexOf("$$EnhancerByCGLIB$$") > -1)
        {  //如果是CGLIB动态生成的类
            try {
                packages = packages.substring(0,packages.indexOf("$$"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Object object;
        try {
            object = point.proceed();
        } catch (Exception e) {
            // 异常处理记录日志..log.error(e);
            throw e;
        }
        System.err.println("save done");
        /*SysLog sysLog = new SysLog();
        sysLog.setIpAddress(ip);
        sysLog.setLoginName(loginName);
        sysLog.setName(name);
        sysLog.setMonthName(packages+"."+monthName);
        sysLog.setMonthRemark(monthRemark);
        sysLog.setOperDate(operDate);
        entityService.save(sysLog);*/
        return object;
    }

    //方法运行出现异常时调用
    @AfterThrowing(pointcut = "execution(* com.wnwl.CPN2025.hdao.impl.LogLoginDAOImpl.*(..))",throwing = "ex")
    public void afterThrowing(Exception ex)
    {
        System.out.println("afterThrowing");
        System.out.println(ex);
    }

    // 获取方法的中文备注____用于记录用户的操作日志描述
    public static String getMthodRemark(ProceedingJoinPoint joinPoint)
            throws Exception
    {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        Class targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        String methode = "";
        for (Method m : method)
        {
            if (m.getName().equals(methodName))
            {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length)
                {
                    /*MethodLog methodCache = m.getAnnotation(MethodLog.class);
                    if(methodCache!=null)
                    {
                        methode = methodCache.remark();
                    }
                    break;*/
                }
            }
        }
        return methode;
    }

    public String getIpAddress(HttpServletRequest request) {
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
        if("0:0:0:0:0:0:0:1".equals(ip)) {
            ip="127.0.0.1";
        }
        return ip;
    }
}