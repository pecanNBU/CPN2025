package com.wnwl.CPN2025.hdao.impl;

import com.wnwl.CPN2025.bhh.DType;
import com.wnwl.CPN2025.hdao.DTypeDAO;

import java.util.List;

public class DTypeDAOImpl extends BaseDAOImpl<DType, Integer> implements DTypeDAO {

	public DTypeDAOImpl() {
		 super(DType.class);
	}

    public List<DType> findAllTypes(){
        String cond = "parent.id=1 and id>0 and isLeaf=0 and typeName!='备用'";
        return this.findSelfObjects(cond);
    }
	
}
