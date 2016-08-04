package com.wnwl.CPN2025.bhh;

import java.util.HashSet;
import java.util.Set;

/**
 * UserInfo  @author Jenny
 */

public class UserInfo extends  BaseBhh {

	// Fields

	private Integer id;
	private String loginName;
	private String loginPwd;
	private String userName;
	private byte state;
    private DepInfo depInfo;

	// Constructors

	/** default constructor */
	public UserInfo() {

	}

    /** full constructor */

    public UserInfo(Integer id, String loginName, String loginPwd, String userName, byte state, DepInfo depInfo) {
        this.id = id;
        this.loginName = loginName;
        this.loginPwd = loginPwd;
        this.userName = userName;
        this.state = state;
        this.depInfo = depInfo;
    }

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return this.loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
		this.state = state;
	}

    public DepInfo getDepInfo() {
        return depInfo;
    }

    public void setDepInfo(DepInfo depInfo) {
        this.depInfo = depInfo;
    }

    public String transState(){
        switch(this.state){
            case 1: return "允许登录";
            case 2: return "禁止登录";
        }
        return  "";
    }
}