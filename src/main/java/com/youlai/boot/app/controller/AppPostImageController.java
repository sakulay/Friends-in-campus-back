package com.youlai.boot.app.controller;

import com.youlai.boot.app.service.AppPostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppPostImageForm;
import com.youlai.boot.app.model.query.AppPostImageQuery;
import com.youlai.boot.app.model.vo.AppPostImageVO;
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
 * 图文图片前端控制层
 *
 * @author yuyu
 * @since 2025-03-01 19:27
 */
@Tag(name = "图文图片接口")
@RestController
@RequestMapping("/api/v1/appPostImages")
@RequiredArgsConstructor
public class AppPostImageController  {

    private final AppPostImageService appPostImageService;

    @Operation(summary = "图文图片分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appPostImage:query')")
    public PageResult<AppPostImageVO> getAppPostImagePage(AppPostImageQuery queryParams ) {
        IPage<AppPostImageVO> result = appPostImageService.getAppPostImagePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增图文图片")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appPostImage:add')")
    public Result<Void> saveAppPostImage(@RequestBody @Valid AppPostImageForm formData ) {
        boolean result = appPostImageService.saveAppPostImage(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取图文图片表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appPostImage:edit')")
    public Result<AppPostImageForm> getAppPostImageForm(
        @Parameter(description = "图文图片ID") @PathVariable Long id
    ) {
        AppPostImageForm formData = appPostImageService.getAppPostImageFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改图文图片")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appPostImage:edit')")
    public Result<Void> updateAppPostImage(
            @Parameter(description = "图文图片ID") @PathVariable Long id,
            @RequestBody @Validated AppPostImageForm formData
    ) {
        boolean result = appPostImageService.updateAppPostImage(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除图文图片")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appPostImage:delete')")
    public Result<Void> deleteAppPostImages(
        @Parameter(description = "图文图片ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appPostImageService.deleteAppPostImages(ids);
        return Result.judge(result);
    }
}
