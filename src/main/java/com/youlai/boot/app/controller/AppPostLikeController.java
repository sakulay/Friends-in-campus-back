package com.youlai.boot.app.controller;

import com.youlai.boot.app.service.AppPostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppPostLikeForm;
import com.youlai.boot.app.model.query.AppPostLikeQuery;
import com.youlai.boot.app.model.vo.AppPostLikeVO;
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
 * 用户点赞记录前端控制层
 *
 * @author yuyu
 * @since 2025-03-01 20:51
 */
@Tag(name = "用户点赞记录接口")
@RestController
@RequestMapping("/api/v1/appPostLikes")
@RequiredArgsConstructor
public class AppPostLikeController  {

    private final AppPostLikeService appPostLikeService;

    @Operation(summary = "用户点赞记录分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appPostLike:query')")
    public PageResult<AppPostLikeVO> getAppPostLikePage(AppPostLikeQuery queryParams ) {
        IPage<AppPostLikeVO> result = appPostLikeService.getAppPostLikePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增用户点赞记录")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appPostLike:add')")
    public Result<Void> saveAppPostLike(@RequestBody @Valid AppPostLikeForm formData ) {
        boolean result = appPostLikeService.saveAppPostLike(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取用户点赞记录表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appPostLike:edit')")
    public Result<AppPostLikeForm> getAppPostLikeForm(
        @Parameter(description = "用户点赞记录ID") @PathVariable Long id
    ) {
        AppPostLikeForm formData = appPostLikeService.getAppPostLikeFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改用户点赞记录")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appPostLike:edit')")
    public Result<Void> updateAppPostLike(
            @Parameter(description = "用户点赞记录ID") @PathVariable Long id,
            @RequestBody @Validated AppPostLikeForm formData
    ) {
        boolean result = appPostLikeService.updateAppPostLike(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除用户点赞记录")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appPostLike:delete')")
    public Result<Void> deleteAppPostLikes(
        @Parameter(description = "用户点赞记录ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appPostLikeService.deleteAppPostLikes(ids);
        return Result.judge(result);
    }
}
