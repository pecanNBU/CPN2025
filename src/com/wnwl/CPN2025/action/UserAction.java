package com.wnwl.CPN2025.action;

import com.google.gson.Gson;
import com.wnwl.CPN2025.bhh.DepInfo;
import com.wnwl.CPN2025.bhh.UserDetail;
import com.wnwl.CPN2025.bhh.UserInfo;
import com.wnwl.CPN2025.hdao.DepInfoDAO;
import com.wnwl.CPN2025.hdao.UserDetailDAO;
import com.wnwl.CPN2025.hdao.UserInfoDAO;
import com.wnwl.CPN2025.service.UserService;
import com.wnwl.CPN2025.util.Encyptions;
import com.wnwl.CPN2025.util.PageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Simple to Introduction
 * @ProjectName:    建设工程颗粒物与噪声在线监测系统
 * @Package:        com.wnwl.CPN2025.action
 * @ClassName:      用户、单位相关类
 * @Description:    单位管理,用户管理
 * @Author:         Jenny
 * @CreateDate:     2016.7.30
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
 */
public class UserAction extends BaseAction {
    private UserService userService;
    private DepInfoDAO depInfoDAO;
    private UserInfoDAO userInfoDAO;
    private UserDetailDAO userDetailDAO;
    private List<UserInfo> userInfos;
    private List<DepInfo> depInfos;
    private UserInfo userInfo;
    private DepInfo depInfo;
    private UserDetail userDetail;
    private Encyptions ens;
    private PageBean pageBean;
    private ArrayList<Object> rows;
    private HashMap<String, Object> map;
    private String jsonData;
    private String hql;
    private String cond;
    private byte nodeState;
    private int jumpPage;
    private int pageSize;
    private long count;
    private byte flag;
    private Object entityOld;
    private Integer id;
    private String depName;
    private String chargeUser;
    private String address;
    private String mobilephone;
    private String telephone;
    private String fax;
    private String web;
    private String email;
    private byte type;
    private String userName;
    private String loginName;
    private String loginPwd;
    private byte state;
    private Integer depId;
    private String qq;
    private String picHead;

