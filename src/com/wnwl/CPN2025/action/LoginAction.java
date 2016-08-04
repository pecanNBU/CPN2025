package com.wnwl.CPN2025.action;

import com.wnwl.CPN2025.bhh.*;
import com.wnwl.CPN2025.service.*;
import com.wnwl.CPN2025.util.Encyptions;
import com.wnwl.CPN2025.util.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * Simple to Introduction
 * @ProjectName:    建设工程颗粒物与噪声在线监测系统
 * @Package:        com.wnwl.CPN2025.action
 * @ClassName:      登录操作类
 * @Description:    登录操作
 * @Author:         Jenny
 * @CreateDate:     2016.7.18
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
 */
public class LoginAction extends BaseAction{
	private static final long serialVersionUID = 2696363985077090985L;
	private UserService userService;
	private LogService logService;
    private RegService regService;
	private UserInfo userInfo;          //用户信息对象变量，需注入spring
	private Encyptions ens;             //加密类对象
	private String loginName;           //用户名
	private String psw;                 //密码
	private ArrayList<Object> rows;
	private Map<String, Object> map;
	private long total;
	private Page pages;
    private byte flag;
    private Integer id;
    private String name;
    private boolean state;
    private String desc;
    private Integer userId;

    public static void main(String[] args){
        String aa = "com.wnwl.CPN2025.hdao.impl.TestUserDAOImpl";
        System.err.println(aa.replace("hdao.impl", "bhh").replace("DAOImpl", ""));
    }

    /**
     *@Title: hide
     *@Description: 防止页面超过2小时不操作而失去session
     *@param
     *@return 返回hide.jsp
     *@throws
     */
	public String hide(){
		return SUCCESS;
	}

	public String main(){
		userInfo = userService.findLoginUser();
		if(null==userInfo)
			return ERROR;
		return SUCCESS;
	}

    /**
     *@Title: checkLogin
     *@Description: 验证登录的账号密码,成功则记录登录日志
     *@param
     *@return flag, 返回ajax请求
     *@throws
     */
	public String checkLogin(){
		try {
            ens = new Encyptions();
			String psws = ens.encryptSHA(psw);
			userInfo = userService.checkLogin(loginName, psws);
			if (userInfo == null)
				flag = 0;
			else {
				flag = 1;
                logService.logLogin(userInfo);
			}
		} catch (Exception e) {
			flag = 2;
			e.printStackTrace();
		}
		return SUCCESS;
	}

    /**
     *@Title: logins
     *@Description: 登陆判定，根据是否成功转入相应页面
     *@param
     *@return 返回hide.jsp
     *@throws
     */
	public String logins(){
        try {
            userInfo = userService.findLoginUser();
            List<PrivActor> privActors = userService.getPrivActor(userInfo.getId());
            if(privActors!=null&&privActors.size()>0){
                for(PrivActor privActor:privActors){
                    session.put("actorId", privActor.getId());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
	}
    /**
     *@Title: exitLogin
     *@Description: 退出登录
     *@param
     *@return 返回login.jsp
     *@throws
     */
	public String exitLogin(){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            session.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
	}
	/* (non-Javadoc)
	 * <P> Title:updatepassword()</P>
	 * <P> Description: 获取用户密码</P>
	 * @throws 
	 */
	public String password(){
        try {
            userInfo = userService.findLoginUser();
            String j = userInfo.getLoginPwd();
            HttpServletRequest request = ServletActionContext.getRequest();
            request.setAttribute("j", j);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
	}
	/* (non-Javadoc)
	 * <P> Title:updatepassword()</P>
	 * <P> Description: 修改用户密码</P>
	 * @throws 
	 */
	public String updatepassword(){
        try {
            String newPwdId = ServletActionContext.getRequest().getParameter("newPwdId");
            String userpwd = ServletActionContext.getRequest().getParameter("userpwd");
            String newPwdId1 = ens.encryptSHA(newPwdId);
            String userpwd1 = ens.encryptSHA(userpwd);
            UserInfo userInfo = userService.findLoginUser();
            if(userpwd1.equals(userInfo.getLoginPwd())){
                userInfo.setLoginPwd(newPwdId1);
                userService.getUserInfoDAO().saveOrUpdate(userInfo);
                return SUCCESS;
            }
            //else
                //addFieldError("userpwd", "原始密码不正确，请重新输入");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ERROR;
	}

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public byte getFlag() {
        return flag;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public RegService getRegService() {
        return regService;
    }

    public void setRegService(RegService regService) {
        this.regService = regService;
    }
}
