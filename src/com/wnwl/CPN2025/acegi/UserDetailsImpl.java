package com.wnwl.CPN2025.acegi;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.User;

/**
 * Created by Jenny on 2016/7/20.
 */
public class UserDetailsImpl extends User implements IUserDetails{
    private int id;
    private String userName;
    private GrantedAuthority[] authorities;

    public UserDetailsImpl(String username, String password, boolean enabled,
                           boolean accountNonExpired, boolean credentialsNonExpired,
                           boolean accountNonLocked, GrantedAuthority[] authorities)
            throws IllegalArgumentException {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        setUserName(username);
        setAuthorities(authorities);
    }

    public UserDetailsImpl(int id, String login_name, String username, String password, boolean enabled,
                           boolean accountNonExpired, boolean credentialsNonExpired,
                           boolean accountNonLocked, GrantedAuthority[] authorities)
            throws IllegalArgumentException {
        super(login_name, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.id = id;
        setUserName(username);
        setAuthorities(authorities);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GrantedAuthority[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(GrantedAuthority[] authorities) {
        this.authorities = authorities;
    }
}