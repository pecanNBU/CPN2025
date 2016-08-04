package com.wnwl.CPN2025.action;

import com.wnwl.CPN2025.bhh.DType;
import com.wnwl.CPN2025.hdao.DTypeDAO;
import com.wnwl.CPN2025.service.SystemService;
import com.wnwl.CPN2025.service.UserService;
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
 * @ClassName:      数据词典类
 * @Description:
 * @Author:         Jenny
 * @CreateDate:     2016.7.26
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
 */
public class DTypeAction extends BaseAction {
    private SystemService systemService;
    private UserService userService;
    private DType dType;
    private List<DType> dTypes;
    private byte isLeaf;                //是否叶子分类
    private PageBean pageBean;
    private ArrayList<Object> rows;
    private HashMap<String, Object> map;
    private String hql;
    private String cond;
    private byte nodeState;
    private int jumpPage;
    private int pageSize;
    private long count;
    private String penName;
    private Integer typeId;
    private String typeName;
    private String enName;
    private byte flag;
    private Integer parentId;

    public String checkEnName(){			//验证设备类型-enName唯一性
        cond = " parent.enName='"+penName+"' and enName='"+enName+"'";
        if(typeId!=null)
            cond += " and id!="+typeId;
        dTypes = systemService.getDTypeDAO().findSelfObjects(cond);
        if(dTypes!=null&&dTypes.size()>0)
            flag = 1;
        else
            flag = 0;
        return SUCCESS;
    }

    public String checkTypeName(){			//验证类型名唯一性
        cond = "parent.enName='"+penName+"' and typeName='"+typeName+"'";
        if(typeId!=null)
            cond += " and id!="+typeId;
        dTypes = systemService.getDTypeDAO().findSelfObjects(cond);
        if(dTypes!=null&&dTypes.size()>0)
            flag = 1;
        else
            flag = 0;
        return SUCCESS;
    }

    public String showDType(){
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(),httpRequest.getRequestURI());
        dTypes = systemService.getDTypeDAO().findAllTypes();
        return SUCCESS;
    }

    public String json_dType(){
        hql = "from DType where parent.enName='"+penName+"'";
        pageBean = PageBean.getInstance();
        count = systemService.getDTypeDAO().getCountbyHql(hql);
        pageBean.setPageBean(count, jumpPage, pageSize, "");
        dTypes = systemService.getDTypeDAO().getListByHql(jumpPage, pageSize, hql);
        rows = transRows(dTypes);
        return SUCCESS;
    }

    private ArrayList<Object> transRows(List<DType> dtypes){
        this.rows = new ArrayList<Object>();
        List<Object> datas = new ArrayList<Object>();
        for(DType type:dtypes){
            datas = new ArrayList<Object>();
            datas.add(type.getId());
            datas.add(type.getTypeName());
            datas.add(type.getParent().getTypeName());
            datas.add(type.getIsLeaf()==1?"是":"否");
            datas.add(type.getEnName());
            if(nodeState==1)
                datas.add("<a class='aBtn aEdit' href='javascript:' onclick='edit("+type.getId()+");'>编辑</a>　" +
                        "<a class='aBtn aDel' href='javascript:' onclick='del("+type.getId()+");'>删除</a>");
            rows.add(datas);
        }
        return rows;
    }

    public String showDetailType(){
        dType = systemService.getDTypeDAO().findById(typeId);
        map = new HashMap<String, Object>();
        map.put("typeId", typeId);
        map.put("typeName", dType.getTypeName());
        map.put("penName", dType.getParent().getEnName());
        map.put("pName", dType.getParent().getTypeName());
        map.put("isLeaf", dType.getIsLeaf());
        map.put("enName", dType.getEnName());
        return SUCCESS;
    }

    public String addDType(){
        try {
            DTypeDAO dTypeDAO = systemService.getDTypeDAO();
            if(typeId==null)
                dType = new DType();
            else
                dType = dTypeDAO.findById(typeId);
            dType.setTypeName(typeName);
            dType.setParent(dTypeDAO.findFirstObject("from DType where enName = '"+penName+"'"));
            dType.setIsLeaf(isLeaf);
            dType.setEnName(enName);
            dTypeDAO.save(dType);
            if(typeId!=null){
                map = new HashMap<String,Object>();
                map.put("typeId", typeId);
                map.put("typeName", typeName);
                map.put("pName", dType.getParent().getTypeName());
                map.put("isLeaf", isLeaf==1?"是":"否");
                map.put("enName", enName);
                map.put("typeId", typeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String removeDType(){
        try {
            DTypeDAO dTypeDAO = systemService.getDTypeDAO();
            dType = dTypeDAO.findById(typeId);
            dTypeDAO.remove(dType);
            flag = 0;
        } catch (Exception e) {
            flag = 1;
            e.printStackTrace();
        }
        return SUCCESS;
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

    public byte getNodeState() {
        return nodeState;
    }

    public void setNodeState(byte nodeState) {
        this.nodeState = nodeState;
    }

    public void setIsLeaf(byte isLeaf) {
        this.isLeaf = isLeaf;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public byte getFlag() {
        return flag;
    }

    public List<DType> getDTypes() {
        return dTypes;
    }
}
