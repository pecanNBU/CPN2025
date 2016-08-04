package com.wnwl.CPN2025.hdao;

import com.wnwl.CPN2025.bhh.UserInfo;

import java.util.Set;

public interface UserInfoDAO extends BaseDAO<UserInfo, Integer> {

    public UserInfo findLoginUser();

    public Integer findLoginId();

    public UserInfo findUserByLogin(String loginName);

    public UserInfo checkLogin(String loginName, String psws);

}