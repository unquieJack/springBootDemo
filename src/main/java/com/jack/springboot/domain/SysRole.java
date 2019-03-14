package com.jack.springboot.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sys_role")
public class SysRole  implements Serializable {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long roleId;   //角色id
    private String roleName; //角色名称
    private String description; // 角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户
    private Date createDate;  //创建时间
    private Date modidyDate;   //更新时间
    @Transient
    private List<Long> permissionIds;

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModidyDate() {
        return modidyDate;
    }

    public void setModidyDate(Date modidyDate) {
        this.modidyDate = modidyDate;
    }
}
