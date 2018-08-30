package com.malaxiaoyugan.yuukicore.service.impl;

import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.entity.ArticleExample;
import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.mapper.ArticleMapper;
import com.malaxiaoyugan.yuukicore.mapper.UserMapper;
import com.malaxiaoyugan.yuukicore.service.ArticleService;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Article update(Article article,Long userId) {
        if (article.getId() == null){
            log.error("文章不存在");
            return null;
        }
        article.setUpdateTime(new Date());
        int updateByPrimaryKeyWithBLOBs = articleMapper.updateByPrimaryKeyWithBLOBs(article);
        if (updateByPrimaryKeyWithBLOBs >0){
            return article;
        }else {
            return null;
        }
    }

    @Override
    public Article inset(Article article,Long userId) {
        article.setUserId(userId);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        int insertSelective = articleMapper.insertSelective(article);
        if (insertSelective > 0){
            return article;
        }
        return null;
    }

    @Override
    public ArticleVo get(Long id) {
        ArticleVo articleVo = new ArticleVo();
        Article article = articleMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(article,articleVo);
        User user = userMapper.selectByPrimaryKey(article.getUserId());
        if (user != null){
            articleVo.setNickName(user.getNickName());
        }
        return null;
    }

    @Override
    public List<ArticleVo> getList(Article article) {
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        return null;
    }
}
