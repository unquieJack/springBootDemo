package com.jack.springboot.dao;

import com.jack.springboot.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleDao extends JpaRepository<SysRole, Long> {
    public SysRole findRoleByRoleName(String roleName);
    public void deleteByRoleId(Long roleId);
}
