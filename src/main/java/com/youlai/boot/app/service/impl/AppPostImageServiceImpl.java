package com.youlai.boot.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.model.mapper.AppPostImageMapper;
import com.youlai.boot.app.service.AppPostImageService;
import com.youlai.boot.app.model.entity.AppPostImage;
import com.youlai.boot.app.model.form.AppPostImageForm;
import com.youlai.boot.app.model.query.AppPostImageQuery;
import com.youlai.boot.app.model.vo.AppPostImageVO;
import com.youlai.boot.app.converter.AppPostImageConverter;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 图文图片服务实现类
 *
 * @author yuyu
 * @since 2025-03-01 19:27
 */
@Service
@RequiredArgsConstructor
public class AppPostImageServiceImpl extends ServiceImpl<AppPostImageMapper, AppPostImage> implements AppPostImageService {

    private final AppPostImageConverter appPostImageConverter;

    /**
    * 获取图文图片分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppPostImageVO>} 图文图片分页列表
    */
    @Override
    public IPage<AppPostImageVO> getAppPostImagePage(AppPostImageQuery queryParams) {
        Page<AppPostImageVO> pageVO = this.baseMapper.getAppPostImagePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取图文图片表单数据
     *
     * @param id 图文图片ID
     * @return
     */
    @Override
    public AppPostImageForm getAppPostImageFormData(Long id) {
        AppPostImage entity = this.getById(id);
        return appPostImageConverter.toForm(entity);
    }
    
    /**
     * 新增图文图片
     *
     * @param formData 图文图片表单对象
     * @return
     */
    @Override
    public boolean saveAppPostImage(AppPostImageForm formData) {
        AppPostImage entity = appPostImageConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新图文图片
     *
     * @param id   图文图片ID
     * @param formData 图文图片表单对象
     * @return
     */
    @Override
    public boolean updateAppPostImage(Long id,AppPostImageForm formData) {
        AppPostImage entity = appPostImageConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除图文图片
     *
     * @param ids 图文图片ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppPostImages(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的图文图片数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
