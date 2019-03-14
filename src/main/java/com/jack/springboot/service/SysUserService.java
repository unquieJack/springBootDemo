package com.jack.springboot.service;

import com.jack.springboot.dao.*;
import com.jack.springboot.domain.SysUser;
import com.jack.springboot.domain.SysUserRole;
import com.jack.springboot.enums.ResultEnum;
import com.jack.springboot.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SysUserService {

    @Autowired
    SysUserRoleDao sysUserRoleDao;

    @Autowired
    SysRoleDao sysRoleDao;

    @Autowired
    SysUserDao sysUserDao;

    public void addSysUser(SysUser sysUser) {
        String username = sysUser.getUsername();
        if (getSysUserByUsername(username) != null) {
            throw new CustomException(ResultEnum.USEREXIST);
        }
        List<Long> roleIds = sysUser.getRoleIds();
        SysUser user = sysUserDao.save(sysUser);
        batchHandUserRole(roleIds, user.getUserId(), true);
    }

    public void updateSysUser(SysUser sysUser) {
        SysUser oSysUser = getSysUserByUsername(sysUser.getUsername());
        if (oSysUser != null && oSysUser.getUserId()!=sysUser.getUserId()) {
            throw new CustomException(ResultEnum.USEREXIST);
        }
        Long userId = sysUser.getUserId();
        Optional<SysUser> oUser = sysUserDao.findById(userId);
        if (oUser != null && oUser.isPresent()) {
            sysUser.setModidyDate(new Date());
            sysUserDao.save(sysUser);

            List<SysUserRole> userRoleList = sysUserRoleDao.findByUserId(userId);
            if (userRoleList != null && userRoleList.size() > 0) {
                batchHandUserRole(null, sysUser.getUserId(), false);
                batchHandUserRole(sysUser.getRoleIds(), sysUser.getUserId(), true);
            }
        } else {
            throw new CustomException(ResultEnum.USERISEXIST);
        }

    }

    public void deleteSysUser(SysUser sysUser) {
        Long userId = sysUser.getUserId();
        Optional<SysUser> oSysUser = sysUserDao.findById(userId);
        if (oSysUser != null && oSysUser.isPresent()) {
            sysUserDao.delete(sysUser);
            batchHandUserRole(null, sysUser.getUserId(), false);
        } else {
            throw new CustomException(ResultEnum.USERISEXIST);
        }
    }

    public SysUser getSysUserByUsername(String username) {
        return sysUserDao.findByUsername(username);
    }

    public void batchHandUserRole(List<Long> roleIds, Long userId, Boolean isAdd) {
        if(isAdd){
            if (roleIds != null && roleIds.size() > 0) {
                for (long roleid : roleIds) {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(roleid);
                    sysUserRole.setUserId(userId);
                    sysUserRoleDao.save(sysUserRole);
                }
            }
        }else{
            sysUserRoleDao.deleteByUserId(userId);
        }
    }

}
