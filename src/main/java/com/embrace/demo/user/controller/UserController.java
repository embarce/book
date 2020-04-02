package com.embrace.demo.user.controller;

import com.embrace.demo.user.entity.UserInfo;
import com.embrace.demo.user.service.UserService;
import com.embrace.demo.util.AppResponse;
import com.embrace.demo.util.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Desrciption 用户功能模块
 * @author Embrace
 * @date 2020/3/26 11:06
 */
@RestController
@RequestMapping("/User")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;
    /***
     * @ClassName
     * @Description : 新增用户
     *
     * @param userInfo
     * @Author : Embrace
     * @Date : 2020-03-26
    */
    @PostMapping("addUser")
    public AppResponse addUser(UserInfo userInfo){
        try {
            AppResponse appResponse=userService.addUser(userInfo);
            return appResponse;
        }catch (Exception e){
            logger.error("新增用户失败",e);
            System.out.println(e.toString());
            throw e;
        }
    }
    /***
     * @ClassName
     * @Description : 删除用户
     * @param userAcct
     * @Author : Embrace
     * @Date : 2020-3-26
    */
    @PostMapping("deleteUser")
    public AppResponse deleteUser(String userList ,String userAcct){
        try{
           AppResponse appResponse=userService.deleteUser(userList,userAcct);
           return appResponse;
        }catch (Exception e){
            logger.error("删除用户失败",e);
            System.out.println(e.toString());
            throw e;
        }
    }
    /***
     * @ClassName
     * @Description : 查询用户列表
     * @param userInfo
     * @Author : Embrace
     * @Date :  2020-3-26
    */
    @RequestMapping(value="listUserByPage")
    public AppResponse listUserByPage(UserInfo userInfo){
        try {
            return userService.listUserByPage(userInfo);
        }catch (Exception e){
            logger.error("查询用户列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * 查询指定用户数据
     * @param userAcct
     * @return
     */
    @RequestMapping(value = "getUserById")
    public AppResponse getUserById(String userAcct){
        try {
            return userService.getUserById(userAcct);
        }catch (Exception e){
            logger.error("查询用户异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }
     /**
      * @ClassName
      * @Description : 修改用户信息
      * @param userInfo
      * @Return :
      * @Author : Embrace
      * @Date : 2020-3-26
     */
    @PostMapping("updateUser")
    public AppResponse updateUser(UserInfo userInfo) {
        try {
            //获取用户id
            String user = AuthUtils.getCurrentUserId();
            userInfo.setLastModifiedBy(user);
            return userService.updateUser(userInfo);
        } catch (Exception e) {
            logger.error("修改用户信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

}
