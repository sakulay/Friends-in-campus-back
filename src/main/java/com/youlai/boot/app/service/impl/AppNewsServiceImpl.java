package com.youlai.boot.app.service.impl;

import com.youlai.boot.app.model.mapper.AppNewsMapper;
import com.youlai.boot.common.exception.BusinessException;
import com.youlai.boot.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.service.AppNewsService;
import com.youlai.boot.app.model.entity.AppNews;
import com.youlai.boot.app.model.form.AppNewsForm;
import com.youlai.boot.app.model.query.AppNewsQuery;
import com.youlai.boot.app.model.vo.AppNewsVO;
import com.youlai.boot.app.converter.AppNewsConverter;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 校园新闻资讯服务实现类
 *
 * @author yuyu
 * @since 2025-04-20 08:10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AppNewsServiceImpl extends ServiceImpl<AppNewsMapper, AppNews> implements AppNewsService {

    private final AppNewsConverter appNewsConverter;

    /**
    * 获取校园新闻资讯分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppNewsVO>} 校园新闻资讯分页列表
    */
    @Override
    public IPage<AppNewsVO> getAppNewsPage(AppNewsQuery queryParams) {
        Page<AppNewsVO> pageVO = this.baseMapper.getAppNewsPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取校园新闻资讯表单数据
     *
     * @param id 校园新闻资讯ID
     * @return
     */
    @Override
    public AppNewsForm getAppNewsFormData(Long id) {
        AppNews entity = this.getById(id);
        return appNewsConverter.toForm(entity);
    }
    
    /**
     * 新增校园新闻资讯
     *
     * @param formData 校园新闻资讯表单对象
     * @return
     */
    @Override
    public boolean saveAppNews(AppNewsForm formData) {
        boolean exists = lambdaQuery()
                .eq(AppNews::getTitle, formData.getTitle())
                .eq(AppNews::getDate, formData.getDate())
                .eq(AppNews::getIsDeleted, 0)
                .exists();
        if (exists) {
            log.info("跳过重复资讯：{}", formData.getTitle());
            throw new BusinessException(ResultCode.NEWS_ALREADY_EXISTS);
        }
        AppNews entity = appNewsConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新校园新闻资讯
     *
     * @param id   校园新闻资讯ID
     * @param formData 校园新闻资讯表单对象
     * @return
     */
    @Override
    public boolean updateAppNews(Long id,AppNewsForm formData) {
        AppNews entity = appNewsConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除校园新闻资讯
     *
     * @param ids 校园新闻资讯ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppNewss(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的校园新闻资讯数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
