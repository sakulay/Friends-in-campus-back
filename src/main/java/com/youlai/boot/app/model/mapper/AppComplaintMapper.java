package com.youlai.boot.app.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppComplaint;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppComplaintQuery;
import com.youlai.boot.app.model.vo.AppComplaintVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户举报记录Mapper接口
 *
 * @author yuyu
 * @since 2025-04-22 20:49
 */
@Mapper
public interface AppComplaintMapper extends BaseMapper<AppComplaint> {

    /**
     * 获取用户举报记录分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppComplaintVO> getAppComplaintPage(Page<AppComplaintVO> page, AppComplaintQuery queryParams);

}
