package com.jack.springboot.dao;

import com.jack.springboot.domain.SysUser;
import com.jack.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserDao extends JpaRepository<SysUser, Long> {
    public SysUser findByUsername(String username);
}
