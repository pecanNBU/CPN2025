package com.wnwl.CPN2025.action;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements RequestAware, ServletRequestAware,
        SessionAware, ApplicationAware, ServletContextAware, ServletResponseAware {
    private static final long serialVersionUID = 3271591412540669256L;
    /**
     * 本页面主要提供六个主要的接口
     */
    protected Map<String, Object> request;
    protected HttpServletRequest httpRequest;
    protected HttpServletResponse httpResponse;
    protected Map<String, Object> session;
    protected Map<String, Object> application;
    protected ServletContext context;

    /**
     * @param object 需要转换的对象
     * @return 返回json字符串
     * @throws
     * @Title: toJson
     * @Description: 转为json格式字符串, 然后页面通过eval转为json对象
     */
    public String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object).toString();
    }

    /**
     * @param obj 需要转换的对象
     * @return 返回HashMap<name, val>对象
     * @throws
     * @Title: getEntityMap
     * @Description: 将实体类转换为Map对象, 主要针对showDetail*方法
     */
    public HashMap<String, Object> getEntityMap(Object obj) {
        //long begin = System.nanoTime();
        HashMap<String, Object> map = new HashMap<String, Object>();
        Class<? extends Object> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();  // 获取对象的所有方法
        long end = System.nanoTime();
        String methodName;
        Object rsValue;
        String fieldName;   //字段名
        String firstField;  //字段名-第一个字符
        /*Field[] fields = clazz.getDeclaredFields();
        Method method;
        for(Field field:fields){
            fieldName = field.getName();
            fieldName = "get"+(new StringBuilder()).append(Character.toUpperCase(fieldName.charAt(0))).append(fieldName.substring(1)).toString();
            try {
                method = clazz.getMethod(fieldName);
                rsValue = method.invoke(obj);       // 调用get方法，获取返回值
                if (rsValue == null) //没有返回值
                    continue;
                if(rsValue.toString().indexOf("com.wnwl.CPN2025.bhh")>-1)
                    continue;
                fieldName = methodName.substring(4, methodName.length());
                firstField = methodName.substring(3, 4).toLowerCase();
                fieldName = firstField+fieldName;
                map.put(fieldName, rsValue.toString());
            } catch (Exception e) {
                continue;
            }
        }*/
        for (Method method : methods) {
            methodName = method.getName();
            if (methodName.indexOf("get") == -1)    // 不是get方法
                continue;
            try {
                rsValue = method.invoke(obj);       // 调用get方法，获取返回值
                if (rsValue == null) //没有返回值
                    continue;
                if (rsValue.toString().indexOf("com.wnwl.CPN2025.bhh") > -1)
                    continue;
                fieldName = methodName.substring(4, methodName.length());
                firstField = methodName.substring(3, 4).toLowerCase();
                fieldName = firstField + fieldName;
                map.put(fieldName, rsValue.toString());
            } catch (Exception e) {
                continue;
            }
        }
        //long end1 = System.nanoTime();
        //System.out.println((end - begin)+"|"+(end1 - end));
        return map;
    }

    public void setServletContext(ServletContext arg0) {
        this.context = arg0;
    }

    public void setApplication(Map<String, Object> arg0) {
        this.application = arg0;

    }

    public void setSession(Map<String, Object> arg0) {
        this.session = arg0;

    }

    public void setServletRequest(HttpServletRequest arg0) {
        this.httpRequest = arg0;

    }

    public void setServletResponse(HttpServletResponse arg0) {
        this.httpResponse = arg0;
    }

    public void setRequest(Map<String, Object> arg0) {
        this.request = arg0;

    }

}