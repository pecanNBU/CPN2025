package com.wnwl.CPN2025.bhh;

/**
 * LogLogin  @author Jenny
 */

public class LogLogin implements java.io.Serializable {

	// Fields

	private Integer id;
	private int userId;
	private Integer dt;
	private String ip;
	private String browser;

	// Constructors

	/** default constructor */
	public LogLogin() {
	}

	/** full constructor */
	public LogLogin(int userId, Integer dt, String ip, String browser) {
		this.userId = userId;
		this.dt = dt;
		this.ip = ip;
		this.browser = browser;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getDt() {
		return this.dt;
	}

	public void setDt(Integer dt) {
		this.dt = dt;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBrowser() {
		return this.browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

}