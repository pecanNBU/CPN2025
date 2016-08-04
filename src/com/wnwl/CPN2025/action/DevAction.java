package com.wnwl.CPN2025.action;

import com.wnwl.CPN2025.bhh.RegInfo;
import com.wnwl.CPN2025.hdao.RegInfoDAO;
import com.wnwl.CPN2025.service.RegService;
import com.wnwl.CPN2025.service.UserService;
import com.wnwl.CPN2025.util.PageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Simple to Introduction
 * @ProjectName:    建设工程颗粒物与噪声在线监测系统
 * @Package:        com.wnwl.CPN2025.action
 * @ClassName:      设备相关类
 * @Description:    设备配置、参数配置
 * @Author:         Jenny
 * @CreateDate:     2016.8.4
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
*/
public class DevAction extends BaseAction {
    private RegService regService;
    private UserService userService;
    private RegInfoDAO regInfoDAO;
    private RegInfo regInfo;
    private List<RegInfo> regInfos;
    private PageBean pageBean;
    private ArrayList<Object> rows;
    private HashMap<String, Object> map;
    private String hql;
    private String cond;
    private byte nodeState;
    private int jumpPage;
    private int pageSize;
    private long count;

    /**
     *@Title:       showRegInfo
     *@Description: 监测设备浏览
     *@param
     *@return       返回showRegInfo.jsp
     *@throws
     */
    public String showRegInfo(){
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(),httpRequest.getRequestURI());
        return SUCCESS;
    }

    public String json_regInfo(){

        return SUCCESS;
    }

    public byte getNodeState() {
        return nodeState;
    }

    public void setNodeState(byte nodeState) {
        this.nodeState = nodeState;
    }

    public void setRegService(RegService regService) {
        this.regService = regService;
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

    public PageBean getPageBean() {
        return pageBean;
    }

    public ArrayList<Object> getRows() {
        return rows;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }
}
