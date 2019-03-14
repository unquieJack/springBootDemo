package com.jack.springboot.service;


import com.jack.springboot.dao.*;
import com.jack.springboot.domain.RolePermission;
import com.jack.springboot.domain.SysRole;
import com.jack.springboot.domain.SysUser;
import com.jack.springboot.domain.SysUserRole;
import com.jack.springboot.enums.ResultEnum;
import com.jack.springboot.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SysRoleService {
    @Autowired
    RolePermissionDao rolePermissionDao;

    @Autowired
    SysUserRoleDao sysUserRoleDao;

    @Autowired
    SysRoleDao sysRoleDao;

    public void addSysRole(SysRole role){
        String rolename = role.getRoleName();
        SysRole sysRole = querySysRoleByName(rolename);
        if(sysRole==null){
            sysRoleDao.save(role);
            List<Long> permissionIds = role.getPermissionIds();
            batchRolePermission(permissionIds,role.getRoleId(),true);
        }else{
            throw new CustomException(ResultEnum.ROLEISEXIT);
        }
    }
    public void delSysRoleById(SysRole role){
        Optional<SysRole> op = sysRoleDao.findById(role.getRoleId());
        if(op!=null && op.isPresent()){
            sysRoleDao.deleteByRoleId(role.getRoleId());
            sysUserRoleDao.deleteByRoleId(role.getRoleId());
            rolePermissionDao.deleteByRoleId(role.getRoleId());
        }else{
            throw new CustomException(ResultEnum.ROLEEXIT);
        }
    }
    public void updateSysRole(SysRole role){
        SysRole osysRole = querySysRoleByName(role.getRoleName());
        if (osysRole != null && osysRole.getRoleId()!=osysRole.getRoleId()) {
            throw new CustomException(ResultEnum.ROLEISEXIT);
        }
        Long roleId = role.getRoleId();
        Optional<SysRole> op = sysRoleDao.findById(roleId);
        if (op != null && op.isPresent()) {
            role.setModidyDate(new Date());
            sysRoleDao.save(role);

            //删表该角色之前拥有的权限
            batchRolePermission(null,role.getRoleId(),false);
            batchRolePermission(role.getPermissionIds(),role.getRoleId(),true);
        }else{
            throw new CustomException(ResultEnum.ROLEEXIT);
        }
    }
    public SysRole querySysRoleByName(String roleName){
        return sysRoleDao.findRoleByRoleName(roleName);
    }

    public void batchRolePermission(List<Long> permissionIds,Long roleId,Boolean isAdd){
        if(isAdd){
            if(permissionIds!=null && permissionIds.size()>0){
                for(Long permissionid : permissionIds){
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setPerId(permissionid);
                    rolePermission.setRoleId(roleId);
                    rolePermissionDao.save(rolePermission);
                }
            }
        }else{
            rolePermissionDao.deleteByRoleId(roleId);
        }
    }
}
