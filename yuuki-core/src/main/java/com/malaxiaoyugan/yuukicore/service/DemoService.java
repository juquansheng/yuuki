package com.malaxiaoyugan.yuukicore.service;

import com.malaxiaoyugan.yuukicore.entity.Demo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;


public interface DemoService {
    Demo insert(Demo demo);
    Demo update(Demo demo);
    void delete(Long id);
    PageBean getList(int page, int rows);
}
