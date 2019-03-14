package com.jack.springboot.service;

import com.jack.springboot.dao.RolePermissionDao;
import com.jack.springboot.dao.SysPermissionDao;
import com.jack.springboot.domain.SysPermission;
import com.jack.springboot.enums.ResultEnum;
import com.jack.springboot.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Transactional
public class SysPermissionService {

    @Autowired
    RolePermissionDao rolePermissionDao;

    @Autowired
    SysPermissionDao sysPermissionDao;

    public void addPermission(SysPermission sysPermission){
        sysPermissionDao.save(sysPermission);
    }
    public void deletePermission(Long pserId){
        SysPermission olsp = sysPermissionDao.findByPserId(pserId);
        if(olsp!=null){
            sysPermissionDao.deleteByPserId(pserId);

            rolePermissionDao.deleteByPerId(pserId);
        }else{
            throw new CustomException(ResultEnum.PERMISSIONISEXIST);
        }
    }
    public void updatePermission(SysPermission sysPermission){
        Long pserId = sysPermission.getPserId();
        SysPermission olsp = sysPermissionDao.findByPserId(pserId);
        if(olsp!=null){
            sysPermission.setModidyDate(new Date());
            sysPermissionDao.save(sysPermission);
        }else{
            throw new CustomException(ResultEnum.PERMISSIONISEXIST);
        }

    }
}
