package com.malaxiaoyugan.yuukicore.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.entity.Reply;
import com.malaxiaoyugan.yuukicore.entity.ReplyExample;
import com.malaxiaoyugan.yuukicore.mapper.ReplyMapper;
import com.malaxiaoyugan.yuukicore.service.ReplyService;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *@describe 回复评论接口
 *@author  ttbf
 *@date  2018/9/18
 */
@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;


    @Override
    public Reply insert(Reply reply) {
        Date date = new Date();
        reply.setCreateTime(date);
        reply.setUpdateTime(date);
        int insertSelective = replyMapper.insertSelective(reply);
        if (insertSelective > 0){
            return reply;
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Reply delReply = new Reply();
        delReply.setReplyId(id);
        delReply.setStatus(-1);
        delReply.setUpdateTime(new Date());
        int update = replyMapper.updateByPrimaryKey(delReply);
        if (update > 0){
            return true;
        }
        return false;
    }

    @Override
    public PageBean list(Long id, int page, int rows) {
        ReplyExample replyExample = new ReplyExample();
        replyExample.createCriteria().andStatusNotEqualTo(-1).andCommentIdEqualTo(id);
        if (page != 0 && rows != 0) {
            PageHelper.startPage(page, rows);
        }
        List<Reply> replyList = replyMapper.selectByExample(replyExample);
        PageBean<Reply> pageBean = new PageBean<>();
        PageInfo<Reply> pageInfo = new PageInfo<>(replyList);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setPageDatas(replyList);
        return pageBean;
    }
}
