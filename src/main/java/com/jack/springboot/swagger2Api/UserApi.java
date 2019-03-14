package com.jack.springboot.swagger2Api;

import com.jack.springboot.domain.Result;
import com.jack.springboot.domain.SysUser;
import com.jack.springboot.domain.User;
import com.jack.springboot.enums.ResultEnum;
import com.jack.springboot.properties.UserProperties;
import com.jack.springboot.service.SysUserService;
import com.jack.springboot.service.UserService;
import com.jack.springboot.util.ResultUtils;
import com.jack.springboot.util.ShiroUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="user/api")
public class UserApi {

    private final static Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    UserService userService;
    @Autowired
    UserProperties userProperties;
    @Autowired
    SysUserService sysUserService;

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


    @ApiOperation(value="测试权限", notes="测试权限")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Result deleteSysUser(){
        SysUser sysUser = new SysUser();
        sysUser.setModidyDate(new Date());
        sysUser.setCreateDate(new Date());
        sysUser.setEamil("69934765@qq.com");
        sysUser.setNickName("jack111");
        sysUser.setPassword("hibeg9ez11");
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(1L);
        roleIds.add(2L);
        roleIds.add(3L);
        sysUser.setRoleIds(roleIds);
        sysUser.setSalt("123123123123");
        sysUser.setState((byte)1);
        sysUser.setTelNum("15607109985");
        sysUser.setUsername("crystal11");

        sysUser.setUserId(86L);
        sysUserService.updateSysUser(sysUser);
        return ResultUtils.success();
    }



    @ApiOperation(value="测试登录", notes="测试登录")
    @RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String",paramType = "path"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String",paramType = "path")
    })
    @ResponseBody
    public Result login(@PathVariable(value = "username") String username, @PathVariable(value = "password") String password) throws IOException {
//		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//		if(!captcha.equalsIgnoreCase(kaptcha)){
//			return R.error("验证码不正确");
//		}

        try {
            Subject subject = ShiroUtils.getSubject();
            //sha256加密
            //password = new Sha256Hash(password).toHex();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return ResultUtils.error(-1,"用户名错误");
            //return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            //return R.error(e.getMessage());
            return ResultUtils.error(-1,"密码错误");
        } catch (LockedAccountException e) {
            //return R.error(e.getMessage());
            return ResultUtils.error(-1,"账户被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            //return R.error("账户验证失败");
            return ResultUtils.error(-1,"账户验证失败");
        }

//        Set<String> permsSet = new HashSet<>();
//        if (null != ShiroUtils.getUserEntity()) {
//            permsSet = ShiroUtils.getUserEntity().getPermsSet();
//        }
//        return R.ok().put("permsSet", permsSet);
        return ResultUtils.success();
    }
}
