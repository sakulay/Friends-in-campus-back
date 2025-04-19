package com.youlai.boot.app.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.boot.app.model.entity.AppFriend;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppFriendQuery;
import com.youlai.boot.app.model.vo.AppFriendVO;
import com.youlai.boot.app.model.vo.FriendSimpleVO;
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

    IPage<FriendSimpleVO> getAppFriendListByStudentId(Page<FriendSimpleVO> page, Long studentId, String keyword);

    Integer getAppFriendRelationship(Long studentId, Long friendId);

    FriendSimpleVO getAppFriendInfo(Long studentId, Long friendId);
}
