package com.youlai.boot.app.controller;

import com.youlai.boot.app.service.AppFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppFriendForm;
import com.youlai.boot.app.model.query.AppFriendQuery;
import com.youlai.boot.app.model.vo.AppFriendVO;
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
 * 用户好友关系前端控制层
 *
 * @author yuyu
 * @since 2025-02-28 18:25
 */
@Tag(name = "用户好友关系接口")
@RestController
@RequestMapping("/api/v1/appFriends")
@RequiredArgsConstructor
public class AppFriendController  {

    private final AppFriendService appFriendService;

    @Operation(summary = "用户好友关系分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appFriend:query')")
    public PageResult<AppFriendVO> getAppFriendPage(AppFriendQuery queryParams ) {
        IPage<AppFriendVO> result = appFriendService.getAppFriendPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增用户好友关系")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appFriend:add')")
    public Result<Void> saveAppFriend(@RequestBody @Valid AppFriendForm formData ) {
        boolean result = appFriendService.saveAppFriend(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取用户好友关系表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appFriend:edit')")
    public Result<AppFriendForm> getAppFriendForm(
        @Parameter(description = "用户好友关系ID") @PathVariable Long id
    ) {
        AppFriendForm formData = appFriendService.getAppFriendFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改用户好友关系")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appFriend:edit')")
    public Result<Void> updateAppFriend(
            @Parameter(description = "用户好友关系ID") @PathVariable Long id,
            @RequestBody @Validated AppFriendForm formData
    ) {
        boolean result = appFriendService.updateAppFriend(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除用户好友关系")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appFriend:delete')")
    public Result<Void> deleteAppFriends(
        @Parameter(description = "用户好友关系ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appFriendService.deleteAppFriends(ids);
        return Result.judge(result);
    }
}
