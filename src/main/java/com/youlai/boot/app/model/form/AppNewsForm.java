package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 校园新闻资讯表单对象
 *
 * @author yuyu
 * @since 2025-04-20 08:10
 */
@Getter
@Setter
@Schema(description = "校园新闻资讯表单对象")
public class AppNewsForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

//    @NotNull(message = "不能为空")
    private Long id;

    @Size(max=255, message="长度不能超过255个字符")
    private String title;

    @Size(max=255, message="长度不能超过255个字符")
    private String date;

    private String content;


}
