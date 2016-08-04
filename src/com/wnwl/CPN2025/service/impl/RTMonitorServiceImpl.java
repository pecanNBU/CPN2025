package com.wnwl.CPN2025.service.impl;

import com.wnwl.CPN2025.bhh.DType;
import com.wnwl.CPN2025.hdao.*;
import com.wnwl.CPN2025.service.RTMonitorService;

public class RTMonitorServiceImpl extends BaseServiceImpl<DType,Integer> implements RTMonitorService {
    private RegInfoDAO regInfoDAO;      //设备基础数据
    private RegParamDAO regParamDAO;    //设备参数数据
    private ConsInfoDAO consInfoDAO;      //工地信息

    public RegInfoDAO getRegInfoDAO() {
        return regInfoDAO;
    }

    public void setRegInfoDAO(RegInfoDAO regInfoDAO) {
        this.regInfoDAO = regInfoDAO;
    }

    public RegParamDAO getRegParamDAO() {
        return regParamDAO;
    }

    public void setRegParamDAO(RegParamDAO regParamDAO) {
        this.regParamDAO = regParamDAO;
    }

    public ConsInfoDAO getConsInfoDAO() {
        return consInfoDAO;
    }

    public void setConsInfoDAO(ConsInfoDAO consInfoDAO) {
        this.consInfoDAO = consInfoDAO;
    }
}
