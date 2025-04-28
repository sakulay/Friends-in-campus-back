package com.youlai.boot.app.service.impl;

import com.youlai.boot.app.model.vo.FriendSimpleVO;
import com.youlai.boot.core.security.model.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.model.mapper.AppComplaintMapper;
import com.youlai.boot.app.service.AppComplaintService;
import com.youlai.boot.app.model.entity.AppComplaint;
import com.youlai.boot.app.model.form.AppComplaintForm;
import com.youlai.boot.app.model.query.AppComplaintQuery;
import com.youlai.boot.app.model.vo.AppComplaintVO;
import com.youlai.boot.app.converter.AppComplaintConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 用户举报记录服务实现类
 *
 * @author yuyu
 * @since 2025-04-22 20:49
 */
@Service
@RequiredArgsConstructor
public class AppComplaintServiceImpl extends ServiceImpl<AppComplaintMapper, AppComplaint> implements AppComplaintService {

    private final AppComplaintConverter appComplaintConverter;

    /**
    * 获取用户举报记录分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppComplaintVO>} 用户举报记录分页列表
    */
    @Override
    public IPage<AppComplaintVO> getAppComplaintPage(AppComplaintQuery queryParams) {
        Page<AppComplaintVO> pageVO = this.baseMapper.getAppComplaintPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取用户举报记录表单数据
     *
     * @param id 用户举报记录ID
     * @return
     */
    @Override
    public AppComplaintForm getAppComplaintFormData(Long id) {
        AppComplaint entity = this.getById(id);
        return appComplaintConverter.toForm(entity);
    }
    
    /**
     * 新增用户举报记录
     *
     * @param formData 用户举报记录表单对象
     * @return
     */
    @Override
    public boolean saveAppComplaint(AppComplaintForm formData) {
        // 从 JWT 令牌中获取 studentId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(authority -> {
            if (authority.getAuthority().equals("ROLE_STUDENT")) {
                // 如果是学生
                Long studentId = Long.parseLong(((AppUserDetails)authentication.getPrincipal()).getStudentId());
                formData.setUserId(studentId);
            }
        });
        AppComplaint entity = appComplaintConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新用户举报记录
     *
     * @param id   用户举报记录ID
     * @param formData 用户举报记录表单对象
     * @return
     */
    @Override
    public boolean updateAppComplaint(Long id,AppComplaintForm formData) {
        AppComplaint entity = appComplaintConverter.toEntity(formData);
        entity.setId(id);
        return this.updateById(entity);
    }
    
    /**
     * 删除用户举报记录
     *
     * @param ids 用户举报记录ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppComplaints(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的用户举报记录数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
