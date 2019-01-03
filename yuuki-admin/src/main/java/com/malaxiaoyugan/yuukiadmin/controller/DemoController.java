package com.malaxiaoyugan.yuukiadmin.controller;

import com.malaxiaoyugan.yuukicore.entity.Demo;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.service.DemoService;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value = "/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public ResponseVO insert(@RequestBody Demo demo) {
        demoService.insert(demo);
        return TTBFResultUtil.success("成功");
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseVO update(@RequestBody Demo demo) {
        demoService.update(demo);
        return TTBFResultUtil.success("成功");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResponseVO delete(@RequestParam("id") Long id) {
        demoService.delete(id);
        return TTBFResultUtil.success("成功");
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ResponseVO get(@RequestParam("page") Integer page,
                          @RequestParam("rows") Integer rows) {
        PageBean list = demoService.getList(page, rows);
        return TTBFResultUtil.success("成功",list);
    }
}
