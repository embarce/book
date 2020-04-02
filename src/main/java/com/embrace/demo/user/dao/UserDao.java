package com.embrace.demo.user.dao;

import com.embrace.demo.goods.entity.goodsInfo;
import com.embrace.demo.user.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 *
 * @Description :
 * @Author : Embrace
 * @Date :  2020-3-25
*/
@Mapper
public interface UserDao {
    /**
     * 查看是否存在用户
     * @param userAcct 用户信息
     * @return
     */
    int countUserById(@Param("userAcct") String userAcct);
    /**
     * 新增用户
     * @param  userInfo
     * @return
     */
    int addUser(UserInfo userInfo);

    /**
     * 删除用户
     * @param userAcct listCode 选中的用户编号集合
     * @return
     */
    int deleteUser(List<String> listCode,@Param("userAcct") String userAcct);

    /**
     * 分页列表查询用户信息
     * @param userInfo
     * @return
     */
    List<UserInfo> listUserByPage(UserInfo userInfo);

    /**
     * 查询指定的用户的详情
     * @param userAcct
     * @return
     */
    UserInfo getUserById(@Param("userAcct") String userAcct);

    /**
     * 修改用户信息账号检查接口
     * @param userInfo
     * @return
     */
    int countUserAcct(UserInfo userInfo);
     /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    int updateUser(UserInfo userInfo);
}