    /**
     *@Title: showDepInfo
     *@Description: 单位浏览
     *@param
     *@return 返回showDepInfo.jsp
     *@throws
     */
    public String showDepInfo(){
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(),httpRequest.getRequestURI());
        return SUCCESS;
    }

    public String json_depInfo(){
        cond = "id >0 order by type";
        pageBean = PageBean.getInstance();
        count = userService.getDepInfoDAO().getCountbyCond(cond);
        pageBean.setPageBean(count, jumpPage, pageSize, "");
        depInfos = userService.getDepInfoDAO().getList(jumpPage, pageSize, cond);
        rows = transRows_depInfo(depInfos);
        return SUCCESS;
    }

    private ArrayList<Object> transRows_depInfo(List<DepInfo> depInfos){
        this.rows = new ArrayList<Object>();
        List<Object> datas = new ArrayList<Object>();
        for(DepInfo depInfo:depInfos){
            datas = new ArrayList<Object>();
            datas.add(depInfo.getId());
            datas.add(depInfo.transType());
            datas.add(depInfo.getDepName());
            datas.add(depInfo.getChargeUser());
            datas.add("");
            if(nodeState==1)
                datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail("+depInfo.getId()+");'>详情</a>　" +
                        "<a class='aBtn aEdit' href='javascript:' onclick='edit("+depInfo.getId()+");'>编辑</a>　" +
                        "<a class='aBtn aDel' href='javascript:' onclick='del("+depInfo.getId()+");'>删除</a>");
            else
                datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail("+depInfo.getId()+");'>详情</a>");
            rows.add(datas);
        }
        return rows;
    }

    public String showDetailDepInfo(){
        depInfo = userService.getDepInfoDAO().findById(id);
        map = new HashMap<String, Object>();
        map.put("id", depInfo.getId());
        map.put("depName", depInfo.getDepName());
        map.put("chargeUser", depInfo.getChargeUser());
        map.put("address", depInfo.getAddress());
        map.put("mobilephone", depInfo.getMobilephone());
        map.put("telephone", depInfo.getTelephone());
        map.put("fax", depInfo.getFax());
        map.put("web", depInfo.getWeb());
        map.put("email", depInfo.getEmail());
        map.put("type", depInfo.getType());
        map.put("type_", depInfo.transType());
        return SUCCESS;
    }

    public String addDepInfo(){
        depInfoDAO = userService.getDepInfoDAO();
        if(id==null)
            depInfo = new DepInfo();
        else {
            depInfo = depInfoDAO.findById(id);
            try {
                entityOld = depInfo.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        depInfo.setDepName(depName);
        depInfo.setChargeUser(chargeUser);
        depInfo.setAddress(address);
        depInfo.setMobilephone(mobilephone);
        depInfo.setTelephone(telephone);
        depInfo.setFax(fax);
        depInfo.setWeb(web);
        depInfo.setEmail(email);
        depInfo.setType(type);
        if(id==null)
            depInfoDAO.addEntity(depInfo);
        else
            depInfoDAO.updateEntity(entityOld, depInfo);
        if(id!=null){
            map = new HashMap<String, Object>();
            map.put("id", depInfo.getId());
            map.put("depName", depInfo.getDepName());
            map.put("chargeUser", depInfo.getChargeUser());
            map.put("type_", depInfo.transType());
        }
        return SUCCESS;
    }

    public String removeDepInfo(){
        try {
            depInfoDAO = userService.getDepInfoDAO();
            depInfo = depInfoDAO.findById(id);
            depInfoDAO.removeEntity(depInfo);
            flag = 0;
        } catch (Exception e) {
            e.printStackTrace();
            flag = 1;
        }
        return SUCCESS;
    }
    
    /**
     *@Title: showUserInfo
     *@Description: 单位浏览
     *@param
     *@return 返回showUserInfo.jsp
     *@throws
     */
    public String showUserInfo(){
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(),httpRequest.getRequestURI());
        cond = "id>=0 order by type";
        depInfos = userService.getDepInfoDAO().findSelfObjects(cond);
        map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        rows = new ArrayList<Object>();
        byte type1 = -1, type2 = -1;
        for(DepInfo depInfo:depInfos){
            type1 = depInfo.getType();
            type2 = type2==-1?type1:type2;
            if(type1!=type2){
                map.put(type2+"", rows);
                type2 = type1;
                rows = new ArrayList<Object>();
            }
            map1 = new HashMap<String, Object>();
            map1.put("id", depInfo.getId());
            map1.put("name", depInfo.getDepName());
            rows.add(map1);
        }
        map.put(type1+"", rows);
        jsonData = toJson(map);
        return SUCCESS;
    }

    public String json_userInfo(){
        cond = "id >0 order by depInfo.type";
        pageBean = PageBean.getInstance();
        count = userService.getUserInfoDAO().getCountbyCond(cond);
        pageBean.setPageBean(count, jumpPage, pageSize, "");
        userInfos = userService.getUserInfoDAO().getList(jumpPage, pageSize, cond);
        rows = transRows_userInfo(userInfos);
        return SUCCESS;
    }

    private ArrayList<Object> transRows_userInfo(List<UserInfo> userInfos){
        this.rows = new ArrayList<Object>();
        List<Object> datas = new ArrayList<Object>();
        for(UserInfo userInfo:userInfos){
            datas = new ArrayList<Object>();
            datas.add(userInfo.getId());
            datas.add(userInfo.getUserName());
            datas.add(userInfo.getLoginName());
            datas.add(userInfo.getDepInfo().getDepName());
            datas.add("");
            if(nodeState==1)
                datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail("+userInfo.getId()+");'>详情</a>　" +
                        "<a class='aBtn aEdit' href='javascript:' onclick='edit("+userInfo.getId()+");'>编辑</a>　" +
                        "<a class='aBtn aDel' href='javascript:' onclick='del("+userInfo.getId()+");'>删除</a>");
            else
                datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail("+userInfo.getId()+");'>详情</a>");
            rows.add(datas);
        }
        return rows;
    }

    public String showDetailUserInfo(){
        userInfo = userService.getUserInfoDAO().findById(id);
        map = new HashMap<String, Object>();
        map.put("id", userInfo.getId());
        map.put("userName", userInfo.getUserName());
        map.put("loginName", userInfo.getLoginName());
        map.put("depName", userInfo.getDepInfo().getDepName());
        map.put("state", userInfo.getState());
        map.put("state_", userInfo.transState());
        userDetailDAO = userService.getUserDetailDAO();
        hql = "from UserDetail where userInfo.id="+id;
        userDetail = userDetailDAO.findFirstObject(hql);
        map.put("mobilephone", userDetail.getMobilephone());
        map.put("telephone", userDetail.getTelephone());
        map.put("email", userDetail.getEmail());
        map.put("qq", userDetail.getQq());
        map.put("picHead", userDetail.getPicHead());
        return SUCCESS;
    }

    public String addUserInfo(){
        try {
            flag = 0;
            userInfoDAO = userService.getUserInfoDAO();
            userDetailDAO = userService.getUserDetailDAO();
            if(id==null) {
                userInfo = new UserInfo();
                loginPwd = "".equals(loginPwd)?"123456":loginPwd;
            }
            else {
                flag = 1;
                userInfo = userInfoDAO.findById(id);
                entityOld = userInfo.clone();
            }
            userInfo.setUserName(userName);
            userInfo.setLoginName(loginName);
            ens = new Encyptions();
            userInfo.setLoginPwd(ens.encryptSHA(loginPwd));
            userInfo.setState(state);
            userInfo.setDepInfo(userService.getDepInfoDAO().findById(depId));
            if(id==null)
                userInfoDAO.addEntity(userInfo);
            else
                userInfoDAO.updateEntity(entityOld, userInfo);
            hql = "from UserDetail where userInfo.id="+userInfo.getId();
            userDetail = userDetailDAO.findFirstObject(hql);
            if(userDetail==null)
                userDetail = new UserDetail();
            else
                entityOld = userDetail.clone();
            userDetail.setUserInfo(userInfo);
            userDetail.setEmail(email);
            userDetail.setTelephone(telephone);
            userDetail.setMobilephone(mobilephone);
            userDetail.setPicHead(picHead);
            userDetail.setQq(qq);
            if(userDetail.getId()==null)
                userDetailDAO.addEntity(userDetail);
            else
                userDetailDAO.updateEntity(entityOld, userDetail);
            if(id!=null){
                map = new HashMap<String, Object>();
                map.put("id", userInfo.getId());
                map.put("userName", userInfo.getUserName());
                map.put("loginName", userInfo.getLoginName());
                map.put("depName", userInfo.getDepInfo().getDepName());
                map.put("state", userInfo.transState());
            }
        } catch (Exception e) {
            flag = -1;
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String removeUserInfo(){
        try {
            userInfoDAO = userService.getUserInfoDAO();
            userInfo = userInfoDAO.findById(id);
            userInfoDAO.removeEntity(userInfo);
            flag = 0;
        } catch (Exception e) {
            e.printStackTrace();
            flag = 1;
        }
        return SUCCESS;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setJumpPage(int jumpPage) {
        this.jumpPage = jumpPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public byte getNodeState() {
        return nodeState;
    }

    public void setNodeState(byte nodeState) {
        this.nodeState = nodeState;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setPicHead(String picHead) {
        this.picHead = picHead;
    }

    public String getJsonData() {
        return jsonData;
    }
}
