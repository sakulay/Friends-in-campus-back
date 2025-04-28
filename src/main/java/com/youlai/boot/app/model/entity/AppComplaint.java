package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 用户举报记录实体对象
 *
 * @author yuyu
 * @since 2025-04-22 20:49
 */
@Getter
@Setter
@TableName("app_complaint")
public class AppComplaint extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 举报用户ID，关联到 app_user 表
     */
    private Long userId;
    /**
     * 举报类型（例如：恶意内容、色情暴力等）
     */
    private String complaintType;
    /**
     * 举报描述，说明举报原因
     */
    private String complaintDescription;
    /**
     * 举报目标类型，1-图文，2-评论
     */
    private Integer complaintTargetType;
    /**
     * 举报目标ID（对应图文ID或评论ID）
     */
    private Long complaintTargetId;
    /**
     * 举报状态，0-待处理，1-已受理，2-已处理，3-已驳回
     */
    private Integer status;
    /**
     * 是否删除(1-删除，0-未删除)
     */
    private Integer isDeleted;
}
