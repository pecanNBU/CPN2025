package com.wnwl.CPN2025.log;

import com.wnwl.CPN2025.bhh.LogOper;
import com.wnwl.CPN2025.bhh.LogOperCont;
import com.wnwl.CPN2025.hdao.LogOperContDAO;
import com.wnwl.CPN2025.service.LogService;
import com.wnwl.CPN2025.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * 日志管理类
 * Created by Jenny on 2016/7/19.
 *
 */
public class LogOperation extends logBase{
    public UserService userService;
    public LogService logService;

    //声明后置通知
    public void doAfterReturning(JoinPoint joinPoint) {
        System.err.println("后置通知");
        //System.out.println("---" + result + "---");
    }

    //声明例外通知
    public void doAfterThrowing(JoinPoint joinPoint) {
        System.err.println("例外通知");
        //System.out.println(e.getMessage());
    }

    //声明最终通知
    public void doAfterAdd(JoinPoint joinPoint) {
        System.err.println("最终通知");
        Object[] parames = joinPoint.getArgs();//获取目标方法体参数
        //String aa = parseParames(parames);
        String className = joinPoint.getTarget().getClass().toString();//获取目标类名
        className = className.substring(className.indexOf("com"));
        String signature = joinPoint.getSignature().toString();//获取目标方法签名
        String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));
        //String modelName = getModelName(className); //根据类名获取所属的模块
        List<Map<String, String>> listOperConts = new ArrayList<Map<String, String>>();
        try {
            listOperConts = adminOptionContent(parames);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(className+"|"+methodName+"|"+listOperConts.size());
    }

    /**
     * add操作执行之前执行的方法
     *
     * @param joinPoint:包含action所有的相关配置信息和request等内置对象。
     * @throws Exception
     */
    public void doBeforeRemove(JoinPoint joinPoint) throws Exception {
        System.err.println("前置通知");

        Object[] parames = joinPoint.getArgs();//获取目标方法体参数
        // String params = parseParames(parames); //解析目标方法体的参数
        String className = joinPoint.getTarget().getClass().toString();//获取目标类名
        className = className.substring(className.indexOf("com"));
        String signature = joinPoint.getSignature().toString();//获取目标方法签名
        String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));
        //String modelName = getModelName(className); //根据类名获取所属的模块
        List<Map<String, String>> listOperConts = new ArrayList<Map<String, String>>();
        try {
            listOperConts = adminOptionContent(parames);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(className+"|"+methodName+"|"+listOperConts.size());
    }

    /**
     * 捕捉添加、更新、删除事件,获取对应方法,字段,值,耗时时间等属性,并存入操作日志
     * @param pjp
     * @return
     */
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        //System.out.println("进入方法---环绕通知");
        Object[] parames = pjp.getArgs();   //获取目标方法体参数 - 在操作执行前获取
        long begin = System.nanoTime();
        Object o = pjp.proceed();
        long end = System.nanoTime();
        Integer userId = userService.findLoginId();
        HttpServletRequest request = ServletActionContext.getRequest();
        String ip = getIpAddress(request);
        //System.out.println("退出方法---环绕通知");
        Long dt = System.currentTimeMillis() / 1000;
        int dtLast = (int) (end-begin) / 1000000;   //执行时长(ms)

        String targetName = pjp.getTarget().getClass().getName();
        if (targetName.indexOf("$$EnhancerByCGLIB$$") > -1) {  //如果是CGLIB动态生成的类
            try {
                targetName = targetName.substring(0,targetName.indexOf("$$"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        /*targetName.replace("hdao.impl", "bhh").replace("DAOImpl", "");
        Class targetClass = Class.forName(targetName);

        String tableName = HibernateConfigurationHelper.getTableName(targetClass);
        Map<String, String> map = HibernateConfigurationHelper.getComments(targetClass);

        System.err.println(tableName);*/
        //begin = System.nanoTime();
        List<Map<String, String>> listOperConts = new ArrayList<Map<String, String>>();
        String methodName = pjp.getSignature().getName();
        byte operType = getOperationType(methodName);
        try {
            if(operType==2) //更新操作
                listOperConts = getUpdateConts(parames);
            else
                listOperConts = getConts(parames);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] classNames = getClassNames(parames[0]);
        String tbName = classNames[0];
        String tbRemark = classNames[1];
        LogOper logOper = new LogOper();
        logOper.setUserId(userId);
        logOper.setIp(ip);
        logOper.setDt(dt.intValue());
        logOper.setDtLast(dtLast);
        logOper.setType(operType);
        logOper.setTbName(tbName);
        logOper.setTbRemark(tbRemark);
        logService.getLogOperDAO().saveLog(logOper);
        int logContId = logOper.getId();
        LogOperCont logOperCont;
        LogOperContDAO logOperContDAO = logService.getLogOperContDAO();
        for(Map<String, String> mapConts:listOperConts){
            logOperCont = new LogOperCont();
            logOperCont.setLogId(logContId);
            logOperCont.setFdName(mapConts.get("source"));
            logOperCont.setFdRemark(mapConts.get("remark"));
            logOperCont.setValOld(mapConts.get("val"));
            logOperCont.setValNew(mapConts.get("valNew"));
            logOperContDAO.saveLog(logOperCont);
        }
        //end = System.nanoTime();
        //System.out.println(methodName+"|"+((end - begin)/1000000));
        return o;
    }

    /**
     * 根据方法名称，映射操作类型
     *
     * @param methodName
     * @return
     */
    private byte getOperationType(String methodName) {
        byte type = 0;
        if (methodName.indexOf("add") > -1)
            type = 1;
        else if (methodName.indexOf("update") > -1)
            type = 2;
        else if (methodName.indexOf("remove") > -1)
            type = 3;
        return type;
    }

    /**
     * 根据包名查询检索其所属的模块
     * @param packageName 包名
     * @return 模块名称
     */
    private String getModelName(String packageName){
        String modelName = "";
        SAXReader reader = new SAXReader();
        try {
            //读取project.xml模块信息描述xml文档
            File proj = ResourceUtils.getFile("classpath:project.xml");
            Document doc = reader.read(proj);
            //获取文档根节点
            Element root = doc.getRootElement();
            //查询模块名称
            modelName = searchModelName(root, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelName;
    }

    /**
     * 采用递归方式根据包名逐级检索所属模块
     * @param element 元素节点
     * @param packageName 包名
     * @return 模块名称
     */
    private String searchModelName(Element element, String packageName){
        String modelName = searchModelNodes(element, packageName);
        //若将包名解析到最后的根目录后仍未检索到模块名称，则返回空
        if(packageName.lastIndexOf(".")==-1){
            return modelName;
        }
        //逐级检索模块名称
        if(modelName.equals("")){
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
            modelName = searchModelName(element, packageName);
        }
        return modelName;
    }


    /**
     * 根据xml文档逐个节点检索模块名称
     * @param element 节点元素
     * @param packageName 包名
     * @return 模块名称
     */
    @SuppressWarnings("unchecked")
    private String searchModelNodes(Element element, String packageName){

        String modelName = "";
        Element modules = element.element("modules");
        Iterator it = modules.elementIterator();
        if(!it.hasNext()){
            return modelName;
        }
        while (it.hasNext()) {
            Element model = (Element) it.next();
            String pack = model.attributeValue("packageName");
            String name = model.elementText("moduleCHPath");
            if(packageName.equals(pack)){
                modelName = name;
                return modelName;
            }
            if(modelName!=null && !modelName.equals("")){
                break;
            }
            modelName = searchModelNodes(model, packageName);
        }

        return modelName;
    }

    /**
     * 解析方法参数
     * @param parames 方法参数
     * @return 解析后的方法参数
     */
    private String parseParames(Object[] parames) {
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<parames.length; i++){
            if(parames[i] instanceof Object[] || parames[i] instanceof Collection){
                JSONArray json = JSONArray.fromObject(parames[i]);
                if(i==parames.length-1){
                    sb.append(json.toString());
                }else{
                    sb.append(json.toString() + ",");
                }
            }else{
                JSONObject json = JSONObject.fromObject(parames[i]);
                if(i==parames.length-1){
                    sb.append(json.toString());
                }else{
                    sb.append(json.toString() + ",");
                }
            }
        }
        String params = sb.toString();
        params = params.replaceAll("(\"\\w+\":\"\",)", "");
        params = params.replaceAll("(,\"\\w+\":\"\")", "");
        return params;
    }

    /**
     * 获取更新的字段及对应值
     * @param args 传值
     * @return 属性、值、注释
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getUpdateConts(Object[] args) throws Exception{
        List<Map<String, String>> listOperConts = new ArrayList<Map<String, String>>();   //存储所有更新的表字段内容
        if (args == null || args.length!=2)
            return null;
        List<Map<String, String>> listOperContsOld = getmapOperCons(args[0]);   //更新前数值
        List<Map<String, String>> listOperContsNew = getmapOperCons(args[1]);   //更新后数值
        Map<String, String> mapConsOld, mapConsNew;
        for(int i=0;i<listOperContsOld.size();i++){
            mapConsOld = listOperContsOld.get(i);
            mapConsNew = listOperContsNew.get(i);
            if(!mapConsOld.get("val").toString().equals(mapConsNew.get("val").toString())) {    //更新字段
                mapConsOld.put("valNew", mapConsNew.get("val"));
                listOperConts.add(mapConsOld);
            }
        }
        return listOperConts;
    }

    /**
     * 获取添加、删除的字段及对应值
     * @param args 传值
     * @return 属性、值、注释
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getConts(Object[] args) throws Exception{
        if (args == null || args.length==0)
            return null;
        List<Map<String, String>> listOperConts = getmapOperCons(args[0]);   //存储所有更新的表字段内容
        return listOperConts;
    }

    /**
     * 获取传值的属性、值、注释
     * @param args 传值
     * @return 传值属性、值、注释
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> adminOptionContent(Object[] args) throws Exception{

        List<Map<String, String>> listOperConts = new ArrayList<Map<String, String>>();   //存储所有更新的表字段内容
        Map<String, String> mapOperCons;
        if (args == null)
            return null;
        // 遍历参数对象
        for (Object info : args) {
            listOperConts = getmapOperCons(info);
        }
        return listOperConts;
    }

    /*
    * 解析
    * */
    private List<Map<String, String>> getmapOperCons(Object obj){
        List<Map<String, String>> listOperConts = new ArrayList<Map<String, String>>();
        Map<String, String> mapOperCons = new HashMap<String, String>();
        Class<? extends Object> clazz = obj.getClass();
        Object rsValue;
        MyAnnotation.MyFieldAnnotation myFieldAnnotation;
        String methodName;
        Map<String, String> mapFieldRemark = new HashMap<String, String>(); //字段注释
        Map<String, String> mapFieldSource = new HashMap<String, String>(); //字段原值
        String fieldName;   //字段名
        String firstField;  //字段名-第一个字符
        // 获取对象的所有声明的字段
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            try {
                myFieldAnnotation = field.getAnnotation(MyAnnotation.MyFieldAnnotation.class);
                if (myFieldAnnotation == null) //没有注释
                    continue;
                fieldName = field.getName();
                mapFieldRemark.put(fieldName, myFieldAnnotation.remark());
                mapFieldSource.put(fieldName, myFieldAnnotation.field());
                //mapOperCons.put("name", field.getName());
                //mapOperCons.put("remark", myFieldAnnotation.remark());
                //System.err.println(myFieldAnnotation.remark());
            } catch (Exception e) {
                continue;
            }
        }
        Method[] methods = clazz.getDeclaredMethods();  // 获取对象的所有方法
        String fieldSource;
        for (Method method : methods) {
            methodName = method.getName();
            if (methodName.indexOf("get") == -1)    // 不是get方法
                continue;
            try {
                rsValue = method.invoke(obj);       // 调用get方法，获取返回值
                if (rsValue == null) //没有返回值
                    continue;
                if(rsValue.toString().indexOf("com.wnwl.CPN2025.bhh")>-1) {
                    rsValue = getEntityId(rsValue);
                }
                mapOperCons = new HashMap<String, String>();
                mapOperCons.put("val", rsValue.toString());
                fieldName = methodName.substring(4, methodName.length());
                firstField = methodName.substring(3, 4).toLowerCase();
                fieldName = firstField+fieldName;
                mapOperCons.put("name", fieldName);
                fieldSource = mapFieldSource.get(fieldName);
                mapOperCons.put("source", "".equals(fieldSource.trim())?fieldName:fieldSource);
                mapOperCons.put("remark", mapFieldRemark.get(fieldName));
                listOperConts.add(mapOperCons);
            } catch (Exception e) {
                continue;
            }
        }
        return listOperConts;
    }

    /**
     *@Title: getEntityId
     *@Description: 获取关联表的id
     *@param obj 由反射机制获取的对象
     *@return id
     *@throws
     */
    private Integer getEntityId(Object obj){
        Integer id = null;
        try {
            id = 0;
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if(method.getName()=="getId")
                    return Integer.parseInt(method.invoke(obj).toString());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return id;
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