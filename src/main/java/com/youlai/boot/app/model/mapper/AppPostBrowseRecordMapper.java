package com.youlai.boot.app.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppPostBrowseRecord;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppPostBrowseRecordQuery;
import com.youlai.boot.app.model.vo.AppPostBrowseRecordVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 帖子浏览记录Mapper接口
 *
 * @author yuyu
 * @since 2025-05-03 12:51
 */
@Mapper
public interface AppPostBrowseRecordMapper extends BaseMapper<AppPostBrowseRecord> {

    /**
     * 获取帖子浏览记录分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppPostBrowseRecordVO> getAppPostBrowseRecordPage(Page<AppPostBrowseRecordVO> page, AppPostBrowseRecordQuery queryParams);

}
