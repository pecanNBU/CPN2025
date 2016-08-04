package com.wnwl.CPN2025.bhh;

/**
 * LogOperCont  @author Jenny
 */

public class LogOperCont implements java.io.Serializable {

	// Fields

	private Integer id;
	private int logId;
	private String fdName;
    private String fdRemark;
	private String valOld;
	private String valNew;

	// Constructors

	/** default constructor */
	public LogOperCont() {

	}

    /** full constructor */

    public LogOperCont(Integer id, int logId, String fdName, String fdRemark, String valOld, String valNew) {
        this.id = id;
        this.logId = logId;
        this.fdName = fdName;
        this.fdRemark = fdRemark;
        this.valOld = valOld;
        this.valNew = valNew;
    }

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getFdRemark() {
        return fdRemark;
    }

    public void setFdRemark(String fdRemark) {
        this.fdRemark = fdRemark;
    }

    public String getValOld() {
		return this.valOld;
	}

	public void setValOld(String valOld) {
		this.valOld = valOld;
	}

	public String getValNew() {
		return this.valNew;
	}

	public void setValNew(String valNew) {
		this.valNew = valNew;
	}

}