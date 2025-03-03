package com.youlai.boot.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.mapper.AppPostCommentMapper;
import com.youlai.boot.app.service.AppPostCommentService;
import com.youlai.boot.app.model.entity.AppPostComment;
import com.youlai.boot.app.model.form.AppPostCommentForm;
import com.youlai.boot.app.model.query.AppPostCommentQuery;
import com.youlai.boot.app.model.vo.AppPostCommentVO;
import com.youlai.boot.app.converter.AppPostCommentConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 图文评论服务实现类
 *
 * @author yuyu
 * @since 2025-03-01 17:24
 */
@Service
@RequiredArgsConstructor
public class AppPostCommentServiceImpl extends ServiceImpl<AppPostCommentMapper, AppPostComment> implements AppPostCommentService {

    private final AppPostCommentConverter appPostCommentConverter;

    /**
    * 获取图文评论分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppPostCommentVO>} 图文评论分页列表
    */
    @Override
    public IPage<AppPostCommentVO> getAppPostCommentPage(AppPostCommentQuery queryParams) {
        Page<AppPostCommentVO> pageVO = this.baseMapper.getAppPostCommentPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取图文评论表单数据
     *
     * @param id 图文评论ID
     * @return
     */
    @Override
    public AppPostCommentForm getAppPostCommentFormData(Long id) {
        AppPostComment entity = this.getById(id);
        return appPostCommentConverter.toForm(entity);
    }
    
    /**
     * 新增图文评论
     *
     * @param formData 图文评论表单对象
     * @return
     */
    @Override
    public boolean saveAppPostComment(AppPostCommentForm formData) {
        AppPostComment entity = appPostCommentConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新图文评论
     *
     * @param id   图文评论ID
     * @param formData 图文评论表单对象
     * @return
     */
    @Override
    public boolean updateAppPostComment(Long id,AppPostCommentForm formData) {
        AppPostComment entity = appPostCommentConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除图文评论
     *
     * @param ids 图文评论ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppPostComments(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的图文评论数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
