package com.wnwl.CPN2025.action;

import com.wnwl.CPN2025.bhh.FormCaliFlow;
import com.wnwl.CPN2025.bhh.FormCaliNoise;
import com.wnwl.CPN2025.bhh.FormCaliPm;
import com.wnwl.CPN2025.bhh.FormNoiseData;
import com.wnwl.CPN2025.hdao.*;
import com.wnwl.CPN2025.service.OperService;
import com.wnwl.CPN2025.service.UserService;
import com.wnwl.CPN2025.util.DtUtil;
import com.wnwl.CPN2025.util.PageBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Simple to Introduction
 *
 * @ProjectName: 建设工程颗粒物与噪声在线监测系统
 * @Package: com.wnwl.CPN2025.action
 * @ClassName: 运维表单类
 * @Description: 运维对应的所有表单
 * @Author: Jenny
 * @CreateDate: 2016.8.2
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: V1.0
 */

public class FormAction extends BaseAction {
    private OperService operService;
    private UserService userService;
    private FormCaliNoiseDAO formCaliNoiseDAO;  //噪声在线监测仪校准记录-基础表
    private FormNoiseDataDAO formNoiseDataDAO;  //噪声在线检测仪校准记录-数据表
    private FormCaliFlowDAO formCaliFlowDAO;    //流量校准记录表
    private FormCaliPmDAO formCaliPmDAO;        //颗粒物检测仪校准记录表
    private FormCommCheckDAO formCommCheckDAO;  //通信维护记录表
    private FormDataCheckDAO formDataCheckDAO;  //数据核查记录表
    private FormDevChangeDAO formDevChangeDAO;  //设备更换记录表
    private FormDevCleanDAO formDevCleanDAO;    //清洗保养记录表
    private FormDevFaultDAO formDevFaultDAO;    //故障修复记录表
    private FormDevMoveDAO formDevMoveDAO;      //设备移机记录表
    private FormDevRepairDAO formDevRepairDAO;  //检修维修记录表
    private FormFeedbackDAO formFeedbackDAO;    //反馈意见记录表
    private FormPollDayDAO formPollDayDAO;      //巡检记录-日巡检
    private FormPollMonthDAO formPollMonthDAO;  //巡检记录-周汇报
    private FormPollWeekDAO formPollWeekDAO;    //巡检记录-月巡检
    private FormPollYearDAO formPollYearDAO;    //巡检记录-年报表
    private FormQualCheckDAO formQualCheckDAO;  //质量抽检/对比测试记录表
    private FormTraceValDAO formTraceValDAO;    //量值溯源记录表
    private ArrayList<Object> rows;
    private HashMap<String, Object> map;
    private PageBean pageBean;
    private String cond;
    private byte flag;
    private int jumpPage;
    private int pageSize;
    private long count;
    private byte nodeState;
    private Integer id;
    private String consName;
    private String devName;
    private String depName;
    private String statBjh;
    private String devNo;
    private Date dtCali;
    private String checkDep;
    private String checkUser;
    private Date dtCheck;
    private Float beDataSet;
    private Float beData1;
    private Float beData2;
    private Float beData3;
    private Float beDataMis;
    private Boolean beIsAdjust;
    private Float afDataSet;
    private Float afData1;
    private Float afData2;
    private Float afData3;
    private Float afDataMis;
    private Boolean afIsPass;
    private Float beDataZero;
    private Float beData;
    private Boolean beIsQual;
    private Float afDataSpan;
    private Float afData;
    private Boolean afIsQual;
    private String devType;
    private String caliName;
    private String caliNo;
    private Integer formNoiseId;
    private Float data1;
    private Float data2;
    private Float dataMis;
    private Date dt;

