package com.embrace.demo.cate.dao;

import com.embrace.demo.cate.entity.CateInfo;
import com.embrace.demo.cate.entity.cateParentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface CateDao {
    /**
     * 查询分类是否存在
     * @param cateName
     * @return
     */
    int countCateByName(@Param("cateName") String cateName);

    /**
     * 新增分类
     * @param cateInfo
     * @return
     */
    int addCate(CateInfo cateInfo);
    /**
     * 查询父级分类列表
     */
    List<CateInfo> getCate();

    /**
     * 查询子类
     * @param chId
     * @return
     */
    List<cateParentInfo> findCateById(String chId);
    int addMq(String json);

}
