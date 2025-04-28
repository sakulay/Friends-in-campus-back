package com.youlai.boot.app.service.impl;

import com.youlai.boot.app.model.mapper.AppChatMessageMapper;
import com.youlai.boot.app.model.vo.MessageVO;
import com.youlai.boot.app.model.vo.UnreadVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.service.AppChatMessageService;
import com.youlai.boot.app.model.entity.AppChatMessage;
import com.youlai.boot.app.model.form.AppChatMessageForm;
import com.youlai.boot.app.model.query.AppChatMessageQuery;
import com.youlai.boot.app.model.vo.AppChatMessageVO;
import com.youlai.boot.app.converter.AppChatMessageConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户聊天记录服务实现类
 *
 * @author yuyu
 * @since 2025-02-28 18:44
 */
@Service
@RequiredArgsConstructor
public class AppChatMessageServiceImpl extends ServiceImpl<AppChatMessageMapper, AppChatMessage> implements AppChatMessageService {

    private final AppChatMessageConverter appChatMessageConverter;

    /**
    * 获取用户聊天记录分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppChatMessageVO>} 用户聊天记录分页列表
    */
    @Override
    public IPage<AppChatMessageVO> getAppChatMessagePage(AppChatMessageQuery queryParams) {
        Page<AppChatMessageVO> pageVO = this.baseMapper.getAppChatMessagePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取用户聊天记录表单数据
     *
     * @param id 用户聊天记录ID
     * @return
     */
    @Override
    public AppChatMessageForm getAppChatMessageFormData(Long id) {
        AppChatMessage entity = this.getById(id);
        return appChatMessageConverter.toForm(entity);
    }
    
    /**
     * 新增用户聊天记录
     *
     * @param formData 用户聊天记录表单对象
     * @return
     */
    @Override
    public boolean saveAppChatMessage(AppChatMessageForm formData) {
        AppChatMessage entity = appChatMessageConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新用户聊天记录
     *
     * @param id   用户聊天记录ID
     * @param formData 用户聊天记录表单对象
     * @return
     */
    @Override
    public boolean updateAppChatMessage(Long id,AppChatMessageForm formData) {
        AppChatMessage entity = appChatMessageConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除用户聊天记录
     *
     * @param ids 用户聊天记录ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppChatMessages(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的用户聊天记录数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    @Transactional
    @Override
    public String findBySenderAndReceiverAndType(Long senderId, Long receiverId) {
        AppChatMessage existing = this.lambdaQuery()
                .eq(AppChatMessage::getSenderId, senderId)
                .eq(AppChatMessage::getReceiverId, receiverId)
                .eq(AppChatMessage::getMessageType, 4)
                .eq(AppChatMessage::getIsDeleted, 0)
                .last("LIMIT 1")
                .one();
        String newContent = "1";
        if (existing != null) {
            // 存在记录，更新 content
            newContent = String.valueOf(Integer.parseInt(existing.getMessageContent()) + 1);
            existing.setMessageContent(newContent);
            this.updateById(existing);
        } else {
            // 不存在记录，插入新数据
            AppChatMessage newMsg = new AppChatMessage();
            newMsg.setSenderId(senderId);
            newMsg.setReceiverId(receiverId);
            newMsg.setMessageType(4);
            newMsg.setMessageContent(newContent);
            newMsg.setIsDeleted(0);
            this.save(newMsg);
        }

        return newContent;
    }

    @Override
    public List<UnreadVO> getAppChatUnread(Long id) {
        List<UnreadVO> unreads = this.baseMapper.getAppChatUnreadById(id);
        for (UnreadVO unread : unreads) {
            String latestContent = this.baseMapper.getLatestContentBySenderAndReceiver(Long.valueOf(unread.getSender()), id);
            unread.setLatestContent(latestContent);
        }
        return unreads;
    }

    @Override
    public List<MessageVO> getAppChatMessages(Long id, Long otherId) {
        return this.baseMapper.getAppChatMessages(id, otherId);
    }

    /**
     * 清空未读消息数量
     * @param senderId
     * @param receiverId
     * @return
     */
    @Override
    public boolean readed(Long senderId, Long receiverId) {
        boolean update = true;
        AppChatMessage existing = this.lambdaQuery()
                .eq(AppChatMessage::getSenderId, senderId)
                .eq(AppChatMessage::getReceiverId, receiverId)
                .eq(AppChatMessage::getMessageType, 4)
                .eq(AppChatMessage::getIsDeleted, 0)
                .last("LIMIT 1")
                .one();
        if (existing != null) {
            // 存在记录，更新 content
            existing.setMessageContent("0");
            update = this.updateById(existing);
        }
        return update;
    }

}
