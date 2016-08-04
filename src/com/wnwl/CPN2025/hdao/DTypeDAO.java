package com.wnwl.CPN2025.hdao;

import com.wnwl.CPN2025.bhh.DType;

import java.util.List;

public interface DTypeDAO extends BaseDAO<DType, Integer> {

    public List<DType> findAllTypes();

}