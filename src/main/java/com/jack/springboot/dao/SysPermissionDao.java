package com.jack.springboot.dao;

import com.jack.springboot.domain.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysPermissionDao extends JpaRepository<SysPermission, Long> {

    public SysPermission findByPserId(Long pserId);

    public void deleteByPserId(Long pserId);

}
