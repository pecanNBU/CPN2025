package com.wnwl.CPN2025.bhh;

import java.util.HashSet;
import java.util.Set;

/**
 * TreeNode  @author Jenny
 */

public class TreeNode implements java.io.Serializable {

	// Fields

	private Integer id;
	private TreeNode parent;
	private String name;
	private byte type;
	private String url;
	private String title;
	private Short sort;
	private String icon;
	private String comment;

	// Constructors

	/** default constructor */
	public TreeNode() {

	}

	/** full constructor */
	public TreeNode(TreeNode parent, String name, byte type, String url,
                    String title, Short sort, String icon, String comment) {
		this.parent = parent;
		this.name = name;
		this.type = type;
		this.url = url;
		this.title = title;
		this.sort = sort;
		this.icon = icon;
		this.comment = comment;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TreeNode getParent() {
		return this.parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getType() {
		return this.type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Short getSort() {
		return this.sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTypeStr(){
	    switch(this.type){
            case 0: return "NAV导航";
            case 1: return "TOP导航";
        }
        return "";
    }
}