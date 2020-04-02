package com.embrace.demo.cate.service;

import com.embrace.demo.activcemq.producer.ProducerController;
import com.embrace.demo.carouselPicture.dao.CarouserlDao;
import com.embrace.demo.cate.dao.CateDao;
import com.embrace.demo.cate.entity.CateInfo;
import com.embrace.demo.cate.entity.cateParentInfo;
import com.embrace.demo.util.AppResponse;
import com.embrace.demo.util.JsonUtils;
import com.embrace.demo.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Embrace
 * @date 2020/3/27 16:33
 */
@Service
public class CateService {
    @Resource
    private CateDao cateDao;
    @Resource
    private ProducerController producerController;

    /**
     * 新增分类
     * @param cateInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addCate(CateInfo cateInfo){
        cateInfo.setCateId(StringUtil.getCommonCode(2));
        cateInfo.setIsDeleted(0);
        cateInfo.setCreateBy("1");
        int num=cateDao.countCateByName(cateInfo.getCateName());
        producerController.sendQueue(cateInfo);
        //新增
        if(num==0){
            int count=cateDao.addCate(cateInfo);
            cateDao.addMq(JsonUtils.toJson(cateInfo));
            if(0==count){
                return AppResponse.bizError("新增失败，请重试");
            }
            return AppResponse.success("新增成功");
        }
        else {
            return AppResponse.success("分类已存在");
        }
    }
    public AppResponse getCate(){
        List<CateInfo> infos=cateDao.getCate();
        for (CateInfo cateP : infos) {
           List<cateParentInfo> cateInfoList=cateDao.findCateById(cateP.getCateId());
           cateP.setCi(cateInfoList);
        }

        return AppResponse.success("get",infos);
    }
}
