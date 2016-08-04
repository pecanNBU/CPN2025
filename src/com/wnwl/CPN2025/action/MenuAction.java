package com.wnwl.CPN2025.action;

import com.wnwl.CPN2025.bhh.SystemInfo;
import com.wnwl.CPN2025.bhh.TreeNode;
import com.wnwl.CPN2025.bhh.UserInfo;
import com.wnwl.CPN2025.service.SystemService;
import com.wnwl.CPN2025.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jenny on 16/5/31.
 */
public class MenuAction extends BaseAction {
    private SystemService systemService;
    private UserService userService;
    private UserInfo userInfo;
    private HashMap<String, Object> map;
    private String hql;
    private String cond;

    public String json_top() {   //top部分
        userInfo = userService.findLoginUser();
        map = new HashMap<String, Object>();
        map.put("userName", userInfo.getUserName());
        hql = "from SystemInfo order by dt desc";
        SystemInfo systemInfo = systemService.getSystemInfoDAO().findFirstObject(hql);
        if (systemInfo != null)
            map.put("projName", systemInfo.getName());
        cond = " type = 1 and parent.id!=id order by parent.id, sort"; //特指系统top部分右测的菜单
        List<TreeNode> listTreeNodes = systemService.getTreeNodeDAO().findSelfObjects(cond);
        List<Object> rows = transNodeDatas(listTreeNodes);
        map.put("listNodes", rows);
        return SUCCESS;
    }

    public String json_nav() {   //nav - 导航信息(只有一级菜单)
        map = new HashMap<String, Object>();
        cond = "type = 0 and parent.id!=id order by parent.id, sort";     //特指系统nav部分的菜单
        List<TreeNode> listTreeNodes = systemService.getTreeNodeDAO().findSelfObjects(cond);
        List<Object> rows = transNodeDatas(listTreeNodes);
        map.put("listNodes", rows);
        return SUCCESS;
    }

    private List<Object> transNodeDatas(List<TreeNode> listTreeNodes) {
        List<Object> rows = new ArrayList<Object>();
        if (listTreeNodes != null && listTreeNodes.size() > 0) {
            Map<String, Object> map1;
            for (TreeNode treeNode : listTreeNodes) {
                map1 = new HashMap<String, Object>();
                map1.put("icon", treeNode.getIcon());
                map1.put("name", treeNode.getName());
                map1.put("url", treeNode.getUrl());
                map1.put("id", treeNode.getId());
                map1.put("pId", treeNode.getParent().getId());
                map1.put("isParent", treeNode.getParent().getId() == treeNode.getParent().getParent().getId() ? 1 : 0);
                rows.add(map1);
            }
        }
        return rows;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
