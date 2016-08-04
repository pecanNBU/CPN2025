package com.wnwl.CPN2025.action;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;

import com.wnwl.CPN2025.bhh.SystemInfo;
import com.wnwl.CPN2025.bhh.VersionInfo;
import com.wnwl.CPN2025.hdao.SystemInfoDAO;
import com.wnwl.CPN2025.hdao.VersionInfoDAO;
import com.wnwl.CPN2025.service.SystemService;
import com.wnwl.CPN2025.service.UserService;
import com.wnwl.CPN2025.util.DtUtil;
import com.wnwl.CPN2025.util.Encyptions;
import com.wnwl.CPN2025.util.PageBean;
import org.apache.struts2.ServletActionContext;

/**
 *
 * Simple to Introduction
 * @ProjectName:    建设工程颗粒物与噪声在线监测系统
 * @Package:        com.wnwl.CPN2025.util
 * @ClassName:      项目基本信息类
 * @Description:    系统信息、版本记录
 * @Author:         Jenny
 * @CreateDate:     2016.7.26
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
 */
public class SystemAction extends BaseAction {
	private SystemService systemService;
	private UserService userService;
	private List<VersionInfo> versionInfos;
	private SystemInfo systemInfo;
    private VersionInfo versionInfo;
	private PageBean pageBean;
	private ArrayList<Object> rows;
	private HashMap<String, Object> map;
	private String cond;
	private byte flag;
	private int jumpPage;
	private int pageSize;
	private long count;
	private byte nodeState;
	private Integer id;
	private String name;
	private String abbr;
	private String version;
	private String comment;
	private String copyright;
	private String email;
	private String fax;
	private String telephone;
	private String web;
    private String apprUser;
    private String chargeUser;
    private String mobilephone;
	private String queryVersion;
	private String downloadFileName;
	private InputStream input;
	private String scriptPath;
	private double alarmStart;    //开始异常的点
	private double alarmEnd;    //结束异常的点
	private Encyptions ens;
	private String helpPsd;        //使用手册对应验证码
	private byte runState;

    public String showSystemInfo() {

        return SUCCESS;
    }
    
	public String json_systemInfo() {        //系统信息
		String hql = "from SystemInfo order by dt desc";
		systemInfo = systemService.getSystemInfoDAO().findFirstObject(hql);
		map = new HashMap<String, Object>();
        map.put("id", systemInfo.getId());
		map.put("name", systemInfo.getName());
		map.put("chargeUser", systemInfo.getChargeUser());
		map.put("abbr", systemInfo.getAbbr());
        map.put("copyright", systemInfo.getCopyright());
		map.put("version", systemInfo.getVersion());
		map.put("dt", DtUtil.transDtToStr(systemInfo.getDt()));
		map.put("copyright", systemInfo.getCopyright());
		map.put("email", systemInfo.getEmail());
		map.put("telephone", systemInfo.getTelephone());
        map.put("mobilephone", systemInfo.getMobilephone());
		map.put("fax", systemInfo.getFax());
		map.put("web", systemInfo.getWeb());
        map.put("comment", systemInfo.getComment());
		/*boolean[] alarmStates = regConditionService.getAlarmStates();
		map.put("regExcep", alarmStates[0] ? 1 : 0);    //数据异常
		map.put("regComm", alarmStates[1] ? 1 : 0);     //通信异常*/
		return SUCCESS;
	}
	
	public String addSystemInfo(){
        try {
            flag = 0;
            SystemInfoDAO systemInfoDAO = systemService.getSystemInfoDAO();
            if(id==null)
                systemInfo = new SystemInfo();
            else
                systemInfo = systemInfoDAO.findById(id);
            systemInfo.setVersion(version);
            systemInfo.setName(name);
            systemInfo.setChargeUser(chargeUser);
            systemInfo.setAbbr(abbr);
            systemInfo.setDt(32);
            systemInfo.setEmail(email);
            systemInfo.setMobilephone(mobilephone);
            systemInfo.setTelephone(telephone);
            systemInfo.setFax(fax);
            systemInfo.setWeb(web);
            systemInfo.setCopyright(copyright);
            systemInfo.setComment(comment);
            systemInfoDAO.saveOrUpdate(systemInfo);
            map = new HashMap<String, Object>();
            map.put("id", systemInfo.getId());
            map.put("name", systemInfo.getName());
            map.put("chargeUser", systemInfo.getChargeUser());
            map.put("abbr", systemInfo.getAbbr());
            map.put("copyright", systemInfo.getCopyright());
            map.put("version", systemInfo.getVersion());
            map.put("dt", DtUtil.transDtToStr(systemInfo.getDt()));
            map.put("copyright", systemInfo.getCopyright());
            map.put("email", systemInfo.getEmail());
            map.put("telephone", systemInfo.getTelephone());
            map.put("mobilephone", systemInfo.getMobilephone());
            map.put("fax", systemInfo.getFax());
            map.put("web", systemInfo.getWeb());
            map.put("comment", systemInfo.getComment());
        } catch (Exception e) {
            flag = -1;
            e.printStackTrace();
        }
        return SUCCESS;
    }

	public String showVersionInfo() {
		nodeState = systemService.findNodeStatebyUrl(session.get("actorId").toString(), httpRequest.getRequestURI());
		return SUCCESS;
	}

