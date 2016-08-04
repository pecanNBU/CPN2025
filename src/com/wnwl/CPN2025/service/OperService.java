package com.wnwl.CPN2025.service;

import com.wnwl.CPN2025.bhh.DType;
import com.wnwl.CPN2025.hdao.*;

/**
 *
 * Simple to Introduction
 * @ProjectName:    建设工程颗粒物与噪声在线监测系统
 * @Package:        com.wnwl.CPN2025.service
 * @ClassName:      运维管理接口
 * @Description:    系统巡检、清洗校准、移机操作、报表提交
 * @Author:         Jenny
 * @CreateDate:     2016.7.18
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
 */
public interface OperService extends BaseService<DType,Integer> {

    public FormCaliNoiseDAO getFormCaliNoiseDAO();

    public FormCaliFlowDAO getFormCaliFlowDAO();

    public FormCaliPmDAO getFormCaliPmDAO();

    public FormCommCheckDAO getFormCommCheckDAO();

    public FormDataCheckDAO getFormDataCheckDAO();

    public FormDevChangeDAO getFormDevChangeDAO();

    public FormDevCleanDAO getFormDevCleanDAO();

    public FormDevFaultDAO getFormDevFaultDAO();

    public FormDevMoveDAO getFormDevMoveDAO();

    public FormDevRepairDAO getFormDevRepairDAO();

    public FormFeedbackDAO getFormFeedbackDAO();

    public FormNoiseDataDAO getFormNoiseDataDAO();

    public FormPollDayDAO getFormPollDayDAO();

    public FormPollMonthDAO getFormPollMonthDAO();

    public FormPollWeekDAO getFormPollWeekDAO();

    public FormPollYearDAO getFormPollYearDAO();

    public FormQualCheckDAO getFormQualCheckDAO();

    public FormTraceValDAO getFormTraceValDAO();
    
}
