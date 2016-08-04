package com.wnwl.CPN2025.action;

import com.wnwl.CPN2025.service.MonitorService;

/**
 * Created by peng on 2016/7/19.
 */
public class MonitorAction extends BaseAction {
    private MonitorService monitorService;

    public String showMonitors() {
        monitorService.initSDK();//初始化SDK
        monitorService.LoginActionPerformed("192.168.1.170", 8000, "admin", "wnwl1234");//注册用户
        //monitorService.RealPlayActionPerformed();//预览
       /* monitorService.UpMousePressed();
        try {
            sleep(5000);
        } catch (Exception ex) {
}*/
        //monitorService.UpMouseReleased();
        monitorService.AutoActionPerformedNo(1);//自动巡航，速度为1
        return SUCCESS;
    }

    public MonitorService getMonitorService() {
        return monitorService;
    }

    public void setMonitorService(MonitorService monitorService) {
        this.monitorService = monitorService;
    }
}

