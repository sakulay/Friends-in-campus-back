package com.youlai.boot.app.service.impl;

import com.youlai.boot.app.model.mapper.AppPostCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.service.AppPostCategoryService;
import com.youlai.boot.app.model.entity.AppPostCategory;
import com.youlai.boot.app.model.form.AppPostCategoryForm;
import com.youlai.boot.app.model.query.AppPostCategoryQuery;
import com.youlai.boot.app.model.vo.AppPostCategoryVO;
import com.youlai.boot.app.converter.AppPostCategoryConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 帖子分类服务实现类
 *
 * @author yuyu
 * @since 2025-05-14 13:00
 */
@Service
@RequiredArgsConstructor
public class AppPostCategoryServiceImpl extends ServiceImpl<AppPostCategoryMapper, AppPostCategory> implements AppPostCategoryService {

    private final AppPostCategoryConverter appPostCategoryConverter;

    /**
    * 获取帖子分类分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppPostCategoryVO>} 帖子分类分页列表
    */
    @Override
    public IPage<AppPostCategoryVO> getAppPostCategoryPage(AppPostCategoryQuery queryParams) {
        Page<AppPostCategoryVO> pageVO = this.baseMapper.getAppPostCategoryPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取帖子分类表单数据
     *
     * @param id 帖子分类ID
     * @return
     */
    @Override
    public AppPostCategoryForm getAppPostCategoryFormData(Long id) {
        AppPostCategory entity = this.getById(id);
        return appPostCategoryConverter.toForm(entity);
    }
    
    /**
     * 新增帖子分类
     *
     * @param formData 帖子分类表单对象
     * @return
     */
    @Override
    public boolean saveAppPostCategory(AppPostCategoryForm formData) {
        AppPostCategory entity = appPostCategoryConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新帖子分类
     *
     * @param id   帖子分类ID
     * @param formData 帖子分类表单对象
     * @return
     */
    @Override
    public boolean updateAppPostCategory(Long id,AppPostCategoryForm formData) {
        AppPostCategory entity = appPostCategoryConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除帖子分类
     *
     * @param ids 帖子分类ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppPostCategorys(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的帖子分类数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
