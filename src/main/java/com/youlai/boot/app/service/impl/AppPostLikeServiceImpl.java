package com.youlai.boot.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.model.mapper.AppPostLikeMapper;
import com.youlai.boot.app.service.AppPostLikeService;
import com.youlai.boot.app.model.entity.AppPostLike;
import com.youlai.boot.app.model.form.AppPostLikeForm;
import com.youlai.boot.app.model.query.AppPostLikeQuery;
import com.youlai.boot.app.model.vo.AppPostLikeVO;
import com.youlai.boot.app.converter.AppPostLikeConverter;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 用户点赞记录服务实现类
 *
 * @author yuyu
 * @since 2025-03-01 20:51
 */
@Service
@RequiredArgsConstructor
public class AppPostLikeServiceImpl extends ServiceImpl<AppPostLikeMapper, AppPostLike> implements AppPostLikeService {

    private final AppPostLikeConverter appPostLikeConverter;

    /**
    * 获取用户点赞记录分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppPostLikeVO>} 用户点赞记录分页列表
    */
    @Override
    public IPage<AppPostLikeVO> getAppPostLikePage(AppPostLikeQuery queryParams) {
        Page<AppPostLikeVO> pageVO = this.baseMapper.getAppPostLikePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取用户点赞记录表单数据
     *
     * @param id 用户点赞记录ID
     * @return
     */
    @Override
    public AppPostLikeForm getAppPostLikeFormData(Long id) {
        AppPostLike entity = this.getById(id);
        return appPostLikeConverter.toForm(entity);
    }
    
    /**
     * 新增用户点赞记录
     *
     * @param formData 用户点赞记录表单对象
     * @return
     */
    @Override
    public boolean saveAppPostLike(AppPostLikeForm formData) {
        AppPostLike entity = appPostLikeConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新用户点赞记录
     *
     * @param id   用户点赞记录ID
     * @param formData 用户点赞记录表单对象
     * @return
     */
    @Override
    public boolean updateAppPostLike(Long id,AppPostLikeForm formData) {
        AppPostLike entity = appPostLikeConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除用户点赞记录
     *
     * @param ids 用户点赞记录ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppPostLikes(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的用户点赞记录数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
