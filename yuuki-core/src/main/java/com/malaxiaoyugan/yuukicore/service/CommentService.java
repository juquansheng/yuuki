package com.malaxiaoyugan.yuukicore.service;

import com.malaxiaoyugan.yuukicore.entity.Comment;
import com.malaxiaoyugan.yuukicore.vo.CommentVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CommentService {

    /**
     * 添加评论
     * @param comment
     * @return
     */
    CommentVo insert(Comment comment);

    /**
     * 删除评论
     * @param id
     */
    boolean delete(Long id);

    /**
     * 获取评论列表
     * @param id
     * @param page
     * @param rows
     * @return
     */
    PageBean list(Long id, int page, int rows) throws UnsupportedEncodingException;
}
