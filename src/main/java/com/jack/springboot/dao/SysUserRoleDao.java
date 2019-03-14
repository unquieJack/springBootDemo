package com.jack.springboot.dao;

import com.jack.springboot.domain.SysUser;
import com.jack.springboot.domain.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysUserRoleDao extends JpaRepository<SysUserRole, Long> {
    List<SysUserRole>  findByUserId(Long userId);

    void deleteByUserId(Long userId);

    void deleteByRoleId(Long roleId);
}
