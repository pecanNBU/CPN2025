package com.wnwl.CPN2025.hdao.impl;

import com.wnwl.CPN2025.bhh.DepInfo;
import com.wnwl.CPN2025.hdao.DepInfoDAO;

import java.util.List;

public class DepInfoDAOImpl extends BaseDAOImpl<DepInfo, Integer> implements DepInfoDAO {

	public DepInfoDAOImpl() {
		 super(DepInfo.class);
	}

    public List<DepInfo> getAllDepInfos(){
        return this.findAll();
    }
	
}
