package com.jack.springboot.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_role_permission")
public class RolePermission implements Serializable {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long id;

    private Long roleId;

    private Long perId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPerId() {
        return perId;
    }

    public void setPerId(Long perId) {
        this.perId = perId;
    }
}
