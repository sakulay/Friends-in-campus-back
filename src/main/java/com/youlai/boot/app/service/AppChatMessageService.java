package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppChatMessage;
import com.youlai.boot.app.model.form.AppChatMessageForm;
import com.youlai.boot.app.model.query.AppChatMessageQuery;
import com.youlai.boot.app.model.vo.AppChatMessageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.boot.app.model.vo.MessageVO;
import com.youlai.boot.app.model.vo.UnreadVO;

import java.util.List;

/**
 * 用户聊天记录服务类
 *
 * @author yuyu
 * @since 2025-02-28 18:44
 */
public interface AppChatMessageService extends IService<AppChatMessage> {

    /**
     *用户聊天记录分页列表
     *
     * @return
     */
    IPage<AppChatMessageVO> getAppChatMessagePage(AppChatMessageQuery queryParams);

    /**
     * 获取用户聊天记录表单数据
     *
     * @param id 用户聊天记录ID
     * @return
     */
     AppChatMessageForm getAppChatMessageFormData(Long id);

    /**
     * 新增用户聊天记录
     *
     * @param formData 用户聊天记录表单对象
     * @return
     */
    boolean saveAppChatMessage(AppChatMessageForm formData);

    /**
     * 修改用户聊天记录
     *
     * @param id   用户聊天记录ID
     * @param formData 用户聊天记录表单对象
     * @return
     */
    boolean updateAppChatMessage(Long id, AppChatMessageForm formData);

    /**
     * 删除用户聊天记录
     *
     * @param ids 用户聊天记录ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppChatMessages(String ids);

    /**
     * 处理未读消息的更新
     * @param senderId
     * @param receiverId
     * @return
     */
    String findBySenderAndReceiverAndType(Long senderId, Long receiverId);

    List<UnreadVO> getAppChatUnread(Long id);

    List<MessageVO> getAppChatMessages(Long id, Long otherId);

    public boolean readed(Long senderId, Long receiverId);
}
