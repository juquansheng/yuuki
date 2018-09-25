package com.malaxiaoyugan.yuukicore.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.entity.ArticleExample;
import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.entity.UserLog;
import com.malaxiaoyugan.yuukicore.mapper.ArticleMapper;
import com.malaxiaoyugan.yuukicore.mapper.UserLogMapper;
import com.malaxiaoyugan.yuukicore.mapper.UserMapper;
import com.malaxiaoyugan.yuukicore.service.ArticleService;
import com.malaxiaoyugan.yuukicore.service.UserLogService;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLogService userLogService;



    @Override
    public Article update(Article article,String introduce, Long userId) {
        String time12="yyyy-MM-dd hh:mm:ss";
        String time24="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time24);
        if (article.getId() == null){
            log.error("文章不存在");
            return null;
        }
        article.setIntroduce(introduce);
        Date date = new Date();
        article.setUpdateTime(date);
        int updateByPrimaryKeyWithBLOBs = articleMapper.updateByPrimaryKeySelective(article);
        if (updateByPrimaryKeyWithBLOBs >0){
            userLogService.insert(article.getTitle(),4,article.getUserId(),article.getId(),simpleDateFormat.format(date));
            return article;
        }else {
            return null;
        }
    }

    @Override
    public Article inset(Article article,String introduce,Long userId) {
        String time12="yyyy-MM-dd hh:mm:ss";
        String time24="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time24);
        Date date = new Date();
        article.setUserId(userId);
        article.setIntroduce(introduce);
        article.setCreateTime(date);
        article.setUpdateTime(date);
        int insertSelective = articleMapper.insertSelective(article);
        if (insertSelective > 0){
            userLogService.insert(article.getTitle(),0,article.getUserId(),article.getId(),simpleDateFormat.format(date));
            return article;
        }
        return null;
    }

    @Override
    public ArticleVo getDetail(Long id) throws UnsupportedEncodingException {

        ArticleVo articleVo = new ArticleVo();
        Article article = articleMapper.selectByPrimaryKey(id);

        //增加浏览次数
        Article articleUpdate = new Article();
        articleUpdate.setId(id);
        articleUpdate.setBrowseTimes(article.getBrowseTimes() + 1);
        articleMapper.updateByPrimaryKeySelective(articleUpdate);

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
        String time12="yyyy-MM-dd hh:mm:ss";
        String time24="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time24);
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        //选择条件
        if (article.getUserId() != null){
            //criteria.andUserIdEqualTo(article.getUserId());
        }
        articleExample.setOrderByClause("create_time desc");
        if (page != 0 && rows != 0) {
            PageHelper.startPage(page, rows);
        }
        List<Article> articleList = articleMapper.selectByExample(articleExample);
        List<ArticleVo> articleVoList = Lists.newArrayList();
        for (Article articles:articleList){
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(articles,articleVo);
            articleVo.setNickName(userMapper.selectByPrimaryKey(articleVo.getUserId()).getNickName());
            articleVo.setCreateTimeString(simpleDateFormat.format(articleVo.getCreateTime()));
            articleVoList.add(articleVo);
        }

        PageBean<ArticleVo> pageBean = new PageBean<>();
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setPageNumber(pageInfo.getPageNum());
        pageBean.setPageSize(pageInfo.getSize());
        pageBean.setPageDatas(articleVoList);
        return pageBean;
    }

    @Override
    public boolean deleteArticle(Long id) {
        String time12="yyyy-MM-dd hh:mm:ss";
        String time24="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time24);
        Article article = new Article();
        article.setId(id);
        article.setStatus(-1);
        article.setUpdateTime(new Date());
        int i = articleMapper.updateByPrimaryKeySelective(article);
        if (i > 0){
            userLogService.insert(article.getTitle(),5,article.getUserId(),article.getId(),simpleDateFormat.format(new Date()));
            return true;
        }else {
            return false;
        }

    }
}
