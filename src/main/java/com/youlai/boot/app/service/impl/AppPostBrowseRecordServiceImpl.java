package com.youlai.boot.app.service.impl;

import com.youlai.boot.app.model.mapper.AppPostBrowseRecordMapper;
import com.youlai.boot.app.model.vo.AppPostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.service.AppPostBrowseRecordService;
import com.youlai.boot.app.model.entity.AppPostBrowseRecord;
import com.youlai.boot.app.model.form.AppPostBrowseRecordForm;
import com.youlai.boot.app.model.query.AppPostBrowseRecordQuery;
import com.youlai.boot.app.model.vo.AppPostBrowseRecordVO;
import com.youlai.boot.app.converter.AppPostBrowseRecordConverter;
import com.youlai.boot.app.service.AppPostService;
import com.youlai.boot.app.model.query.AppPostQuery;
import com.youlai.boot.app.model.mapper.AppPostMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.time.LocalDateTime;

/**
 * 帖子浏览记录服务实现类
 *
 * @author yuyu
 * @since 2025-05-03 12:51
 */
@Service
@RequiredArgsConstructor
public class AppPostBrowseRecordServiceImpl extends ServiceImpl<AppPostBrowseRecordMapper, AppPostBrowseRecord> implements AppPostBrowseRecordService {

    private final AppPostBrowseRecordConverter appPostBrowseRecordConverter;
    private final AppPostService appPostService;

    /**
    * 获取帖子浏览记录分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppPostVO>} 帖子浏览记录分页列表
    */
    @Override
    public IPage<AppPostVO> getAppPostBrowseRecordPage(AppPostBrowseRecordQuery queryParams) {
        // 获取浏览记录分页数据
        Page<AppPostBrowseRecordVO> browseRecordPage = this.baseMapper.getAppPostBrowseRecordPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 如果没有浏览记录，直接返回空分页
        if (browseRecordPage.getRecords().isEmpty()) {
            return new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        }

        // 获取所有浏览的帖子ID
        List<Long> postIds = browseRecordPage.getRecords().stream()
                .map(AppPostBrowseRecordVO::getPostId)
                .collect(Collectors.toList());

        // 构建帖子查询参数
        AppPostQuery postQuery = new AppPostQuery();
        postQuery.setPageNum(1);
        postQuery.setPageSize(postIds.size());
        postQuery.setStatus(1); // 只查询已审核通过的帖子
        postQuery.setPostIds(postIds); // 设置要查询的帖子ID列表
        postQuery.setOrderByPostIds(true); // 按照postIds顺序排序

        // 获取帖子信息
        IPage<AppPostVO> postPage = appPostService.getAppPostPage(postQuery);

        // 返回帖子分页数据
        return postPage;
    }
    
    /**
     * 获取帖子浏览记录表单数据
     *
     * @param id 帖子浏览记录ID
     * @return
     */
    @Override
    public AppPostBrowseRecordForm getAppPostBrowseRecordFormData(Long id) {
        AppPostBrowseRecord entity = this.getById(id);
        return appPostBrowseRecordConverter.toForm(entity);
    }
    
    /**
     * 新增帖子浏览记录
     *
     * @param formData 帖子浏览记录表单对象
     * @return
     */
    @Override
    public boolean saveAppPostBrowseRecord(AppPostBrowseRecordForm formData) {
        // 检查是否已存在浏览记录
        LambdaQueryWrapper<AppPostBrowseRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppPostBrowseRecord::getViewerId, formData.getViewerId())
                .eq(AppPostBrowseRecord::getPostId, formData.getPostId())
                .eq(AppPostBrowseRecord::getIsDeleted, 0);
        
        AppPostBrowseRecord existingRecord = this.getOne(queryWrapper);
        
        if (existingRecord != null) {
            // 如果记录已存在，更新浏览时间
            existingRecord.setBrowseTime(LocalDateTime.now());
            return this.updateById(existingRecord);
        } else {
            // 如果记录不存在，创建新记录
            AppPostBrowseRecord entity = appPostBrowseRecordConverter.toEntity(formData);
            entity.setBrowseTime(LocalDateTime.now());
            entity.setIsDeleted(0);
            return this.save(entity);
        }
    }
    
    /**
     * 更新帖子浏览记录
     *
     * @param id   帖子浏览记录ID
     * @param formData 帖子浏览记录表单对象
     * @return
     */
    @Override
    public boolean updateAppPostBrowseRecord(Long id,AppPostBrowseRecordForm formData) {
        AppPostBrowseRecord entity = appPostBrowseRecordConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除帖子浏览记录
     *
     * @param ids 帖子浏览记录ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppPostBrowseRecords(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的帖子浏览记录数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    @Override
    public boolean deleteAppPostBrowseRecord(Integer viewerId, Long postId) {
        Assert.notNull(viewerId, "浏览者ID不能为空");
        Assert.notNull(postId, "帖子ID不能为空");
        
        LambdaQueryWrapper<AppPostBrowseRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppPostBrowseRecord::getViewerId, viewerId)
                .eq(AppPostBrowseRecord::getPostId, postId)
                .eq(AppPostBrowseRecord::getIsDeleted, 0);

        // 构建更新对象，将 is_deleted 设置为 1
        AppPostBrowseRecord updateRecord = new AppPostBrowseRecord();
        updateRecord.setIsDeleted(1);

        // 调用 update 方法进行逻辑删除
        return this.update(updateRecord, queryWrapper);
    }

}
