package com.youlai.boot.app.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppUserProfile;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppUserProfileQuery;
import com.youlai.boot.app.model.vo.AppUserProfileVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户个人信息Mapper接口
 *
 * @author yuyu
 * @since 2025-02-23 19:43
 */
@Mapper
public interface AppUserProfileMapper extends BaseMapper<AppUserProfile> {

    /**
     * 获取用户个人信息分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppUserProfileVO> getAppUserProfilePage(Page<AppUserProfileVO> page, AppUserProfileQuery queryParams);

    AppUserProfile getByStudentId(Long id);

}
