package com.youlai.boot.app.service.impl;

import com.youlai.boot.app.model.vo.AddCommentVO;
import com.youlai.boot.app.model.vo.CommentVO;
import com.youlai.boot.app.model.vo.FriendSimpleVO;
import com.youlai.boot.app.service.AppFriendService;
import com.youlai.boot.core.security.model.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.model.mapper.AppPostCommentMapper;
import com.youlai.boot.app.service.AppPostCommentService;
import com.youlai.boot.app.model.entity.AppPostComment;
import com.youlai.boot.app.model.form.AppPostCommentForm;
import com.youlai.boot.app.model.query.AppPostCommentQuery;
import com.youlai.boot.app.model.vo.AppPostCommentVO;
import com.youlai.boot.app.converter.AppPostCommentConverter;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * 图文评论服务实现类
 *
 * @author yuyu
 * @since 2025-03-01 17:24
 */
@Service
@RequiredArgsConstructor
public class AppPostCommentServiceImpl extends ServiceImpl<AppPostCommentMapper, AppPostComment> implements AppPostCommentService {

    private final AppPostCommentConverter appPostCommentConverter;
    private final AppFriendService appFriendService;
    /**
    * 获取图文评论分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppPostCommentVO>} 图文评论分页列表
    */
    @Override
    public IPage<AppPostCommentVO> getAppPostCommentPage(AppPostCommentQuery queryParams) {
        Page<AppPostCommentVO> pageVO = this.baseMapper.getAppPostCommentPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }

    /**
     * 根据图文Id获取对应的评论列表
     * @param queryParams
     * @return
     */
    @Override
    public IPage<CommentVO> getCommentPageById(AppPostCommentQuery queryParams) {
        Page<CommentVO> pageVO = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<CommentVO> allComments = this.baseMapper.getCommentPageById(queryParams.getPostId());
        // 从 JWT 令牌中获取 studentId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = ((AppUserDetails)authentication.getPrincipal()).getStudentId();
        Long studentId = Long.parseLong(id);
        for(CommentVO comment : allComments) {
            FriendSimpleVO appFriendInfo = appFriendService.getAppFriendInfo(studentId, comment.getUserId());
            if(appFriendInfo != null) {
                comment.setUrl(appFriendInfo.getAvatar());
                comment.setUser(appFriendInfo.getNickname());
                comment.setUserInfo(appFriendInfo);
            }
        }
        // 分离一级评论和二级评论
        Map<Long, CommentVO> commentMap = new HashMap<>();
        List<CommentVO> firstLevelComments = new ArrayList<>();

        for (CommentVO comment : allComments) {
            if (comment.getReplyId() == null) {
                firstLevelComments.add(comment);
            }
            commentMap.put(comment.getId(), comment);
        }

        // 组装二级评论
        for (CommentVO comment : allComments) {
            if (comment.getReplyId() != null) {
                CommentVO parentComment = commentMap.get(comment.getReplyId());
                if (parentComment != null && parentComment.getReplyList() != null) {
                    parentComment.getReplyList().add(comment);
                } else if (parentComment != null) {
                    List<CommentVO> replyList = new ArrayList<>();
                    replyList.add(comment);
                    parentComment.setReplyList(replyList);
                }
            }
        }

        pageVO.setRecords(firstLevelComments);
        pageVO.setTotal(allComments.size());

        return pageVO;
    }
    /**
     * 获取图文评论表单数据
     *
     * @param id 图文评论ID
     * @return
     */
    @Override
    public AppPostCommentForm getAppPostCommentFormData(Long id) {
        AppPostComment entity = this.getById(id);
        return appPostCommentConverter.toForm(entity);
    }
    
    /**
     * 新增图文评论
     *
     * @param formData 图文评论表单对象
     * @return
     */
    @Transactional
    @Override
    public AddCommentVO saveAppPostComment(AppPostCommentForm formData) {
        AddCommentVO addCommentVO = new AddCommentVO();
        AppPostComment entity = appPostCommentConverter.toEntity(formData);
        boolean isSaved = this.save(entity);
        // 从 JWT 令牌中获取 studentId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().stream().forEach(authority -> {
            if (authority.getAuthority().equals("ROLE_STUDENT")) {
                // 如果是学生
                String id = ((AppUserDetails)authentication.getPrincipal()).getStudentId();
                Long studentId = Long.parseLong(id);
                FriendSimpleVO appFriendInfo = appFriendService.getAppFriendInfo(studentId, studentId);
                if(appFriendInfo != null) {
                    addCommentVO.setUserInfo(appFriendInfo);
                }
            }
        });

        if(isSaved) {
            addCommentVO.setId(Math.toIntExact(entity.getId()));
        }
        return addCommentVO;
    }
    
    /**
     * 更新图文评论
     *
     * @param id   图文评论ID
     * @param formData 图文评论表单对象
     * @return
     */
    @Override
    public boolean updateAppPostComment(Long id,AppPostCommentForm formData) {
        AppPostComment entity = appPostCommentConverter.toEntity(formData);
        entity.setId(id);
        return this.updateById(entity);
    }
    
    /**
     * 删除图文评论
     *
     * @param ids 图文评论ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppPostComments(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的图文评论数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }



}
