package com.youlai.boot.app.controller;

import com.youlai.boot.app.model.vo.AppPostVO;
import com.youlai.boot.app.service.AppPostBrowseRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppPostBrowseRecordForm;
import com.youlai.boot.app.model.query.AppPostBrowseRecordQuery;
import com.youlai.boot.app.model.vo.AppPostBrowseRecordVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.boot.common.result.PageResult;
import com.youlai.boot.common.result.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * 帖子浏览记录前端控制层
 *
 * @author yuyu
 * @since 2025-05-03 12:51
 */
@Tag(name = "帖子浏览记录接口")
@RestController
@RequestMapping("/api/v1/appPostBrowseRecords")
@RequiredArgsConstructor
public class AppPostBrowseRecordController  {

    private final AppPostBrowseRecordService appPostBrowseRecordService;

    @Operation(summary = "帖子浏览记录分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appPostBrowseRecord:query')")
    public PageResult<AppPostVO> getAppPostBrowseRecordPage(AppPostBrowseRecordQuery queryParams ) {
        IPage<AppPostVO> result = appPostBrowseRecordService.getAppPostBrowseRecordPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增帖子浏览记录")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appPostBrowseRecord:add')")
    public Result<Void> saveAppPostBrowseRecord(@RequestBody AppPostBrowseRecordForm formData ) {
        boolean result = appPostBrowseRecordService.saveAppPostBrowseRecord(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取帖子浏览记录表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appPostBrowseRecord:edit')")
    public Result<AppPostBrowseRecordForm> getAppPostBrowseRecordForm(
        @Parameter(description = "帖子浏览记录ID") @PathVariable Long id
    ) {
        AppPostBrowseRecordForm formData = appPostBrowseRecordService.getAppPostBrowseRecordFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改帖子浏览记录")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appPostBrowseRecord:edit')")
    public Result<Void> updateAppPostBrowseRecord(
            @Parameter(description = "帖子浏览记录ID") @PathVariable Long id,
            @RequestBody @Validated AppPostBrowseRecordForm formData
    ) {
        boolean result = appPostBrowseRecordService.updateAppPostBrowseRecord(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除帖子浏览记录")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appPostBrowseRecord:delete')")
    public Result<Void> deleteAppPostBrowseRecords(
        @Parameter(description = "帖子浏览记录ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appPostBrowseRecordService.deleteAppPostBrowseRecords(ids);
        return Result.judge(result);
    }

    @Operation(summary = "根据浏览者ID和帖子ID删除浏览记录")
    @DeleteMapping("/viewer/{viewerId}/post/{postId}")
    @PreAuthorize("@ss.hasPerm('app:appPostBrowseRecord:delete')")
    public Result<Void> deleteAppPostBrowseRecord(
        @Parameter(description = "浏览者ID") @PathVariable Integer viewerId,
        @Parameter(description = "帖子ID") @PathVariable Long postId
    ) {
        boolean result = appPostBrowseRecordService.deleteAppPostBrowseRecord(viewerId, postId);
        return Result.judge(result);
    }
}
