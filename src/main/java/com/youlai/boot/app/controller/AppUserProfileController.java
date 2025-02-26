package com.youlai.boot.app.controller;

import com.youlai.boot.app.service.AppUserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppUserProfileForm;
import com.youlai.boot.app.model.query.AppUserProfileQuery;
import com.youlai.boot.app.model.vo.AppUserProfileVO;
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
 * 用户个人信息前端控制层
 *
 * @author yuyu
 * @since 2025-02-23 19:43
 */
@Tag(name = "用户个人信息接口")
@RestController
@RequestMapping("/api/v1/appUserProfiles")
@RequiredArgsConstructor
public class AppUserProfileController  {

    private final AppUserProfileService appUserProfileService;

    @Operation(summary = "用户个人信息分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appUserProfile:query')")
    public PageResult<AppUserProfileVO> getAppUserProfilePage(AppUserProfileQuery queryParams ) {
        IPage<AppUserProfileVO> result = appUserProfileService.getAppUserProfilePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增用户个人信息")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appUserProfile:add')")
    public Result<Void> saveAppUserProfile(@RequestBody @Valid AppUserProfileForm formData ) {
        boolean result = appUserProfileService.saveAppUserProfile(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取用户个人信息表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appUserProfile:edit')")
    public Result<AppUserProfileForm> getAppUserProfileForm(
        @Parameter(description = "用户个人信息ID") @PathVariable Long id
    ) {
        AppUserProfileForm formData = appUserProfileService.getAppUserProfileFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改用户个人信息")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appUserProfile:edit')")
    public Result<Void> updateAppUserProfile(
            @Parameter(description = "用户个人信息ID") @PathVariable Long id,
            @RequestBody @Validated AppUserProfileForm formData
    ) {
        boolean result = appUserProfileService.updateAppUserProfile(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除用户个人信息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appUserProfile:delete')")
    public Result<Void> deleteAppUserProfiles(
        @Parameter(description = "用户个人信息ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appUserProfileService.deleteAppUserProfiles(ids);
        return Result.judge(result);
    }
}
