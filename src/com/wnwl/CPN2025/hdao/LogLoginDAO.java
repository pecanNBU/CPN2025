package com.wnwl.CPN2025.hdao;

import com.wnwl.CPN2025.bhh.LogLogin;
import com.wnwl.CPN2025.bhh.UserInfo;

public interface LogLoginDAO extends BaseDAO<LogLogin, Integer> {

    public byte logLogin(UserInfo userInfo);

    public byte addTest(int a, String b, boolean c);

    public byte updateTest(UserInfo userInfo, int a, String b, boolean c);

    public byte removeTest(int id);

}