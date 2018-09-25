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
import com.malaxiaoyugan.yuukicore.mapper.UserMapper;
import com.malaxiaoyugan.yuukicore.service.CommentService;
import com.malaxiaoyugan.yuukicore.vo.CommentVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private UserMapper userMapper;

    @Override
    public CommentVo insert(Comment comment) {
        String time12="yyyy-MM-dd hh:mm:ss";
        String time24="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time24);
        Date date = new Date();
        comment.setCreateTime(date);
        comment.setUpdateTime(date);
        int insertSelective = commentMapper.insertSelective(comment);
        if (insertSelective > 0){
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            commentVo.setNickName(userMapper.selectByPrimaryKey(comment.getUserId()).getNickName());
            commentVo.setCreateTimeString(simpleDateFormat.format(comment.getCreateTime()));
            return commentVo;
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
        String time12="yyyy-MM-dd hh:mm:ss";
        String time24="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time24);
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andStatusNotEqualTo(-1).andArticleIdEqualTo(id);
        if (page != 0 && rows != 0) {
            PageHelper.startPage(page, rows);
        }
        commentExample.setOrderByClause("create_time desc");
        List<Comment> commentList = commentMapper.selectByExampleWithBLOBs(commentExample);

        List<CommentVo> commentVoList = Lists.newArrayList();
        for (Comment comment:commentList){
            CommentVo commentVo = new CommentVo();
            if (comment.getContent() == null){
                commentVo.setContentString("");
            }else {
                commentVo.setContentString(new String(comment.getContent(),"UTF-8"));
            }
            commentVo.setNickName(userMapper.selectByPrimaryKey(comment.getUserId()).getNickName());
            commentVo.setCreateTimeString(simpleDateFormat.format(comment.getCreateTime()));
            commentVoList.add(commentVo);
        }
        PageBean<CommentVo> pageBean = new PageBean<>();
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setPageNumber(pageInfo.getPageNum());
        pageBean.setPageSize(pageInfo.getSize());
        pageBean.setPageDatas(commentVoList);
        return pageBean;
    }
}
