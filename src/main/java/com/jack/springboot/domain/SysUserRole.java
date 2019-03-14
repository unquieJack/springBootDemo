package com.jack.springboot.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_user_role")
public class SysUserRole implements Serializable {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long id;
    private Long userId;
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
