package com.youlai.boot.app.controller;

import com.youlai.boot.app.model.vo.AppPostVO;
import com.youlai.boot.app.service.AppPostFavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppPostFavoritesForm;
import com.youlai.boot.app.model.query.AppPostFavoritesQuery;
import com.youlai.boot.app.model.vo.AppPostFavoritesVO;
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
 * 帖子收藏记录前端控制层
 *
 * @author yuyu
 * @since 2025-05-04 12:22
 */
@Tag(name = "帖子收藏记录接口")
@RestController
@RequestMapping("/api/v1/appPostFavoritess")
@RequiredArgsConstructor
public class AppPostFavoritesController  {

    private final AppPostFavoritesService appPostFavoritesService;

    @Operation(summary = "帖子收藏记录分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appPostFavorites:query')")
    public PageResult<AppPostVO> getAppPostFavoritesPage(AppPostFavoritesQuery queryParams ) {
        IPage<AppPostVO> result = appPostFavoritesService.getAppPostFavoritesPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增帖子收藏记录")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appPostFavorites:add')")
    public Result<Void> saveAppPostFavorites(@RequestBody AppPostFavoritesForm formData ) {
        boolean result = appPostFavoritesService.saveAppPostFavorites(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取帖子收藏记录表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appPostFavorites:edit')")
    public Result<AppPostFavoritesForm> getAppPostFavoritesForm(
        @Parameter(description = "帖子收藏记录ID") @PathVariable Long id
    ) {
        AppPostFavoritesForm formData = appPostFavoritesService.getAppPostFavoritesFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改帖子收藏记录")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appPostFavorites:edit')")
    public Result<Void> updateAppPostFavorites(
            @Parameter(description = "帖子收藏记录ID") @PathVariable Long id,
            @RequestBody @Validated AppPostFavoritesForm formData
    ) {
        boolean result = appPostFavoritesService.updateAppPostFavorites(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "取消收藏")
    @DeleteMapping
    @PreAuthorize("@ss.hasPerm('app:appPostFavorites:delete')")
    public Result<Void> cancelFavorite(
        @Parameter(description = "用户ID") @RequestParam Long userId,
        @Parameter(description = "帖子ID") @RequestParam Long postId
    ) {
        boolean result = appPostFavoritesService.cancelFavorite(userId, postId);
        return Result.judge(result);
    }

    @Operation(summary = "删除帖子收藏记录")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appPostFavorites:delete')")
    public Result<Void> deleteAppPostFavoritess(
        @Parameter(description = "帖子收藏记录ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appPostFavoritesService.deleteAppPostFavoritess(ids);
        return Result.judge(result);
    }

    @Operation(summary = "查询收藏记录")
    @GetMapping("/record/{userId}/{postId}")
    @PreAuthorize("@ss.hasPerm('app:appPostFavorites:query')")
    public Result<AppPostFavoritesVO> getFavoriteRecord(
        @Parameter(description = "用户ID") @PathVariable  Long userId,
        @Parameter(description = "帖子ID") @PathVariable  Long postId
    ) {
        AppPostFavoritesVO result = appPostFavoritesService.getFavoriteRecord(userId, postId);
        return Result.success(result);
    }
}
