package com.wnwl.CPN2025.bhh;

import java.util.HashSet;
import java.util.Set;

/**
 * LogOper  @author Jenny
 */

public class LogOper implements java.io.Serializable {

	// Fields

	private Integer id;
	private int userId;
    private String ip;
	private Integer dt;
    private Integer dtLast;
	private byte type;
	private String tbName;
	private String tbRemark;
	private String comment;

	// Constructors

	/** default constructor */
	public LogOper() {

	}

    /** full constructor */

    public LogOper(Integer id, int userId, String ip, Integer dt, Integer dtLast, byte type, String tbName, String tbRemark, String comment) {
        this.id = id;
        this.userId = userId;
        this.ip = ip;
        this.dt = dt;
        this.dtLast = dtLast;
        this.type = type;
        this.tbName = tbName;
        this.tbRemark = tbRemark;
        this.comment = comment;
    }

	// Property accessors


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Integer getDtLast() {
        return dtLast;
    }

    public void setDtLast(Integer dtLast) {
        this.dtLast = dtLast;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public String getTbRemark() {
        return tbRemark;
    }

    public void setTbRemark(String tbRemark) {
        this.tbRemark = tbRemark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOperType(){
        switch(type){
            case 1:return "添加";
            case 2:return "更新";
            case 3:return "删除";
        }
        return "";
    }
}