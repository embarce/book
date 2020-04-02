package com.embrace.demo.goods.service;

import com.embrace.demo.goods.dao.GoodsDao;
import com.embrace.demo.goods.entity.goodsInfo;
import com.embrace.demo.util.AppResponse;
import com.embrace.demo.util.JsonUtils;
import com.embrace.demo.util.RedisOperator;
import com.embrace.demo.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 实现类
 * @author 72937
 * @Date 2020-03-23
 */
@Service
public class GoodsService {
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private RedisOperator redisOperator;
    public AppResponse setKey(goodsInfo goodsInfo){
        String jsonString=JsonUtils.toJson(goodsInfo);
        if(null==redisOperator.get("goods1")){
            redisOperator.set("goods1",jsonString);
            return AppResponse.success("新增成功");
        }
        else return AppResponse.bizError("新增失败redis已存在");
    }
    public AppResponse getKey(String goodsInfo){
        String info=redisOperator.get(goodsInfo);
        return AppResponse.success("查询成功",JsonUtils.fromJson(info,PageInfo.class));
    }
    /**
     * goods 新增商品
     * @param goodsInfo
     * @return
     * @author 72937
     * @Date 2020-03-23
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addGoods(goodsInfo goodsInfo) {
        // 校验商品是否存在
        int countgoodsBybookId = goodsDao.countgoodsBybookId(goodsInfo.getBookId());
        if(0 !=countgoodsBybookId ) {
            return AppResponse.bizError("商品账号已存在，请重新输入！");
        }
        goodsInfo.setGoodId(StringUtil.getCommonCode(2));
        goodsInfo.setIsDeleted(0);
        // 新增商品
        int count = goodsDao.addGoods(goodsInfo);
        if(0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * 查询列表
     * @param goodsInfo
     * @return
     * @Author 7293747
     * @Date 2020-03-25
     */
    public AppResponse listGoods(goodsInfo goodsInfo){
        PageHelper.startPage(goodsInfo.getPageNum(), goodsInfo.getPageSize());
        List<goodsInfo> goodsInfoList = goodsDao.listGoodsByPage(goodsInfo);
        if(null==redisOperator.get("goods1")){
            // 包装Page对象
            PageInfo<goodsInfo> pageData = new PageInfo<goodsInfo>(goodsInfoList);
            String jsonString=JsonUtils.toJson(pageData);
            redisOperator.set("goods1",jsonString,60*5);
            return AppResponse.success("查询成功！",pageData);
        }
        else return AppResponse.bizError("新增失败redis已存在");
    }

    /**
     * 删除商品
     * @pram goodsId
     * @author 72937
     * @date 2020-3-25
     * @return AppResponse
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteGoods(String goodsId,String userId) {
        List<String> listCode = Arrays.asList(goodsId.split(","));
        AppResponse appResponse = AppResponse.success("删除成功！");
        // 删除用户
        int count = goodsDao.deleteGoods(listCode,userId);
        if(0 == count) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        return appResponse;
    }

    /**
     * 修改商品信息
     * @param goodsInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateGoods(goodsInfo goodsInfo){
        AppResponse appResponse=AppResponse.success("修改成功");
        // 校验商品是否存在
        int countgoodsBybookId = goodsDao.countgoodsBybookId(goodsInfo.getGoodId());
        if(0 ==countgoodsBybookId ) {
            return AppResponse.bizError("商品账号不存在，请重新输入！");
        }
        //修改商品信息
        int count = goodsDao.updateGoods(goodsInfo);
        if(0!=count){
            appResponse=AppResponse.versionError("修改成功，请刷新页面");
            return appResponse;
        }
        return appResponse;
    }

    /**
     * 查询商品详情
     * @param goodsId
     * @return
     */
    public AppResponse getGoodsByGoodsId(String goodsId){
        goodsInfo goodsInfo=goodsDao.getGoodsByGoodsId(goodsId);
        return AppResponse.success("查询成功",goodsInfo);
    }

    /**
     * 上下架商品
     * @param goodsId
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateGoodsUpper(String goodsId,String userId){
        List<String> listCode = Arrays.asList(goodsId.split(","));
        AppResponse appResponse=AppResponse.success("上架成功");
        int count = goodsDao.updateGoodsUpper(listCode,userId);
        if(0==count){
            appResponse = AppResponse.bizError("上架失败，请重试！");
        }
        return appResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateGoodsLower(String goodsId,String userId){
        List<String> listCode = Arrays.asList(goodsId.split(","));
        AppResponse appResponse=AppResponse.success("下架成功");
        int count = goodsDao.updateGoodsLower(listCode,userId);
        if(0==count){
            appResponse = AppResponse.bizError("下架失败，请重试！");
        }
        return appResponse;
    }
}
