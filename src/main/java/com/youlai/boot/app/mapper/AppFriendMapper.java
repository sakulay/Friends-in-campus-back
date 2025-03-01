package com.youlai.boot.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppFriend;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppFriendQuery;
import com.youlai.boot.app.model.vo.AppFriendVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户好友关系Mapper接口
 *
 * @author yuyu
 * @since 2025-02-28 18:25
 */
@Mapper
public interface AppFriendMapper extends BaseMapper<AppFriend> {

    /**
     * 获取用户好友关系分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppFriendVO> getAppFriendPage(Page<AppFriendVO> page, AppFriendQuery queryParams);

}
