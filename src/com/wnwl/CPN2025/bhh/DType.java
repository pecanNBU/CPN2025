package com.wnwl.CPN2025.bhh;

/**
 * parent  @author Jenny
 */

public class DType implements java.io.Serializable {

	// Fields

	private Integer id;
	private DType parent;
	private String typeName;
	private byte isLeaf;
	private String enName;

	// Constructors

	/** default constructor */
	public DType() {

	}

	/** full constructor */
	public DType(DType parent, String typeName, byte isLeaf, String enName) {
		this.parent = parent;
		this.typeName = typeName;
		this.isLeaf = isLeaf;
		this.enName = enName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DType getParent() {
		return this.parent;
	}

	public void setParent(DType parent) {
		this.parent = parent;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public byte getIsLeaf() {
		return this.isLeaf;
	}

	public void setIsLeaf(byte isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

}