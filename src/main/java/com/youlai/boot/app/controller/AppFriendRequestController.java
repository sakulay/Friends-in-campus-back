package com.youlai.boot.app.controller;

import com.youlai.boot.app.service.AppFriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppFriendRequestForm;
import com.youlai.boot.app.model.query.AppFriendRequestQuery;
import com.youlai.boot.app.model.vo.AppFriendRequestVO;
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
 * 好友申请前端控制层
 *
 * @author yuyu
 * @since 2025-02-28 18:38
 */
@Tag(name = "好友申请接口")
@RestController
@RequestMapping("/api/v1/appFriendRequests")
@RequiredArgsConstructor
public class AppFriendRequestController  {

    private final AppFriendRequestService appFriendRequestService;

    @Operation(summary = "好友申请分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appFriendRequest:query')")
    public PageResult<AppFriendRequestVO> getAppFriendRequestPage(AppFriendRequestQuery queryParams ) {
        IPage<AppFriendRequestVO> result = appFriendRequestService.getAppFriendRequestPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增好友申请")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appFriendRequest:add')")
    public Result<Void> saveAppFriendRequest(@RequestBody @Valid AppFriendRequestForm formData ) {
        boolean result = appFriendRequestService.saveAppFriendRequest(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取好友申请表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appFriendRequest:edit')")
    public Result<AppFriendRequestForm> getAppFriendRequestForm(
        @Parameter(description = "好友申请ID") @PathVariable Long id
    ) {
        AppFriendRequestForm formData = appFriendRequestService.getAppFriendRequestFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改好友申请")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appFriendRequest:edit')")
    public Result<Void> updateAppFriendRequest(
            @Parameter(description = "好友申请ID") @PathVariable Long id,
            @RequestBody @Validated AppFriendRequestForm formData
    ) {
        boolean result = appFriendRequestService.updateAppFriendRequest(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除好友申请")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appFriendRequest:delete')")
    public Result<Void> deleteAppFriendRequests(
        @Parameter(description = "好友申请ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appFriendRequestService.deleteAppFriendRequests(ids);
        return Result.judge(result);
    }

    @Operation(summary = "通过好友请求")
    @PutMapping(value = "/passRequest/{id}")
    public Result<Void> passRequest(
            @Parameter(description = "学生用户ID") @PathVariable Long id
    ) {
        boolean result = appFriendRequestService.passRequest(id);
        return Result.judge(result);
    }
}
