package com.wnwl.CPN2025.bhh;

import java.util.HashSet;
import java.util.Set;

/**
 * RegParam  @author Jenny
 */

public class RegParam implements java.io.Serializable {

	// Fields

	private Integer id;
	private DType DTypeByUnitTypeId;
	private DType DTypeByParaTypeId;
	private RegInfo regInfo;
	private String paraName;
	private Integer memoType;
    private float dataMin;

	// Constructors

	/** default constructor */
	public RegParam() {

	}

    /** full constructor */

    public RegParam(Integer id, DType DTypeByUnitTypeId, DType DTypeByParaTypeId, RegInfo regInfo, String paraName, Integer memoType, float dataMin) {
        this.id = id;
        this.DTypeByUnitTypeId = DTypeByUnitTypeId;
        this.DTypeByParaTypeId = DTypeByParaTypeId;
        this.regInfo = regInfo;
        this.paraName = paraName;
        this.memoType = memoType;
        this.dataMin = dataMin;
    }

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DType getDTypeByUnitTypeId() {
		return this.DTypeByUnitTypeId;
	}

	public void setDTypeByUnitTypeId(DType DTypeByUnitTypeId) {
		this.DTypeByUnitTypeId = DTypeByUnitTypeId;
	}

	public DType getDTypeByParaTypeId() {
		return this.DTypeByParaTypeId;
	}

	public void setDTypeByParaTypeId(DType DTypeByParaTypeId) {
		this.DTypeByParaTypeId = DTypeByParaTypeId;
	}

	public RegInfo getRegInfo() {
		return this.regInfo;
	}

	public void setRegInfo(RegInfo regInfo) {
		this.regInfo = regInfo;
	}

	public String getParaName() {
		return this.paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public Integer getMemoType() {
		return this.memoType;
	}

	public void setMemoType(Integer memoType) {
		this.memoType = memoType;
	}

    public float getDataMin() {
        return dataMin;
    }

    public void setDataMin(float dataMin) {
        this.dataMin = dataMin;
    }
}