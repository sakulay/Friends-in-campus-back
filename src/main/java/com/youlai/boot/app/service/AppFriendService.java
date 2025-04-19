package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppFriend;
import com.youlai.boot.app.model.form.AppFriendForm;
import com.youlai.boot.app.model.query.AppFriendQuery;
import com.youlai.boot.app.model.vo.AppFriendVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.boot.app.model.vo.FriendSimpleVO;

/**
 * 用户好友关系服务类
 *
 * @author yuyu
 * @since 2025-02-28 18:25
 */
public interface AppFriendService extends IService<AppFriend> {

    /**
     *用户好友关系分页列表
     *
     * @return
     */
    IPage<AppFriendVO> getAppFriendPage(AppFriendQuery queryParams);

    /**
     * 获取用户好友关系表单数据
     *
     * @param id 用户好友关系ID
     * @return
     */
     AppFriendForm getAppFriendFormData(Long id);

    /**
     * 新增用户好友关系
     *
     * @param formData 用户好友关系表单对象
     * @return
     */
    boolean saveAppFriend(AppFriendForm formData);

    /**
     * 修改用户好友关系
     *
     * @param id   用户好友关系ID
     * @param formData 用户好友关系表单对象
     * @return
     */
    boolean updateAppFriend(Long id, AppFriendForm formData);

    /**
     * 删除用户好友关系
     *
     * @param ids 用户好友关系ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppFriends(String ids);

    IPage<FriendSimpleVO> getAppFriendListByStudentId(Long studentId, String keyword);

    Integer getAppFriendRelationship(Long studentId, Long friendId);

    FriendSimpleVO getAppFriendInfo(Long studentId, Long friendId);
}
