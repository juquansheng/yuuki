package com.malaxiaoyugan.yuukicore.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.malaxiaoyugan.yuukicore.aop.TTBFException;
import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.entity.Demo;
import com.malaxiaoyugan.yuukicore.entity.DemoExample;
import com.malaxiaoyugan.yuukicore.mapper.DemoMapper;
import com.malaxiaoyugan.yuukicore.service.DemoService;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private DemoMapper demoMapper;

    @Override
    public Demo insert(Demo demo) {
        demo.setCreateTime(new Date());
        demo.setUpdateTime(new Date());
        int insert = demoMapper.insertSelective(demo);
        if (insert <= 0){
            throw new TTBFException(501,"添加失败");
        }
        return demo;
    }

    @Override
    public Demo update(Demo demo) {
        demo.setUpdateTime(new Date());
        int insert = demoMapper.updateByPrimaryKeySelective(demo);
        if (insert <= 0){
            throw new TTBFException(501,"修改失败");
        }
        return demo;
    }

    @Override
    public void delete(Long id) {
        Demo demo = new Demo();
        demo.setId(id);
        demo.setStatus(-1);
        demo.setUpdateTime(new Date());
        int insert = demoMapper.updateByPrimaryKeySelective(demo);
        if (insert <= 0){
            throw new TTBFException(501,"删除失败");
        }
    }

    @Override
    public PageBean getList(int page, int rows) {
        DemoExample demoExample = new DemoExample();
        demoExample.createCriteria().andStatusEqualTo(0);
        if (page != 0 && rows != 0) {
            PageHelper.startPage(page, rows);
        }
        demoExample.setOrderByClause("update_time desc");
        List<Demo> demoList = demoMapper.selectByExample(demoExample);
        PageBean<Demo> pageBean = new PageBean<>();
        PageInfo<Demo> pageInfo = new PageInfo<>(demoList);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setPageNumber(pageInfo.getPageNum());
        pageBean.setPageSize(pageInfo.getSize());
        pageBean.setPageDatas(demoList);
        return pageBean;
    }
}
