package com.wnwl.CPN2025.bhh;

/**
 * DepInfo  @author Jenny
 */

public class DepInfo extends BaseBhh {

	// Fields

	private Integer id;
	private String depName;
	private String chargeUser;
	private String address;
    private String mobilephone;
	private String telephone;
	private String fax;
	private String web;
	private String email;
    private byte type;

	// Constructors

	/** default constructor */
	public DepInfo() {

	}

    /** full constructor */

    public DepInfo(Integer id, String depName, String chargeUser, String address, String mobilephone, String telephone,
                   String fax, String web, String email, byte type) {
        this.id = id;
        this.depName = depName;
        this.chargeUser = chargeUser;
        this.address = address;
        this.mobilephone = mobilephone;
        this.telephone = telephone;
        this.fax = fax;
        this.web = web;
        this.email = email;
        this.type = type;
    }

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepName() {
		return this.depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getChargeUser() {
		return this.chargeUser;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String transType(){
        switch(this.type){
            case 1:return "环保部门";
            case 2:return "运维单位";
            case 3:return "施工单位";
        }
        return "其它单位";
    }
}