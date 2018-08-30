package com.malaxiaoyugan.yuukicore.service;

import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;

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
    Article update(Article article,Long userId);

    /**
     * 添加文章
     * @param article
     * @return
     */
    Article inset(Article article,Long userId);

    /**
     * 获取文章
     * @param id
     * @return
     */
    ArticleVo get(Long id);

    /**
     * 查询文章
     * @param article
     * @return
     */
    List<ArticleVo> getList(Article article);
}
