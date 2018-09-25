package com.malaxiaoyugan.yuukicore.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.malaxiaoyugan.yuukicore.entity.*;
import com.malaxiaoyugan.yuukicore.mapper.ArticleMapper;
import com.malaxiaoyugan.yuukicore.mapper.CommentMapper;
import com.malaxiaoyugan.yuukicore.mapper.ReplyMapper;
import com.malaxiaoyugan.yuukicore.mapper.UserMapper;
import com.malaxiaoyugan.yuukicore.service.CommentService;
import com.malaxiaoyugan.yuukicore.service.MessageService;
import com.malaxiaoyugan.yuukicore.service.UserLogService;
import com.malaxiaoyugan.yuukicore.vo.CommentVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.resources.Messages;

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
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private MessageService messageService;

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
            //添加记录
            userLogService.insert(articleMapper.selectByPrimaryKey(comment.getArticleId()).getTitle(),1,
                    comment.getUserId(),comment.getArticleId(),simpleDateFormat.format(comment.getCreateTime()));

            //消息推送
            Message message = new Message();
            message.setUserTo(articleMapper.selectByPrimaryKey(comment.getArticleId()).getUserId());
            message.setUserFrom(comment.getUserId());
            message.setContent(userMapper.selectByPrimaryKey(comment.getUserId()).getNickName()+"评论了您的文章《"+articleMapper.selectByPrimaryKey(comment.getArticleId()).getTitle()+"》");
            message.setType(0);
            message.setCreatetime(date);
            message.setUpdatetime(date);
            messageService.insert(message);

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
        String time12="yyyy-MM-dd hh:mm:ss";
        String time24="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time24);
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
            //添加记录
            userLogService.insert(articleMapper.selectByPrimaryKey(comment.getArticleId()).getTitle(),6,
                    comment.getUserId(),comment.getArticleId(),simpleDateFormat.format(date));
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
