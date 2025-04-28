package com.youlai.boot.app.controller;

import com.youlai.boot.app.service.AppNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppNewsForm;
import com.youlai.boot.app.model.query.AppNewsQuery;
import com.youlai.boot.app.model.vo.AppNewsVO;
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
 * 校园新闻资讯前端控制层
 *
 * @author yuyu
 * @since 2025-04-20 08:10
 */
@Tag(name = "校园新闻资讯接口")
@RestController
@RequestMapping("/api/v1/appNewss")
@RequiredArgsConstructor
public class AppNewsController  {

    private final AppNewsService appNewsService;

    @Operation(summary = "校园新闻资讯分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appNews:query')")
    public PageResult<AppNewsVO> getAppNewsPage(AppNewsQuery queryParams ) {
        IPage<AppNewsVO> result = appNewsService.getAppNewsPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增校园新闻资讯")
    @PostMapping("/save")
    public Result<Void> saveAppNews(@RequestBody @Valid AppNewsForm formData ) {
        boolean result = appNewsService.saveAppNews(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取校园新闻资讯表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appNews:edit')")
    public Result<AppNewsForm> getAppNewsForm(
        @Parameter(description = "校园新闻资讯ID") @PathVariable Long id
    ) {
        AppNewsForm formData = appNewsService.getAppNewsFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改校园新闻资讯")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appNews:edit')")
    public Result<Void> updateAppNews(
            @Parameter(description = "校园新闻资讯ID") @PathVariable Long id,
            @RequestBody @Validated AppNewsForm formData
    ) {
        boolean result = appNewsService.updateAppNews(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除校园新闻资讯")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appNews:delete')")
    public Result<Void> deleteAppNewss(
        @Parameter(description = "校园新闻资讯ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appNewsService.deleteAppNewss(ids);
        return Result.judge(result);
    }
}