	public String json_versionInfo() {
		pageBean = PageBean.getInstance();
		cond = "id>0 order by dt desc";
		count = systemService.getVersionInfoDAO().getCountbyCond(cond);
		pageBean.setPageBean(count, jumpPage, pageSize, "");
		versionInfos = systemService.getVersionInfoDAO().getList(jumpPage, pageSize, cond);
		rows = transRows_versionInfos(versionInfos);
		return SUCCESS;
	}

	public String query_versionInfo() {
		pageBean = PageBean.getInstance();
		cond = "";
		if (queryVersion != null && !"".equals(queryVersion))
			cond = "version like '%" + queryVersion + "%' and ";
		cond += "id>0 order by dt desc";
		count = systemService.getVersionInfoDAO().getCountbyCond(cond);
		pageBean.setPageBean(count, jumpPage, pageSize, "");
		versionInfos = systemService.getVersionInfoDAO().getList(jumpPage, pageSize, cond);
		rows = transRows_versionInfos(versionInfos);
		return SUCCESS;
	}

	private ArrayList<Object> transRows_versionInfos(List<VersionInfo> versionInfos) {
		this.rows = new ArrayList<Object>();
		List<Object> datas;
		for (VersionInfo versionInfo : versionInfos) {
			datas = new ArrayList<Object>();
			datas.add(versionInfo.getId());
			datas.add(versionInfo.getApprUser());
            datas.add(versionInfo.getChargeUser());
			datas.add(versionInfo.getVersion());
			datas.add(DtUtil.transDtToStr(versionInfo.getDt()));
			datas.add(versionInfo.getComment());
			if (nodeState == 1) {
				datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail(" + versionInfo.getId() + ");'>详细信息</a>"
						+ "　<a class='aBtn aEdit' href='javascript:' onclick='edit(" + versionInfo.getId() + ");'>编辑</a>"
						+ "　<a class='aBtn aDel' href='javascript:' onclick='del(" + versionInfo.getId() + ");'>删除</a>");
			} else {
				datas.add("<a class='aBtn aDetail' href='javascript:' onclick='detail(" + versionInfo.getId() + ");'>详细信息</a>");
			}
			rows.add(datas);
		}
		return rows;
	}

	public String showDetailVersionInfo() {
        versionInfo = systemService.getVersionInfoDAO().findById(id);
		map = new HashMap<String, Object>();
		map.put("id", id);
        map.put("version", versionInfo.getVersion());
		map.put("apprUser", versionInfo.getApprUser());
		map.put("chargeUser", versionInfo.getChargeUser());
		map.put("dt", DtUtil.transDtToStr(versionInfo.getDt()));
		map.put("comment", versionInfo.getComment());
		return SUCCESS;
	}

	public String addVersionInfo() {
		try {
            VersionInfoDAO versionInfoDAO = systemService.getVersionInfoDAO();
            if (id == null)
				versionInfo = new VersionInfo();
			else
                versionInfo = versionInfoDAO.findById(id);
			versionInfo.setVersion(version);
			versionInfo.setApprUser(apprUser);
			versionInfo.setChargeUser(chargeUser);
			versionInfo.setDt(1221);
			versionInfo.setComment(comment);
            versionInfoDAO.save(versionInfo);
			map = new HashMap<String, Object>();
			if (id != null) {
				map.put("id", id);
				map.put("apprUser", versionInfo.getApprUser());
				map.put("chargeUser", versionInfo.getChargeUser());
				map.put("version", versionInfo.getVersion());
				map.put("dt", DtUtil.transDtToStr(versionInfo.getDt()));
				map.put("comment", versionInfo.getComment());
			}
			flag = 0;
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String removeVersionInfo() {
		try {
            VersionInfoDAO versionInfoDAO = systemService.getVersionInfoDAO();
		    versionInfo = versionInfoDAO.findById(id);
            versionInfoDAO.remove(versionInfo);
			flag = 0;
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String json_checkVersion() {        //验证版本号的唯一性
		cond = "version='" + version + "'";
		if (id != null)
			cond += " and id<>" + id;
		versionInfos = systemService.getVersionInfoDAO().findSelfObjects(cond);
		if (versionInfos != null && versionInfos.size() > 0)
			flag = 1;
		else
			flag = 0;
		return SUCCESS;
	}

	public String download() {
		if(flag!=106)
			return "error";
		try {
			ServletContext ctx = ServletActionContext.getServletContext();
			String fileStr = ctx.getRealPath("files") + "/help/help.pdf";
			//setInput(new FileInputStream(fileStr));
			downloadFileName = "CTM2025-电缆沟在线监测系统使用手册.pdf";
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public String json_helpCommit() {    //核对使用手册的验证码
		try {
			String hql = "from SystemInfo order by dt desc";
			SystemInfo systemInfo = systemService.getSystemInfoDAO().findFirstObject(hql);
			String psd = systemInfo.getAbbr();
			String helpPsd_ = ens.encryptSHA(helpPsd);
			String psd_ = ens.encryptSHA("nbwnwl");    //万能密码
			flag = 106;
			if (!helpPsd_.equals(psd) && !helpPsd_.equals(psd_)) {
				flag = 1;
			}
		} catch (Exception e) {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setApprUser(String apprUser) {
        this.apprUser = apprUser;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public void setQueryVersion(String queryVersion) {
        this.queryVersion = queryVersion;
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
}
