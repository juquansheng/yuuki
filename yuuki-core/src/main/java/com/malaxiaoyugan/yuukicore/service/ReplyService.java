package com.malaxiaoyugan.yuukicore.service;

import com.malaxiaoyugan.yuukicore.entity.Reply;
import com.malaxiaoyugan.yuukicore.vo.PageBean;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ReplyService {

    /**
     * 添加评论
     * @param reply
     * @return
     */
    Reply insert(Reply reply);

    /**
     * 删除回复
     * @param id
     */
    boolean delete(Long id);

    /**
     * 根据评论id获取评论下回复
     * @param id
     * @return
     */
    PageBean list(Long id, int page, int rows) throws UnsupportedEncodingException;

}
