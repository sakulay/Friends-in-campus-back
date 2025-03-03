package com.youlai.boot.app.controller;

import com.youlai.boot.app.service.AppPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppPostForm;
import com.youlai.boot.app.model.query.AppPostQuery;
import com.youlai.boot.app.model.vo.AppPostVO;
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
 * 图文内容前端控制层
 *
 * @author yuyu
 * @since 2025-03-01 17:11
 */
@Tag(name = "图文内容接口")
@RestController
@RequestMapping("/api/v1/appPosts")
@RequiredArgsConstructor
public class AppPostController  {

    private final AppPostService appPostService;

    @Operation(summary = "图文内容分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appPost:query')")
    public PageResult<AppPostVO> getAppPostPage(AppPostQuery queryParams ) {
        IPage<AppPostVO> result = appPostService.getAppPostPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增图文内容")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appPost:add')")
    public Result<Void> saveAppPost(@RequestBody @Valid AppPostForm formData ) {
        boolean result = appPostService.saveAppPost(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取图文内容表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appPost:edit')")
    public Result<AppPostForm> getAppPostForm(
        @Parameter(description = "图文内容ID") @PathVariable Long id
    ) {
        AppPostForm formData = appPostService.getAppPostFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改图文内容")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appPost:edit')")
    public Result<Void> updateAppPost(
            @Parameter(description = "图文内容ID") @PathVariable Long id,
            @RequestBody @Validated AppPostForm formData
    ) {
        boolean result = appPostService.updateAppPost(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "验证并更新图文内容状态")
    @PutMapping("/verify/{id}")
    @PreAuthorize("@ss.hasPerm('app:appPost:verify')")
    public Result<Void> verifyAppPost(
        @Parameter(description = "图文内容ID") @PathVariable Long id,
        @Parameter(description = "状态") @RequestBody Integer status
    ) {
        boolean result = appPostService.verifyAppPost(id, status);
        return Result.judge(result);
    }

    @Operation(summary = "删除图文内容")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appPost:delete')")
    public Result<Void> deleteAppPosts(
        @Parameter(description = "图文内容ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appPostService.deleteAppPosts(ids);
        return Result.judge(result);
    }
}
