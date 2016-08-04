package com.wnwl.CPN2025.bhh;

/**
 * LogBrowse  @author Jenny
 */

public class LogShow implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
    private String ip;
	private Integer dt;
    private Integer dtLast;
    private String moduleName;
    private String moduleRemark;
    private String methodName;
    private String methodRemark;
	private String url;

	// Constructors

	/** default constructor */
	public LogShow() {

	}
    
    /** full constructor */
    
    public LogShow(Integer id, Integer userId, String ip, Integer dt, Integer dtLast, String moduleName, String moduleRemark, 
                   String methodName, String methodRemark, String url) {
        this.id = id;
        this.userId = userId;
        this.ip = ip;
        this.dt = dt;
        this.dtLast = dtLast;
        this.moduleName = moduleName;
        this.moduleRemark = moduleRemark;
        this.methodName = methodName;
        this.methodRemark = methodRemark;
        this.url = url;
    }

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDt() {
		return this.dt;
	}

	public void setDt(Integer dt) {
		this.dt = dt;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getDtLast() {
        return dtLast;
    }

    public void setDtLast(Integer dtLast) {
        this.dtLast = dtLast;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getModuleRemark() {
        return moduleRemark;
    }

    public void setModuleRemark(String moduleRemark) {
        this.moduleRemark = moduleRemark;
    }

    public String getMethodRemark() {
        return methodRemark;
    }

    public void setMethodRemark(String methodRemark) {
        this.methodRemark = methodRemark;
    }
}