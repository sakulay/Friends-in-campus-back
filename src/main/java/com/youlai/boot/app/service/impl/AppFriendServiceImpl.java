package com.youlai.boot.app.service.impl;

import com.youlai.boot.app.model.form.AppUserProfileForm;
import com.youlai.boot.app.model.vo.AppFriendRequestVO;
import com.youlai.boot.app.service.AppFriendRequestService;
import groovy.lang.Lazy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.model.mapper.AppFriendMapper;
import com.youlai.boot.app.service.AppFriendService;
import com.youlai.boot.app.model.entity.AppFriend;
import com.youlai.boot.app.model.form.AppFriendForm;
import com.youlai.boot.app.model.query.AppFriendQuery;
import com.youlai.boot.app.model.vo.AppFriendVO;
import com.youlai.boot.app.model.vo.FriendSimpleVO; // 假设存在 FriendSimpleVO 类
import com.youlai.boot.app.converter.AppFriendConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 用户好友关系服务实现类
 *
 * @author yuyu
 * @since 2025-02-28 18:25
 */
@Service
@RequiredArgsConstructor
public class AppFriendServiceImpl extends ServiceImpl<AppFriendMapper, AppFriend> implements AppFriendService {

    private final AppFriendConverter appFriendConverter;
    private final AppFriendMapper appFriendMapper;

    private final AppFriendRequestService appFriendRequestService;
    /**
    * 获取用户好友关系分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppFriendVO>} 用户好友关系分页列表
    */
    @Override
    public IPage<AppFriendVO> getAppFriendPage(AppFriendQuery queryParams) {
        Page<AppFriendVO> pageVO = this.baseMapper.getAppFriendPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取用户好友关系表单数据
     *
     * @param id 用户好友关系ID
     * @return
     */
    @Override
    public AppFriendForm getAppFriendFormData(Long id) {
        AppFriend entity = this.getById(id);
        return appFriendConverter.toForm(entity);
    }
    
    /**
     * 新增用户好友关系
     *
     * @param formData 用户好友关系表单对象
     * @return
     */
    @Override
    public boolean saveAppFriend(AppFriendForm formData) {
        AppFriend entity = appFriendConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新用户好友关系
     *
     * @param id   用户好友关系ID
     * @param formData 用户好友关系表单对象
     * @return
     */
    @Override
    public boolean updateAppFriend(Long id,AppFriendForm formData) {
        AppFriend entity = appFriendConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除用户好友关系
     *
     * @param ids 用户好友关系ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppFriends(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的用户好友关系数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 根据学生ID查询好友列表，支持模糊查询
     *
     * @param studentId 学生ID
     * @param keyword 模糊查询关键字
     * @return {@link IPage<AppFriendVO>} 用户好友关系分页列表
     */
    @Override
    public IPage<FriendSimpleVO> getAppFriendListByStudentId(Long studentId, String keyword) {
        Page<FriendSimpleVO> page = new Page<>();
        // 假设这里有一个方法可以查询好友列表，支持模糊查询
        IPage<FriendSimpleVO> result = appFriendMapper.getAppFriendListByStudentId(page, studentId, keyword);
        return result;
    }

    @Override
    public Integer getAppFriendRelationship(Long studentId, Long friendId) {
        Integer status = appFriendMapper.getAppFriendRelationship(studentId, friendId);
        return status == null? 0 : status;
    }

    /**
     *
     * @param studentId 查询人Id
     * @param friendId 查询对象Id
     * @return
     */
    @Override
    public FriendSimpleVO getAppFriendInfo(Long studentId, Long friendId) {
        FriendSimpleVO friendSimpleVO = appFriendMapper.getAppFriendInfo(studentId, friendId);
        if(friendSimpleVO != null) {
            // status不存在说明 用户可能申请了，或者没有进行过好友申请
            if(friendSimpleVO.getStatus() == null) {
                AppFriendRequestVO appFriendRequestVO = appFriendRequestService.getFriendRequestBySenderIdAndReceiverId(studentId, friendId);
                // 如果存在申请记录，则根据申请记录的状态设置好友关系状态
                if(appFriendRequestVO != null) {
                    friendSimpleVO.setStatus(appFriendRequestVO.getStatus());
                } else {
                    // 如果不存在申请记录，则默认为未添加
                    friendSimpleVO.setStatus(2);
                }
            }
        }

        return friendSimpleVO;
    }
}