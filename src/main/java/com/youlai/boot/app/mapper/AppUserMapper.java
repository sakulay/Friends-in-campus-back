package com.youlai.boot.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.dto.AppUserAuthInfo;
import com.youlai.boot.app.model.entity.AppUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppUserQuery;
import com.youlai.boot.app.model.vo.AppUserVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 *
 * @author yuyu
 * @since 2025-02-20 22:03
 */
@Mapper
public interface AppUserMapper extends BaseMapper<AppUser> {

    /**
     * 获取用户分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppUserVO> getAppUserPage(Page<AppUserVO> page, AppUserQuery queryParams);

    /**
     * 根据学生号获取用户信息
     * @param studentId
     * @return
     */
    AppUserAuthInfo getUserAuthInfo(String studentId);


}
