package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 图文内容视图对象
 *
 * @author yuyu
 * @since 2025-03-01 17:11
 */
@Getter
@Setter
@Schema( description = "图文内容视图对象")
public class AppPostVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "主键，自增")
    private Long id;
    @Schema(description = "发布图文的用户ID，关联到 app_user 表")
    private Long userId;
    @Schema(description = "图文标题")
    private String title;
    @Schema(description = "图文内容，支持富文本")
    private String content;
    @Schema(description = "点赞数")
    private Integer likeCount;
    @Schema(description = "评论数")
    private Integer commentCount;
    @Schema(description = "发布图文时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "文章状态（0-待审核，1-审核通过，2-禁用）")
    private Integer status;
    @Schema(description = "图片列表")
    private List<AppPostImageVO> imageList;
}
