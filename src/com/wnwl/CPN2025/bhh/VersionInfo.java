package com.wnwl.CPN2025.bhh;

/**
 * VersionInfo  @author Jenny
 */

public class VersionInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String apprUser;
	private String chargeUser;
	private String version;
	private Integer dt;
	private String comment;

	// Constructors

	/** default constructor */
	public VersionInfo() {
	}

    /** full constructor */

    public VersionInfo(Integer id, String apprUser, String chargeUser, String version, Integer dt, String comment) {
        this.id = id;
        this.apprUser = apprUser;
        this.chargeUser = chargeUser;
        this.version = version;
        this.dt = dt;
        this.comment = comment;
    }

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getApprUser() {
        return apprUser;
    }

    public void setApprUser(String apprUser) {
        this.apprUser = apprUser;
    }

    public String getChargeUser() {
        return chargeUser;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getDt() {
		return this.dt;
	}

	public void setDt(Integer dt) {
		this.dt = dt;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}