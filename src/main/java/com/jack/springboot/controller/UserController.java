package com.jack.springboot.controller;

import com.jack.springboot.domain.Result;
import com.jack.springboot.domain.User;
import com.jack.springboot.properties.UserProperties;
import com.jack.springboot.service.UserService;
import com.jack.springboot.util.ResultUtils;
import com.jack.springboot.util.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value="user")
public class UserController {


    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;
    @Autowired
    UserProperties userProperties;


    //http://localhost:8085/user/all
    @RequestMapping(value = "/all")
    @ResponseBody
    public Result queryAll(){

        logger.info("读取配置文件信息:"+userProperties.getUsername());

        List<User> userList = userService.queryAll();
        return ResultUtils.success(userList);
    }
    //http://localhost:8085/user/select?username=crystal
    @RequestMapping(value = "/select")
    @ResponseBody
    public Result queryUserByUsername(String username){
        User user = userService.queryUserByUsername(username);
        return ResultUtils.success(user);

    }
    //http://localhost:8085/user/select1?id=1
    @RequestMapping(value = "/select1")
    @ResponseBody
    public Result queryUserById(int id){
        User user = userService.queryUserById(id);
        return ResultUtils.success(user);
    }

    //http://localhost:8085/user/select2?id=1
    @RequestMapping(value = "/select2")
    @ResponseBody
    public Result queryUserById2(Long id) throws Exception{
        User user = userService.queryUserById2(id);
        return ResultUtils.success(user);
    }

    //http://localhost:8085/user/update?id=1
    @RequestMapping(value = "/update")
    @ResponseBody
    public Result updateUser(Long id) throws Exception{


        User user = userService.queryUserById2(id);
        user.setUsername("crystal");
        user.setPassword("hibeg9ez");
        user = userService.updateUser(user);
        return ResultUtils.success(user);
    }
    //http://localhost:8085/user/delete?id=1
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delUserById(Long id) throws Exception{
        userService.queryUserById2(id);
        this.userService.deleteUser(id);
        return ResultUtils.success();
    }
    //http://localhost:8085/user/add
    @RequestMapping(value = "/add")
    @ResponseBody
    public Result addUser(){
        User user = new User();
        user.setAddress("湖北武汉");
        user.setAge(35);
        user.setEmail(Math.random()*100000+"@qq.com");
        user.setUsername("jack");
        user.setPassword("123456");
        user.setRegDate(new Date());
        user.setUpdateDate(new Date());
        user.setRole_id(1);
        user.setTelNum(String.valueOf(Math.random()*1000000));
        this.userService.insertUser(user);
        return ResultUtils.success(user);
    }


}
