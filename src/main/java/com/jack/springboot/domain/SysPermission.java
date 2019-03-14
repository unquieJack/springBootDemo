package com.jack.springboot.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_permission")
public class SysPermission implements Serializable {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long pserId;   //权限id

    private String name;//名称.

    private String resourceType;//资源类型，[menu|button]

    private String url;//资源路径.

    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view

    private Long parentId; //父编号

    private Boolean available = Boolean.FALSE;

    private Date createDate;  //创建时间

    private Date modidyDate;   //更新时间

    private String description; // 角色描述,UI界面显示使用

    public Long getPserId() {
        return pserId;
    }

    public void setPserId(Long pserId) {
        this.pserId = pserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