    /**
     * @param
     * @return 返回showCaliFlow.jsp
     * @throws
     * @Title: showCaliFlow
     * @Description: 流量校准记录表
     */
    public String showCaliFlow() {
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(), httpRequest.getRequestURI());
        return SUCCESS;
    }

    public String json_caliFlow() {
        formCaliFlowDAO = operService.getFormCaliFlowDAO();
        cond = "id>0 order by dtCheck desc";
        map = formCaliFlowDAO.getPageJsonByCond(cond, jumpPage, pageSize);
        pageBean = (PageBean) map.get("pageBean");
        List<FormCaliFlow> formCaliFlows = (List<FormCaliFlow>) map.get("list");
        transRows_formCaliFlows(formCaliFlows);

        /*formCaliPmDAO = operService.getFormCaliPmDAO();
        pageBean = PageBean.getInstance();
        cond = "id>0 order by dtCheck desc";
        count = formCaliPmDAO.getCountbyCond(cond);
        pageBean.setPageBean(count, jumpPage, pageSize, "");
        List<FormCaliPm> formCaliPms = formCaliPmDAO.getList(jumpPage, pageSize, cond);
        transRows_formCaliPm(formCaliPms);*/
        return SUCCESS;
    }

    private void transRows_formCaliFlows(List<FormCaliFlow> formCaliFlows) {
        rows = new ArrayList<Object>();
        List<Object> datas;
        for (FormCaliFlow form : formCaliFlows) {
            datas = new ArrayList<Object>();
            datas.add(form.getId());
            datas.add(form.getConsName());
            datas.add(form.getDevName());
            datas.add(form.getDepName());
            datas.add(form.getCheckUser());
            datas.add(DtUtil.transDtToStr(form.getDtCheck()));
            datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail(" + form.getId() + ");'>详情</a>");
            rows.add(datas);
        }
    }

    public String showDetailCaliFlow() {
        formCaliFlowDAO = operService.getFormCaliFlowDAO();
        FormCaliFlow formCaliFlow = formCaliFlowDAO.findById(id);
        map = getEntityMap(formCaliFlow);
        /*map = new HashMap<String, Object>();
        map.put("id", formCaliFlow.getId());
        map.put("consName", formCaliFlow.getConsName());
        map.put("devName", formCaliFlow.getDevName());
        map.put("depName", formCaliFlow.getDepName());
        map.put("statBjh", formCaliFlow.getStatBjh());
        map.put("devNo", formCaliFlow.getDevNo());
        map.put("dtCali", DtUtil.transDtToStr(formCaliFlow.getDtCali()));
        map.put("checkDep", formCaliFlow.getCheckDep());
        map.put("checkUser", formCaliFlow.getCheckUser());
        map.put("dtCheck", DtUtil.transDtToStr(formCaliFlow.getDtCheck()));
        map.put("beDataSet", formCaliFlow.getBeDataSet());
        map.put("beData1", formCaliFlow.getBeData1());
        map.put("beData2", formCaliFlow.getBeData2());
        map.put("beData3", formCaliFlow.getBeData3());
        map.put("beDataMis", formCaliFlow.getBeDataMis());
        map.put("beIsAdjust", formCaliFlow.getBeIsAdjust());
        map.put("afDataSet", formCaliFlow.getAfDataSet());
        map.put("afData1", formCaliFlow.getAfData1());
        map.put("afData2", formCaliFlow.getAfData2());
        map.put("afData3", formCaliFlow.getAfData3());
        map.put("afDataMis", formCaliFlow.getAfDataMis());
        map.put("afIsPass", formCaliFlow.getAfIsPass());*/
        return SUCCESS;
    }

    public String addCaliFlow() {
        try {
            FormCaliFlow formCaliFlow = new FormCaliFlow();
            formCaliFlow.setConsName(consName);
            formCaliFlow.setDevName(devName);
            formCaliFlow.setDepName(depName);
            formCaliFlow.setStatBjh(statBjh);
            formCaliFlow.setDevNo(devNo);
            formCaliFlow.setDtCali(DtUtil.transDtToInt(dtCali));
            formCaliFlow.setCheckDep(checkDep);
            formCaliFlow.setCheckUser(checkUser);
            formCaliFlow.setDtCheck(DtUtil.transDtToInt(dtCheck));
            formCaliFlow.setBeDataSet(beDataSet);
            formCaliFlow.setBeData1(beData1);
            formCaliFlow.setBeData2(beData2);
            formCaliFlow.setBeData3(beData3);
            formCaliFlow.setBeDataMis(beDataMis);
            formCaliFlow.setBeIsAdjust(beIsAdjust);
            formCaliFlow.setAfDataSet(afDataSet);
            formCaliFlow.setAfData1(afData1);
            formCaliFlow.setAfData2(afData2);
            formCaliFlow.setAfData3(afData3);
            formCaliFlow.setAfDataMis(afDataMis);
            formCaliFlow.setAfIsPass(afIsPass);
            formCaliFlowDAO = operService.getFormCaliFlowDAO();
            formCaliFlowDAO.addEntity(formCaliFlow);
            flag = 0;
        } catch (Exception e) {
            e.printStackTrace();
            flag = -1;
        }
        return SUCCESS;
    }

    /**
     * @param
     * @return 返回showCaliPm.jsp
     * @throws
     * @Title: showCaliPm
     * @Description: 流量校准记录表
     */
    public String showCaliPm() {
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(), httpRequest.getRequestURI());
        return SUCCESS;
    }

    public String json_caliPm() {
        formCaliPmDAO = operService.getFormCaliPmDAO();
        pageBean = PageBean.getInstance();
        cond = "id>0 order by dtCheck desc";
        count = formCaliPmDAO.getCountbyCond(cond);
        pageBean.setPageBean(count, jumpPage, pageSize, "");
        List<FormCaliPm> formCaliPms = formCaliPmDAO.getList(jumpPage, pageSize, cond);
        transRows_formCaliPm(formCaliPms);
        return SUCCESS;
    }

    private void transRows_formCaliPm(List<FormCaliPm> formCaliPms) {
        rows = new ArrayList<Object>();
        List<Object> datas;
        for (FormCaliPm form : formCaliPms) {
            datas = new ArrayList<Object>();
            datas.add(form.getId());
            datas.add(form.getConsName());
            datas.add(form.getDevName());
            datas.add(form.getDepName());
            datas.add(form.getCheckUser());
            datas.add(DtUtil.transDtToStr(form.getDtCheck()));
            datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail(" + form.getId() + ");'>详情</a>");
            rows.add(datas);
        }
    }

    public String showDetailCaliPm() {
        formCaliPmDAO = operService.getFormCaliPmDAO();
        FormCaliPm formCaliPm = formCaliPmDAO.findById(id);
        map = new HashMap<String, Object>();
        map.put("id", formCaliPm.getId());
        map.put("consName", formCaliPm.getConsName());
        map.put("devName", formCaliPm.getDevName());
        map.put("depName", formCaliPm.getDepName());
        map.put("statBjh", formCaliPm.getStatBjh());
        map.put("devNo", formCaliPm.getDevNo());
        map.put("dtCali", DtUtil.transDtToStr(formCaliPm.getDtCali()));
        map.put("checkDep", formCaliPm.getCheckDep());
        map.put("checkUser", formCaliPm.getCheckUser());
        map.put("dtCheck", DtUtil.transDtToStr(formCaliPm.getDtCheck()));
        map.put("beDataZero", formCaliPm.getBeDataZero());
        map.put("beData1", formCaliPm.getBeData1());
        map.put("beData2", formCaliPm.getBeData2());
        map.put("beData3", formCaliPm.getBeData3());
        map.put("beData", formCaliPm.getBeData());
        map.put("beIsQual", formCaliPm.getBeIsQual());
        map.put("afDataSpan", formCaliPm.getAfDataSpan());
        map.put("afData1", formCaliPm.getAfData1());
        map.put("afData2", formCaliPm.getAfData2());
        map.put("afData3", formCaliPm.getAfData3());
        map.put("afData", formCaliPm.getAfData());
        map.put("afIsQual", formCaliPm.getAfIsQual());
        return SUCCESS;
    }

    public String addCaliPm() {
        try {
            FormCaliPm formCaliPm = new FormCaliPm();
            formCaliPm.setConsName(consName);
            formCaliPm.setDevName(devName);
            formCaliPm.setDepName(depName);
            formCaliPm.setStatBjh(statBjh);
            formCaliPm.setDevNo(devNo);
            formCaliPm.setDtCali(DtUtil.transDtToInt(dtCali));
            formCaliPm.setCheckDep(checkDep);
            formCaliPm.setCheckUser(checkUser);
            formCaliPm.setDtCheck(DtUtil.transDtToInt(dtCheck));
            formCaliPm.setBeDataZero(beDataZero);
            formCaliPm.setBeData1(beData1);
            formCaliPm.setBeData2(beData2);
            formCaliPm.setBeData3(beData3);
            formCaliPm.setBeData(beData);
            formCaliPm.setBeIsQual(beIsQual);
            formCaliPm.setAfDataSpan(afDataSpan);
            formCaliPm.setAfData1(afData1);
            formCaliPm.setAfData2(afData2);
            formCaliPm.setAfData3(afData3);
            formCaliPm.setAfData(afData);
            formCaliPm.setAfIsQual(afIsQual);
            formCaliPmDAO = operService.getFormCaliPmDAO();
            formCaliPmDAO.addEntity(formCaliPm);
            flag = 0;
        } catch (Exception e) {
            e.printStackTrace();
            flag = -1;
        }
        return SUCCESS;
    }

    /**
     * @param
     * @return 返回showCaliNoise.jsp
     * @throws
     * @Title: showCaliNoise
     * @Description: 噪声在线监测仪校准记录-基础表
     */
    public String showCaliNoise() {
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(), httpRequest.getRequestURI());
        return SUCCESS;
    }

    public String json_caliNoise() {
        formCaliNoiseDAO = operService.getFormCaliNoiseDAO();
        pageBean = PageBean.getInstance();
        cond = "id>0 order by dtCheck desc";
        count = formCaliNoiseDAO.getCountbyCond(cond);
        pageBean.setPageBean(count, jumpPage, pageSize, "");
        List<FormCaliNoise> formCaliNoises = formCaliNoiseDAO.getList(jumpPage, pageSize, cond);
        transRows_formCaliNoise(formCaliNoises);
        return SUCCESS;
    }

    private void transRows_formCaliNoise(List<FormCaliNoise> formCaliNoises) {
        rows = new ArrayList<Object>();
        List<Object> datas;
        for (FormCaliNoise form : formCaliNoises) {
            datas = new ArrayList<Object>();
            datas.add(form.getId());
            datas.add(form.getConsName());
            datas.add(form.getDevName());
            datas.add(form.getCaliName());
            datas.add(form.getDepName());
            datas.add(form.getCheckUser());
            datas.add(DtUtil.transDtToStr(form.getDtCheck()));
            datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail(" + form.getId() + ");'>详情</a>");
            rows.add(datas);
        }
    }

    public String showDetailCaliNoise() {
        formCaliNoiseDAO = operService.getFormCaliNoiseDAO();
        FormCaliNoise formCaliNoise = formCaliNoiseDAO.findById(id);
        map = new HashMap<String, Object>();
        map.put("id", formCaliNoise.getId());
        map.put("consName", formCaliNoise.getConsName());
        map.put("devName", formCaliNoise.getDevName());
        map.put("depName", formCaliNoise.getDepName());
        map.put("statBjh", formCaliNoise.getStatBjh());
        map.put("devType", formCaliNoise.getDevType());
        map.put("devNo", formCaliNoise.getDevNo());
        map.put("caliName", formCaliNoise.getCaliName());
        map.put("caliNo", formCaliNoise.getCaliNo());
        map.put("checkDep", formCaliNoise.getCheckDep());
        map.put("checkUser", formCaliNoise.getCheckUser());
        map.put("dtCheck", DtUtil.transDtToStr(formCaliNoise.getDtCheck()));
        return SUCCESS;
    }

    public String addCaliNoise() {
        try {
            FormCaliNoise formCaliNoise = new FormCaliNoise();
            formCaliNoise.setConsName(consName);
            formCaliNoise.setDevName(devName);
            formCaliNoise.setDepName(depName);
            formCaliNoise.setStatBjh(statBjh);
            formCaliNoise.setDevType(devType);
            formCaliNoise.setDevNo(devNo);
            formCaliNoise.setCaliName(caliName);
            formCaliNoise.setCaliNo(caliNo);
            formCaliNoise.setCheckDep(checkDep);
            formCaliNoise.setCheckUser(checkUser);
            formCaliNoise.setDtCheck(DtUtil.transDtToInt(dtCheck));
            formCaliNoiseDAO = operService.getFormCaliNoiseDAO();
            formCaliNoiseDAO.addEntity(formCaliNoise);
            flag = 0;
        } catch (Exception e) {
            e.printStackTrace();
            flag = -1;
        }
        return SUCCESS;
    }

    /**
     * @param
     * @return 返回showNoiseData.jsp
     * @throws
     * @Title: showNoiseData
     * @Description: 噪声在线监测仪校准记录-数据表
     */
    public String showNoiseData() {
        nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(), httpRequest.getRequestURI());
        return SUCCESS;
    }

    public String json_NoiseData() {
        formNoiseDataDAO = operService.getFormNoiseDataDAO();
        pageBean = PageBean.getInstance();
        cond = "id>0 order by dtCheck desc";
        count = formNoiseDataDAO.getCountbyCond(cond);
        pageBean.setPageBean(count, jumpPage, pageSize, "");
        List<FormNoiseData> formNoiseDatas = formNoiseDataDAO.getList(jumpPage, pageSize, cond);
        transRows_formNoiseData(formNoiseDatas);
        return SUCCESS;
    }

    private void transRows_formNoiseData(List<FormNoiseData> formNoiseDatas) {
        rows = new ArrayList<Object>();
        List<Object> datas;
        for (FormNoiseData form : formNoiseDatas) {
            datas = new ArrayList<Object>();
            datas.add(form.getId());
            datas.add(form.getFormNoiseId());
            datas.add(DtUtil.transDtToStr(form.getDt()));
            datas.add(form.getData1());
            datas.add(form.getData2());
            datas.add(form.getDataMis());
            datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail(" + form.getId() + ");'>详情</a>");
            rows.add(datas);
        }
    }

    public String showDetailNoiseData() {
        formNoiseDataDAO = operService.getFormNoiseDataDAO();
        FormNoiseData formNoiseData = formNoiseDataDAO.findById(id);
        map = new HashMap<String, Object>();
        map.put("id", formNoiseData.getId());
        map.put("formNoiseId", formNoiseData.getFormNoiseId());
        map.put("dt", formNoiseData.getDt());
        map.put("data1", formNoiseData.getData1());
        map.put("data2", formNoiseData.getData2());
        map.put("dataMis", formNoiseData.getDataMis());
        return SUCCESS;
    }

    public String addNoiseData() {
        try {
            FormNoiseData formNoiseData = new FormNoiseData();
            formNoiseData.setFormNoiseId(formNoiseId);
            formNoiseData.setDt(DtUtil.transDtToInt(dt));
            formNoiseData.setData1(data1);
            formNoiseData.setData2(data2);
            formNoiseData.setDataMis(dataMis);
            formNoiseDataDAO = operService.getFormNoiseDataDAO();
            formNoiseDataDAO.addEntity(formNoiseData);
            flag = 0;
        } catch (Exception e) {
            e.printStackTrace();
            flag = -1;
        }
        return SUCCESS;
    }


    public byte getNodeState() {
        return nodeState;
    }

    public void setNodeState(byte nodeState) {
        this.nodeState = nodeState;
    }

    public void setOperService(OperService operService) {
        this.operService = operService;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Object> getRows() {
        return rows;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public PageBean getPageBean() {
        return pageBean;
    }

    public byte getFlag() {
        return flag;
    }

    public void setConsName(String consName) {
        this.consName = consName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setStatBjh(String statBjh) {
        this.statBjh = statBjh;
    }

    public void setDevNo(String devNo) {
        this.devNo = devNo;
    }

    public void setDtCali(Date dtCali) {
        this.dtCali = dtCali;
    }

    public void setCheckDep(String checkDep) {
        this.checkDep = checkDep;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public void setDtCheck(Date dtCheck) {
        this.dtCheck = dtCheck;
    }

    public void setBeDataSet(Float beDataSet) {
        this.beDataSet = beDataSet;
    }

    public void setBeData1(Float beData1) {
        this.beData1 = beData1;
    }

    public void setBeData2(Float beData2) {
        this.beData2 = beData2;
    }

    public void setBeData3(Float beData3) {
        this.beData3 = beData3;
    }

    public void setBeDataMis(Float beDataMis) {
        this.beDataMis = beDataMis;
    }

    public void setBeIsAdjust(Boolean beIsAdjust) {
        this.beIsAdjust = beIsAdjust;
    }

    public void setAfDataSet(Float afDataSet) {
        this.afDataSet = afDataSet;
    }

    public void setAfData1(Float afData1) {
        this.afData1 = afData1;
    }

    public void setAfData2(Float afData2) {
        this.afData2 = afData2;
    }

    public void setAfData3(Float afData3) {
        this.afData3 = afData3;
    }

    public void setAfDataMis(Float afDataMis) {
        this.afDataMis = afDataMis;
    }

    public void setAfIsPass(Boolean afIsPass) {
        this.afIsPass = afIsPass;
    }
}
