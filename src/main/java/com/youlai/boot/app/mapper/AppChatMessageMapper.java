package com.youlai.boot.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppChatMessage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppChatMessageQuery;
import com.youlai.boot.app.model.vo.AppChatMessageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户聊天记录Mapper接口
 *
 * @author yuyu
 * @since 2025-02-28 18:44
 */
@Mapper
public interface AppChatMessageMapper extends BaseMapper<AppChatMessage> {

    /**
     * 获取用户聊天记录分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppChatMessageVO> getAppChatMessagePage(Page<AppChatMessageVO> page, AppChatMessageQuery queryParams);

}
