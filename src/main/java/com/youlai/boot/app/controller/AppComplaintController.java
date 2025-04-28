package com.youlai.boot.app.controller;

import com.youlai.boot.app.service.AppComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppComplaintForm;
import com.youlai.boot.app.model.query.AppComplaintQuery;
import com.youlai.boot.app.model.vo.AppComplaintVO;
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
 * 用户举报记录前端控制层
 *
 * @author yuyu
 * @since 2025-04-22 20:49
 */
@Tag(name = "用户举报记录接口")
@RestController
@RequestMapping("/api/v1/appComplaints")
@RequiredArgsConstructor
public class AppComplaintController  {

    private final AppComplaintService appComplaintService;

    @Operation(summary = "用户举报记录分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appComplaint:query')")
    public PageResult<AppComplaintVO> getAppComplaintPage(AppComplaintQuery queryParams ) {
        IPage<AppComplaintVO> result = appComplaintService.getAppComplaintPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增用户举报记录")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appComplaint:add')")
    public Result<Void> saveAppComplaint(@RequestBody @Valid AppComplaintForm formData ) {
        boolean result = appComplaintService.saveAppComplaint(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取用户举报记录表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appComplaint:edit')")
    public Result<AppComplaintForm> getAppComplaintForm(
        @Parameter(description = "用户举报记录ID") @PathVariable Long id
    ) {
        AppComplaintForm formData = appComplaintService.getAppComplaintFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改用户举报记录")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appComplaint:edit')")
    public Result<Void> updateAppComplaint(
            @Parameter(description = "用户举报记录ID") @PathVariable Long id,
            @RequestBody @Validated AppComplaintForm formData
    ) {
        boolean result = appComplaintService.updateAppComplaint(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除用户举报记录")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appComplaint:delete')")
    public Result<Void> deleteAppComplaints(
        @Parameter(description = "用户举报记录ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appComplaintService.deleteAppComplaints(ids);
        return Result.judge(result);
    }
}
