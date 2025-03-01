package com.youlai.boot.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.mapper.AppChatMessageMapper;
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

}
