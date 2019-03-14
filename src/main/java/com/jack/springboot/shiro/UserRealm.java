package com.jack.springboot.shiro;

import com.jack.springboot.dao.SysUserDao;
import com.jack.springboot.domain.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 认证
 *
 * @author hizi
 * @email 282840695@qq.com
 * @date 2019年11月10日 上午11:55:49
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.setStringPermissions(user.getPermsSet());
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        //查询用户信息
        SysUser user = sysUserDao.findByUsername(username);
        //账号不存在
        if (user == null) {
            throw new UnknownAccountException();
        }
        //密码错误
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException();
        }
        //账号锁定
        if (user.getState() == 0) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

}
