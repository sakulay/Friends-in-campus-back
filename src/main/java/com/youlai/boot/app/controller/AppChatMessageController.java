package com.youlai.boot.app.controller;

import com.youlai.boot.app.model.vo.MessageVO;
import com.youlai.boot.app.model.vo.UnreadVO;
import com.youlai.boot.app.service.AppChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.boot.app.model.form.AppChatMessageForm;
import com.youlai.boot.app.model.query.AppChatMessageQuery;
import com.youlai.boot.app.model.vo.AppChatMessageVO;
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

import java.util.List;

/**
 * 用户聊天记录前端控制层
 *
 * @author yuyu
 * @since 2025-02-28 18:44
 */
@Tag(name = "用户聊天记录接口")
@RestController
@RequestMapping("/api/v1/appChatMessages")
@RequiredArgsConstructor
public class AppChatMessageController  {

    private final AppChatMessageService appChatMessageService;

    @Operation(summary = "用户聊天记录分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('app:appChatMessage:query')")
    public PageResult<AppChatMessageVO> getAppChatMessagePage(AppChatMessageQuery queryParams ) {
        IPage<AppChatMessageVO> result = appChatMessageService.getAppChatMessagePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增用户聊天记录")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('app:appChatMessage:add')")
    public Result<Void> saveAppChatMessage(@RequestBody @Valid AppChatMessageForm formData ) {
        boolean result = appChatMessageService.saveAppChatMessage(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取用户聊天记录表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('app:appChatMessage:edit')")
    public Result<AppChatMessageForm> getAppChatMessageForm(
        @Parameter(description = "用户聊天记录ID") @PathVariable Long id
    ) {
        AppChatMessageForm formData = appChatMessageService.getAppChatMessageFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改用户聊天记录")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('app:appChatMessage:edit')")
    public Result<Void> updateAppChatMessage(
            @Parameter(description = "用户聊天记录ID") @PathVariable Long id,
            @RequestBody @Validated AppChatMessageForm formData
    ) {
        boolean result = appChatMessageService.updateAppChatMessage(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除用户聊天记录")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('app:appChatMessage:delete')")
    public Result<Void> deleteAppChatMessages(
        @Parameter(description = "用户聊天记录ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = appChatMessageService.deleteAppChatMessages(ids);
        return Result.judge(result);
    }

    @Operation(summary = "获取用户未读消息列表")
    @GetMapping("/{id}/unread")
    @PreAuthorize("@ss.hasPerm('app:appChatMessage:query')")
    public Result<List<UnreadVO>> getAppChatUnread(
            @Parameter(description = "用户聊天记录ID") @PathVariable Long id
    ) {
        List<UnreadVO> unreads = appChatMessageService.getAppChatUnread(id);
        return Result.success(unreads);
    }

    @Operation(summary = "获取聊天记录")
    @GetMapping("/{id}/{other}/messages")
    @PreAuthorize("@ss.hasPerm('app:appChatMessage:query')")
    public Result<List<MessageVO>> getAppChatmessages(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Parameter(description = "对方ID") @PathVariable Long other
    ) {
        List<MessageVO> messages = appChatMessageService.getAppChatMessages(id, other);
        return Result.success(messages);
    }

    @Operation(summary = "清空未读消息")
    @GetMapping("/{sender}/{receiver}/readed")
    @PreAuthorize("@ss.hasPerm('app:appChatMessage:query')")
    public Result<Void> handelReaded(
            @Parameter(description = "用户ID") @PathVariable Long sender,
            @Parameter(description = "对方ID") @PathVariable Long receiver
    ) {
        return Result.judge(appChatMessageService.readed(sender, receiver));
    }
}
