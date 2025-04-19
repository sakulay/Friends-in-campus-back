package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppFriendRequest;
import com.youlai.boot.app.model.form.AppFriendRequestForm;
import com.youlai.boot.app.model.query.AppFriendRequestQuery;
import com.youlai.boot.app.model.vo.AppFriendRequestVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.boot.app.model.vo.FriendSimpleVO;

/**
 * 好友申请服务类
 *
 * @author yuyu
 * @since 2025-02-28 18:38
 */
public interface AppFriendRequestService extends IService<AppFriendRequest> {

    /**
     *好友申请分页列表
     *
     * @return
     */
    IPage<AppFriendRequestVO> getAppFriendRequestPage(AppFriendRequestQuery queryParams);

    /**
     * 获取好友申请表单数据
     *
     * @param id 好友申请ID
     * @return
     */
     AppFriendRequestForm getAppFriendRequestFormData(Long id);

    /**
     * 新增好友申请
     *
     * @param formData 好友申请表单对象
     * @return
     */
    boolean saveAppFriendRequest(AppFriendRequestForm formData);

    /**
     * 通过好友请求
     * @param id 请求id
     * @return
     */
    boolean passRequest(Long id);

    /**
     * 修改好友申请
     *
     * @param id   好友申请ID
     * @param formData 好友申请表单对象
     * @return
     */
    boolean updateAppFriendRequest(Long id, AppFriendRequestForm formData);

    /**
     * 删除好友申请
     *
     * @param ids 好友申请ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppFriendRequests(String ids);

    AppFriendRequestVO getFriendRequestBySenderIdAndReceiverId(Long studentId, Long friendId);

    IPage<FriendSimpleVO> getAppFriendRequestWithInfoPage(AppFriendRequestQuery queryParams);

    boolean rejectRequest(Long id);
}
