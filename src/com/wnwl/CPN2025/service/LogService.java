package com.wnwl.CPN2025.service;

import com.wnwl.CPN2025.bhh.DType;
import com.wnwl.CPN2025.bhh.UserInfo;
import com.wnwl.CPN2025.hdao.LogLoginDAO;
import com.wnwl.CPN2025.hdao.LogOperContDAO;
import com.wnwl.CPN2025.hdao.LogOperDAO;
import com.wnwl.CPN2025.hdao.LogShowDAO;

/**
 *
 * Simple to Introduction
 * @ProjectName:    建设工程颗粒物与噪声在线监测系统
 * @Package:        com.wnwl.CPN2025.service
 * @ClassName:      日志管理类
 * @Description:    登录日志、浏览日志、操作日志
 * @Author:         Jenny
 * @CreateDate:     2016.7.18
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
 */
public interface LogService extends BaseService<DType,Integer> {

    public LogShowDAO getLogShowDAO();

    public LogOperDAO getLogOperDAO();

    public LogOperContDAO getLogOperContDAO();

    public LogLoginDAO getLogLoginDAO();

    public byte logLogin(UserInfo userInfo);

    public byte addTest(int a, String b, boolean c);

    public byte updateTest(UserInfo userInfo, int a, String b, boolean c);

    public byte removeTest(int id);
    
}
