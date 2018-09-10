package com.malaxiaoyugan.yuukicore.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.entity.ArticleExample;
import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.mapper.ArticleMapper;
import com.malaxiaoyugan.yuukicore.mapper.UserMapper;
import com.malaxiaoyugan.yuukicore.service.ArticleService;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
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
    public Article update(Article article,String introduce, Long userId) {
        if (article.getId() == null){
            log.error("文章不存在");
            return null;
        }
        article.setIntroduce(introduce);
        article.setUpdateTime(new Date());
        int updateByPrimaryKeyWithBLOBs = articleMapper.updateByPrimaryKeyWithBLOBs(article);
        if (updateByPrimaryKeyWithBLOBs >0){
            return article;
        }else {
            return null;
        }
    }

    @Override
    public Article inset(Article article,String introduce,Long userId) {
        article.setUserId(userId);
        article.setIntroduce(introduce);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        int insertSelective = articleMapper.insertSelective(article);
        if (insertSelective > 0){
            return article;
        }
        return null;
    }

    @Override
    public ArticleVo getDetail(Long id) throws UnsupportedEncodingException {
        ArticleVo articleVo = new ArticleVo();
        Article article = articleMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setContentString(new String(article.getContent(),"UTF-8"));
        User user = userMapper.selectByPrimaryKey(article.getUserId());
        if (user != null){
            articleVo.setNickName(user.getNickName());
        }
        return articleVo;
    }

    @Override
    public PageBean getList(Article article, int page, int rows) {
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        //选择条件
        if (article.getUserId() != null){
            criteria.andUserIdEqualTo(article.getUserId());
        }
        articleExample.setOrderByClause("create_time desc");
        if (page != 0 && rows != 0) {
            PageHelper.startPage(page, rows);
        }
        List<Article> articleList = articleMapper.selectByExample(articleExample);
        PageBean<Article> pageBean = new PageBean<>();
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setPageDatas(articleList);
        return pageBean;
    }
}
