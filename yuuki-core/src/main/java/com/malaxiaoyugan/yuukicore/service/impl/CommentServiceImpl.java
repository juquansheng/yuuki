package com.malaxiaoyugan.yuukicore.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.malaxiaoyugan.yuukicore.entity.Comment;
import com.malaxiaoyugan.yuukicore.entity.CommentExample;
import com.malaxiaoyugan.yuukicore.entity.Reply;
import com.malaxiaoyugan.yuukicore.entity.ReplyExample;
import com.malaxiaoyugan.yuukicore.mapper.CommentMapper;
import com.malaxiaoyugan.yuukicore.mapper.ReplyMapper;
import com.malaxiaoyugan.yuukicore.service.CommentService;
import com.malaxiaoyugan.yuukicore.vo.CommentVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 *@describe 评论接口
 *@author  ttbf
 *@date  2018/9/14
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public Comment insert(Comment comment) {
        Date date = new Date();
        comment.setCreateTime(date);
        comment.setUpdateTime(date);
        int insertSelective = commentMapper.insertSelective(comment);
        if (insertSelective > 0){
            return comment;
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Date date = new Date();
        Comment comment = new Comment();
        comment.setStatus(-1);
        comment.setUpdateTime(date);
        int insertSelective = commentMapper.insertSelective(comment);
        //删除评论下的回复
        ReplyExample replyExample = new ReplyExample();
        replyExample.createCriteria().andCommentIdEqualTo(id);
        List<Reply> replyList = replyMapper.selectByExample(replyExample);
        for (Reply reply:replyList){
            Reply delReply = new Reply();
            delReply.setReplyId(reply.getId());
            delReply.setStatus(-1);
            delReply.setUpdateTime(date);
            replyMapper.updateByPrimaryKey(delReply);
        }
        if (insertSelective > 0){
            return true;
        }
        return false;
    }

    @Override
    public PageBean list(Long id, int page, int rows) throws UnsupportedEncodingException {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andStatusNotEqualTo(-1).andArticleIdEqualTo(id);
        if (page != 0 && rows != 0) {
            PageHelper.startPage(page, rows);
        }
        List<Comment> commentList = commentMapper.selectByExample(commentExample);

        List<CommentVo> commentVoList = Lists.newArrayList();
        for (Comment comment:commentList){
            CommentVo commentVo = new CommentVo();
            commentVo.setContentString(new String(comment.getContent(),"UTF-8"));
            commentVoList.add(commentVo);
        }
        PageBean<CommentVo> pageBean = new PageBean<>();
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setPageDatas(commentVoList);
        return pageBean;
    }
}
