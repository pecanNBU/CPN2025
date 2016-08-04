/**
 * @Title: AuthenticationManager.java
 * @Package: com.tds.acegi
 * @Description: TODO
 * @author: 198910310@qq.com
 * @date 2012-4-7下午4:04:49
 * @version V1.0
 */
package com.wnwl.CPN2025.acegi;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;

/**
 * 
 * @ClassName: AuthenticationManager
 * @Description: TODO
 * @author: 198910310@qq.com
 * @date 2012-4-7下午4:04:49
 *
 */
public interface AuthenticationManager {
    public Authentication authenticate(Authentication authentication) 
         throws AuthenticationException;
  }
