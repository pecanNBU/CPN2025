package com.wnwl.CPN2025.log;

import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by Jenny on 2016/7/21.
 */
public class logBase {

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

    // 获取方法的注释
    public static String getMethodRemark(ProceedingJoinPoint joinPoint, String methodName) throws Exception {
        Method method = joinPoint.getTarget().getClass().getMethod(methodName);
        MyAnnotation.MyMethodAnnotation myMethodAnnotation = method.getAnnotation(MyAnnotation.MyMethodAnnotation.class);
        if(myMethodAnnotation!=null)
            return myMethodAnnotation.remark();
        return null;
    }

    // 获取实体类信息 注释, 表名
    public static String[] getClassNames(Object obj) throws Exception {
        String[] names = new String[2];
        String targetName = obj.getClass().getName();
        Class targetClass = Class.forName(targetName);
        MyAnnotation.MyClassAnnotation myClassAnnotation = (MyAnnotation.MyClassAnnotation) targetClass.getAnnotation(MyAnnotation.MyClassAnnotation.class);
        if(myClassAnnotation!=null) {
            names[0] = myClassAnnotation.sourceName();
            names[1] = myClassAnnotation.remark();
        }
        return names;
    }

    /*public static String[] getClassNames(ProceedingJoinPoint joinPoint) throws Exception {
        String[] names = new String[2];
        String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = Class.forName(targetName);
        MyAnnotation.MyClassAnnotation myClassAnnotation = (MyAnnotation.MyClassAnnotation) targetClass.getAnnotation(MyAnnotation.MyClassAnnotation.class);
        if(myClassAnnotation!=null) {
            names[0] = myClassAnnotation.remark();
            names[1] = myClassAnnotation.sourceName();
        }
        return names;
    }*/

    // 获取模块的注释
    public static String getModuleRemark(ProceedingJoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = Class.forName(targetName);
        MyAnnotation.MyClassAnnotation myClassAnnotation = (MyAnnotation.MyClassAnnotation) targetClass.getAnnotation(MyAnnotation.MyClassAnnotation.class);
        if(myClassAnnotation!=null)
            return myClassAnnotation.remark();
        return null;
    }
}
