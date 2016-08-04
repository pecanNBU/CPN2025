package com.wnwl.CPN2025.bhh;

/**
 * UserDetail  @author Jenny
 */

public class UserDetail extends BaseBhh {

	// Fields

	private Integer id;
    private UserInfo userInfo;
	private String mobilephone;
	private String telephone;
	private String email;
	private String qq;
	private String picHead;

	// Constructors

	/** default constructor */
	public UserDetail() {

	}

    /** full constructor */

    public UserDetail(Integer id, UserInfo userInfo, String mobilephone, String telephone, String email, String qq, String picHead) {
        this.id = id;
        this.userInfo = userInfo;
        this.mobilephone = mobilephone;
        this.telephone = telephone;
        this.email = email;
        this.qq = qq;
        this.picHead = picHead;
    }

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPicHead() {
		return this.picHead;
	}

	public void setPicHead(String picHead) {
		this.picHead = picHead;
	}

}