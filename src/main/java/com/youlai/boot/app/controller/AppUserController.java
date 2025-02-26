package com.youlai.boot.app.controller;

import com.youlai.boot.app.model.dto.AppUserAuthInfo;
import com.youlai.boot.app.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppUserForm;
import com.youlai.boot.app.model.query.AppUserQuery;
import com.youlai.boot.app.model.vo.AppUserVO;
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
 * 用户前端控制层
 *
 * @author yuyu
 * @since 2025-02-20 22:03
 */
@Tag(name = "100.用户接口")
@RestController
@RequestMapping("/api/v1/appUsers")
@RequiredArgsConstructor
@Slf4j
public class AppUserController  {

    private final AppUserService appUserService;

    @Operation(summary = "用户分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appUser:query')")
    public PageResult<AppUserVO> getAppUserPage(AppUserQuery queryParams ) {
        IPage<AppUserVO> result = appUserService.getAppUserPage(queryParams);
        for(AppUserVO o: result.getRecords()) {
            log.info("UserPage: " + o.toString());
        }
        return PageResult.success(result);
    }

    @Operation(summary = "新增用户")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appUser:add')")
    public Result<Void> saveAppUser(@RequestBody @Valid AppUserForm formData ) {
        boolean result = appUserService.saveAppUser(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取用户表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appUser:edit')")
    public Result<AppUserForm> getAppUserForm(
        @Parameter(description = "用户ID") @PathVariable Long id
    ) {
        AppUserForm formData = appUserService.getAppUserFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改用户")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appUser:edit')")
    public Result<Void> updateAppUser(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @RequestBody @Validated AppUserForm formData
    ) {
        boolean result = appUserService.updateAppUser(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appUser:delete')")
    public Result<Void> deleteAppUsers(
        @Parameter(description = "用户ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appUserService.deleteAppUsers(ids);
        return Result.judge(result);
    }

    @Operation(summary = "通过名字获取用户信息")
    @PostMapping(value = "/getAuth")
    public Result<AppUserAuthInfo> get(
            @RequestBody String username
    ) {
        log.info("username: " + username);
        AppUserAuthInfo appUserAuthInfo = appUserService.getUserAuthInfo(username);
        return Result.success(appUserAuthInfo);
    }
}
