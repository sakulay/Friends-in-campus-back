package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppComplaint;
import com.youlai.boot.app.model.form.AppComplaintForm;
import com.youlai.boot.app.model.query.AppComplaintQuery;
import com.youlai.boot.app.model.vo.AppComplaintVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户举报记录服务类
 *
 * @author yuyu
 * @since 2025-04-22 20:49
 */
public interface AppComplaintService extends IService<AppComplaint> {

    /**
     *用户举报记录分页列表
     *
     * @return
     */
    IPage<AppComplaintVO> getAppComplaintPage(AppComplaintQuery queryParams);

    /**
     * 获取用户举报记录表单数据
     *
     * @param id 用户举报记录ID
     * @return
     */
     AppComplaintForm getAppComplaintFormData(Long id);

    /**
     * 新增用户举报记录
     *
     * @param formData 用户举报记录表单对象
     * @return
     */
    boolean saveAppComplaint(AppComplaintForm formData);

    /**
     * 修改用户举报记录
     *
     * @param id   用户举报记录ID
     * @param formData 用户举报记录表单对象
     * @return
     */
    boolean updateAppComplaint(Long id, AppComplaintForm formData);

    /**
     * 删除用户举报记录
     *
     * @param ids 用户举报记录ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppComplaints(String ids);

}
