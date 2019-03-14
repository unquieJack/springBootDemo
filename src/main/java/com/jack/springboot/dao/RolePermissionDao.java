package com.jack.springboot.dao;

import com.jack.springboot.domain.RolePermission;
import com.jack.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionDao  extends JpaRepository<RolePermission, Long> {
    void deleteByRoleId(Long roleId);
    void deleteByPerId(Long perId);
}
