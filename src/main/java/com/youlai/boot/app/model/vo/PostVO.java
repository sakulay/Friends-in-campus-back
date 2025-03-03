package com.youlai.boot.app.model.vo;

import com.youlai.boot.app.model.entity.AppPostImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 图文内容整合图片视图对象
 *
 * @author yuyu
 * @since 2025-03-01 17:11
 */
@Getter
@Setter
@Schema( description = "图文内容视图整合图片对象")
public class PostVO {
    // 内容部分
    private AppPostVO appPostVO;
    // 图片部分
    private AppPostImage appPostImage;
}
