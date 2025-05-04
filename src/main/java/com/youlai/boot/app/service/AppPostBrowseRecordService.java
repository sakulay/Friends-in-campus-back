package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppPostBrowseRecord;
import com.youlai.boot.app.model.form.AppPostBrowseRecordForm;
import com.youlai.boot.app.model.query.AppPostBrowseRecordQuery;
import com.youlai.boot.app.model.vo.AppPostBrowseRecordVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.boot.app.model.vo.AppPostVO;

/**
 * 帖子浏览记录服务类
 *
 * @author yuyu
 * @since 2025-05-03 12:51
 */
public interface AppPostBrowseRecordService extends IService<AppPostBrowseRecord> {

    /**
     *帖子浏览记录分页列表
     *
     * @return
     */
    IPage<AppPostVO> getAppPostBrowseRecordPage(AppPostBrowseRecordQuery queryParams);

    /**
     * 获取帖子浏览记录表单数据
     *
     * @param id 帖子浏览记录ID
     * @return
     */
     AppPostBrowseRecordForm getAppPostBrowseRecordFormData(Long id);

    /**
     * 新增帖子浏览记录
     *
     * @param formData 帖子浏览记录表单对象
     * @return
     */
    boolean saveAppPostBrowseRecord(AppPostBrowseRecordForm formData);

    /**
     * 修改帖子浏览记录
     *
     * @param id   帖子浏览记录ID
     * @param formData 帖子浏览记录表单对象
     * @return
     */
    boolean updateAppPostBrowseRecord(Long id, AppPostBrowseRecordForm formData);

    /**
     * 删除帖子浏览记录
     *
     * @param ids 帖子浏览记录ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppPostBrowseRecords(String ids);

    /**
     * 根据浏览者ID和帖子ID删除浏览记录
     *
     * @param viewerId 浏览者ID
     * @param postId 帖子ID
     * @return
     */
    boolean deleteAppPostBrowseRecord(Integer viewerId, Long postId);

}
