package com.wnwl.CPN2025.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Simple to Introduction
 * @ProjectName:    建设工程颗粒物与噪声在线监测系统
 * @Package:        com.wnwl.CPN2025.util
 * @ClassName:      扩展类 - 时间转换
 * @Description:    将int类型的时间转换为完整字符串
 * @Author:         Jenny
 * @CreateDate:     2016.7.22
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
 */
public class DtUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *@Title:       transDtToStr
     *@Description: 将存在数据库的int(10)型时间字段转换为可视字符串
     *@param        dt 精确到秒:System.currentTimeMillis() / 1000
     *@return       String, 返回可视化字符串(yyyy-MM-dd HH:mm:ss)
     *@throws
     */
    public static String transDtToStr(Integer dt){
        if(dt == null)
            return "";
        Long dtL = dt.longValue()*1000;
        String strDt = "";
        Date resultdate = new Date(dtL);
        strDt = sdf.format(resultdate);
        return strDt;
    }

    /**
     *@Title:       transDtToInt
     *@Description: 将Date类型时间转为int(10)型时间字段,精确到秒
     *@param        dt
     *@return       Integer, 返回int(10)
     *@throws
     */
    public static Integer transDtToInt(Date dt){
        if(dt == null)
            return 0;
        int dtINt = (int) (dt.getTime()/1000);
        return dtINt;
    }
}
