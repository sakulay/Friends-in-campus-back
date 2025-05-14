package com.youlai.boot.app.controller;

import com.youlai.boot.app.service.AppPostCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppPostCategoryForm;
import com.youlai.boot.app.model.query.AppPostCategoryQuery;
import com.youlai.boot.app.model.vo.AppPostCategoryVO;
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
 * 帖子分类前端控制层
 *
 * @author yuyu
 * @since 2025-05-14 13:00
 */
@Tag(name = "帖子分类接口")
@RestController
@RequestMapping("/api/v1/appPostCategorys")
@RequiredArgsConstructor
public class AppPostCategoryController  {

    private final AppPostCategoryService appPostCategoryService;

    @Operation(summary = "帖子分类分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appPostCategory:query')")
    public PageResult<AppPostCategoryVO> getAppPostCategoryPage(AppPostCategoryQuery queryParams ) {
        IPage<AppPostCategoryVO> result = appPostCategoryService.getAppPostCategoryPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增帖子分类")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appPostCategory:add')")
    public Result<Void> saveAppPostCategory(@RequestBody @Valid AppPostCategoryForm formData ) {
        boolean result = appPostCategoryService.saveAppPostCategory(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取帖子分类表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appPostCategory:edit')")
    public Result<AppPostCategoryForm> getAppPostCategoryForm(
        @Parameter(description = "帖子分类ID") @PathVariable Long id
    ) {
        AppPostCategoryForm formData = appPostCategoryService.getAppPostCategoryFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改帖子分类")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appPostCategory:edit')")
    public Result<Void> updateAppPostCategory(
            @Parameter(description = "帖子分类ID") @PathVariable Long id,
            @RequestBody @Validated AppPostCategoryForm formData
    ) {
        boolean result = appPostCategoryService.updateAppPostCategory(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除帖子分类")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appPostCategory:delete')")
    public Result<Void> deleteAppPostCategorys(
        @Parameter(description = "帖子分类ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appPostCategoryService.deleteAppPostCategorys(ids);
        return Result.judge(result);
    }
}
