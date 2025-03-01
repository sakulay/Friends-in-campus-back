package com.youlai.boot.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppFriendRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppFriendRequestQuery;
import com.youlai.boot.app.model.vo.AppFriendRequestVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 好友申请Mapper接口
 *
 * @author yuyu
 * @since 2025-02-28 18:38
 */
@Mapper
public interface AppFriendRequestMapper extends BaseMapper<AppFriendRequest> {

    /**
     * 获取好友申请分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppFriendRequestVO> getAppFriendRequestPage(Page<AppFriendRequestVO> page, AppFriendRequestQuery queryParams);

}
