package com.wnwl.CPN2025.acegi;

import java.util.ArrayList;
import java.util.List;

import com.wnwl.CPN2025.service.UserService;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

import com.wnwl.CPN2025.bhh.PrivActor;
import com.wnwl.CPN2025.bhh.UserInfo;

public class AcegiUserDeitailsService implements UserDetailsService { 

    /* 依赖注入 */
    private UserService userService;
    private String loginName;

    /* 用户所有的权限 */
    private GrantedAuthority[] grantedAuthArray;

    /**实现接口UserDetailsService的函数，根据用户名获取UserDetails对象*/
    public IUserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException, DataAccessException {

        UserInfo userInfo = userService.findUserByLogin(userName);
        if(userInfo == null )
            throw new UsernameNotFoundException("User name is not found.");

        IUserDetails initUser = new UserDetailsImpl(userInfo.getLoginName(), userInfo.getLoginPwd(), true, true, true, true,
                new GrantedAuthority[] {new GrantedAuthorityImpl("HOLDER")});
        initUser.setId(userInfo.getId());
        initUser.setUserName(userInfo.getUserName());

        // 取得所有用户权限
        List<PrivActor> userRoleList = userService.getPrivActor(userInfo.getId());
        List<PrivActor> roles=new ArrayList<PrivActor>(0);
        if(userRoleList == null || userRoleList.size() == 0) {
            PrivActor e=new PrivActor();
            e.setName("ROLE_A");
            roles.add(e);
        }
        else
            roles.addAll(userRoleList);
        // 取得用户的所有角色
        int size = userRoleList.size();
        grantedAuthArray = new GrantedAuthority[size];
        int j = 0;
        for(int i = 0; i < size; i++) {
            PrivActor userRole = roles.get(i);
            if(userRole != null) {
                this.grantedAuthArray[j++] = new GrantedAuthorityImpl(userRole.getName().toUpperCase());
            }
        }

        if (grantedAuthArray.length == 0)
            throw new UsernameNotFoundException("User has no GrantedAuthority");

        initUser.setAuthorities(grantedAuthArray);

        return initUser;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}