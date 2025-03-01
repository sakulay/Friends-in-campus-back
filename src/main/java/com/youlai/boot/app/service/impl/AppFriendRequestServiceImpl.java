package com.youlai.boot.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.youlai.boot.app.model.dto.AppUserAuthInfo;
import com.youlai.boot.app.model.entity.AppFriend;
import com.youlai.boot.app.model.entity.AppUser;
import com.youlai.boot.app.model.form.AppFriendForm;
import com.youlai.boot.app.model.form.AppUserProfileForm;
import com.youlai.boot.app.service.AppFriendService;
import com.youlai.boot.common.constant.MyConstans;
import com.youlai.boot.common.exception.BusinessException;
import com.youlai.boot.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.mapper.AppFriendRequestMapper;
import com.youlai.boot.app.service.AppFriendRequestService;
import com.youlai.boot.app.model.entity.AppFriendRequest;
import com.youlai.boot.app.model.form.AppFriendRequestForm;
import com.youlai.boot.app.model.query.AppFriendRequestQuery;
import com.youlai.boot.app.model.vo.AppFriendRequestVO;
import com.youlai.boot.app.converter.AppFriendRequestConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * 好友申请服务实现类
 *
 * @author yuyu
 * @since 2025-02-28 18:38
 */
@Service
@RequiredArgsConstructor
public class AppFriendRequestServiceImpl extends ServiceImpl<AppFriendRequestMapper, AppFriendRequest> implements AppFriendRequestService {

    private final AppFriendRequestConverter appFriendRequestConverter;
    private final AppFriendService appFriendService;
    /**
    * 获取好友申请分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppFriendRequestVO>} 好友申请分页列表
    */
    @Override
    public IPage<AppFriendRequestVO> getAppFriendRequestPage(AppFriendRequestQuery queryParams) {
        Page<AppFriendRequestVO> pageVO = this.baseMapper.getAppFriendRequestPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取好友申请表单数据
     *
     * @param id 好友申请ID
     * @return
     */
    @Override
    public AppFriendRequestForm getAppFriendRequestFormData(Long id) {
        AppFriendRequest entity = this.getById(id);
        return appFriendRequestConverter.toForm(entity);
    }
    
    /**
     * 新增好友申请
     *
     * @param formData 好友申请表单对象
     * @return
     */
    @Override
    public boolean saveAppFriendRequest(AppFriendRequestForm formData) {
        AppFriendRequest entity = appFriendRequestConverter.toEntity(formData);
        return this.save(entity);
    }
    /**
     * 通过好友请求
     * @param id 请求id
     * @return
     */
    @Override
    @Transactional
    public boolean passRequest(Long id) {
        //1.检查申请记录是否存在，并且处于待验证状态(0-待处理，1-已通过，2-已拒绝，3-已撤回)
        AppFriendRequest appFriendRequest = this.getById(id);
        if(appFriendRequest != null && appFriendRequest.getStatus() == 0) {
            //2.若存在，则将status字段的值设为1
            UpdateWrapper<AppFriendRequest> wrapper = new UpdateWrapper<>();
            wrapper
                    .eq("id", id)
                    .set("status", 1);
            this.update(null, wrapper);
            //3.通过请求后，新增好友关系, A->B, B->A
            Long sId = appFriendRequest.getSenderId();
            Long rId = appFriendRequest.getReceiverId();
            AppFriendForm friend_Sender = AppFriendForm.builder()
                    .userId(sId)
                    .friendId(rId)
                    .status(1)
                    .build();
            AppFriendForm friend_Receiver = AppFriendForm.builder()
                    .userId(rId)
                    .friendId(sId)
                    .status(1)
                    .build();
            return appFriendService.saveAppFriend(friend_Sender) && appFriendService.saveAppFriend(friend_Receiver);
        } else throw new BusinessException(ResultCode.INVALID_FIREND_REQUEST);
    }
    /**
     * 更新好友申请
     *
     * @param id   好友申请ID
     * @param formData 好友申请表单对象
     * @return
     */
    @Override
    public boolean updateAppFriendRequest(Long id,AppFriendRequestForm formData) {
        AppFriendRequest entity = appFriendRequestConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除好友申请
     *
     * @param ids 好友申请ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppFriendRequests(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的好友申请数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
