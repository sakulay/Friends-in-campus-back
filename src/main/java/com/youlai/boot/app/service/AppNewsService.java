package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppNews;
import com.youlai.boot.app.model.form.AppNewsForm;
import com.youlai.boot.app.model.query.AppNewsQuery;
import com.youlai.boot.app.model.vo.AppNewsVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 校园新闻资讯服务类
 *
 * @author yuyu
 * @since 2025-04-20 08:10
 */
public interface AppNewsService extends IService<AppNews> {

    /**
     *校园新闻资讯分页列表
     *
     * @return
     */
    IPage<AppNewsVO> getAppNewsPage(AppNewsQuery queryParams);

    /**
     * 获取校园新闻资讯表单数据
     *
     * @param id 校园新闻资讯ID
     * @return
     */
     AppNewsForm getAppNewsFormData(Long id);

    /**
     * 新增校园新闻资讯
     *
     * @param formData 校园新闻资讯表单对象
     * @return
     */
    boolean saveAppNews(AppNewsForm formData);

    /**
     * 修改校园新闻资讯
     *
     * @param id   校园新闻资讯ID
     * @param formData 校园新闻资讯表单对象
     * @return
     */
    boolean updateAppNews(Long id, AppNewsForm formData);

    /**
     * 删除校园新闻资讯
     *
     * @param ids 校园新闻资讯ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppNewss(String ids);

}
