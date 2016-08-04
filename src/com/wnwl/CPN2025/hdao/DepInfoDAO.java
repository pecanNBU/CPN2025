package com.wnwl.CPN2025.hdao;

import com.wnwl.CPN2025.bhh.DepInfo;

import java.util.List;

public interface DepInfoDAO extends BaseDAO<DepInfo, Integer> {

    public List<DepInfo> getAllDepInfos();

}