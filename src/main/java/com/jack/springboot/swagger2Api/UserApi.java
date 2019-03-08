package com.jack.springboot.swagger2Api;

import com.jack.springboot.domain.Result;
import com.jack.springboot.domain.User;
import com.jack.springboot.enums.ResultEnum;
import com.jack.springboot.properties.UserProperties;
import com.jack.springboot.service.UserService;
import com.jack.springboot.util.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value="user/api")
public class UserApi {

    private final static Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    UserService userService;
    @Autowired
    UserProperties userProperties;

    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result queryAll(){
        List<User> userList = userService.queryAll();
        return ResultUtils.success(userList);
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getUserById (@PathVariable(value = "id") Long id) throws Exception{
        User user = userService.queryUserById2(id);
        return ResultUtils.success(user);
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public Result addUser(@RequestBody User user){
        if(userService.queryUserByUsername(user.getUsername())!=null)
            return ResultUtils.error(ResultEnum.USEREXIST);
        this.userService.insertUser(user);
        return ResultUtils.success(user);
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result delete (@PathVariable(value = "id") Long id) throws Exception{
        userService.queryUserById2(id);
        this.userService.deleteUser(id);
        return ResultUtils.success();
    }

    @ApiOperation(value="更新用户信息", notes="更新用户信息")
    @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
    @RequestMapping(value = "user", method = RequestMethod.PUT)
    @ResponseBody
    public Result update (@RequestBody User user) throws Exception{
        userService.queryUserById2(user.getId());
        return ResultUtils.success(userService.updateUser(user));
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的用户名来获取用户详细信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/user/username={username}", method = RequestMethod.GET)
    @ResponseBody
    public Result getUserByUsername(@PathVariable(value="username") String username){
        User user = userService.queryUserByUsername(username);
        return ResultUtils.success(user);
    }
}
