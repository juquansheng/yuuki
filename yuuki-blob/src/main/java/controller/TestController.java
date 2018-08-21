package controller;


import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *@describe
 *@author  ttbf
 *@date  2018/8/15
 */
@Slf4j
@RestController
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    private TestService testService;




    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseVO test() {

            return TTBFResultUtil.success("t",testService.test());

    }
}
