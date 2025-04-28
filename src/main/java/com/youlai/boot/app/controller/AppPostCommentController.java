package com.youlai.boot.app.controller;

import com.youlai.boot.app.model.vo.AddCommentVO;
import com.youlai.boot.app.model.vo.CommentVO;
import com.youlai.boot.app.service.AppPostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppPostCommentForm;
import com.youlai.boot.app.model.query.AppPostCommentQuery;
import com.youlai.boot.app.model.vo.AppPostCommentVO;
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
 * 图文评论前端控制层
 *
 * @author yuyu
 * @since 2025-03-01 17:24
 */
@Tag(name = "图文评论接口")
@RestController
@RequestMapping("/api/v1/appPostComments")
@RequiredArgsConstructor
public class AppPostCommentController  {

    private final AppPostCommentService appPostCommentService;

    @Operation(summary = "图文评论分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appPostComment:query')")
    public PageResult<AppPostCommentVO> getAppPostCommentPage(AppPostCommentQuery queryParams ) {
        IPage<AppPostCommentVO> result = appPostCommentService.getAppPostCommentPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "根据图文Id获取对应的评论列表")
    @GetMapping("/pageById")
    @PreAuthorize("@ss.hasPerm('app:appPostComment:query')")
    public PageResult<CommentVO> getCommentPageById(AppPostCommentQuery queryParams ) {
        IPage<CommentVO> result = appPostCommentService.getCommentPageById(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增图文评论")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appPostComment:add')")
    public Result<AddCommentVO> saveAppPostComment(@RequestBody @Valid AppPostCommentForm formData ) {
        AddCommentVO result = appPostCommentService.saveAppPostComment(formData);
        return Result.success(result);
    }

    @Operation(summary = "获取图文评论表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appPostComment:edit')")
    public Result<AppPostCommentForm> getAppPostCommentForm(
        @Parameter(description = "图文评论ID") @PathVariable Long id
    ) {
        AppPostCommentForm formData = appPostCommentService.getAppPostCommentFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改图文评论")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appPostComment:edit')")
    public Result<Void> updateAppPostComment(
            @Parameter(description = "图文评论ID") @PathVariable Long id,
            @RequestBody AppPostCommentForm formData
    ) {
        boolean result = appPostCommentService.updateAppPostComment(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除图文评论")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appPostComment:delete')")
    public Result<Void> deleteAppPostComments(
        @Parameter(description = "图文评论ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appPostCommentService.deleteAppPostComments(ids);
        return Result.judge(result);
    }


}
