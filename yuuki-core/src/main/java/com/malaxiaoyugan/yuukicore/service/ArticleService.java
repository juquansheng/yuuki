package com.malaxiaoyugan.yuukicore.service;

import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *@describe
 *@author  ttbf
 *@date  2018/8/30
 */
public interface ArticleService {
    /**
     * 更新文章
     * @param article
     * @return
     */
    Article update(Article article,String introduce,Long userId);

    /**
     * 添加文章
     * @param article
     * @return
     */
    Article inset(Article article,String introduce, Long userId);

    /**
     * 获取文章
     * @param id
     * @return
     */
    ArticleVo getDetail(Long id) throws UnsupportedEncodingException;

    /**
     * 查询文章
     * @param article
     * @return
     */
    PageBean getList(Article article, int page, int rows);

    /**
     * 删除文章
     * @param id
     * @return
     */
    boolean deleteArticle(Long id);
}
