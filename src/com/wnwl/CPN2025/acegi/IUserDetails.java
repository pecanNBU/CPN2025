package com.wnwl.CPN2025.acegi;

import org.acegisecurity.GrantedAuthority;
/**
 * Created by Jenny on 2016/7/20.
 */
public interface IUserDetails extends org.acegisecurity.userdetails.UserDetails{

    public int getId();

    public void setId(int id);

    public String getUserName();

    public void setUserName(String user_name);

    public GrantedAuthority[] getAuthorities();

    public void setAuthorities(GrantedAuthority[] authorities);

}
