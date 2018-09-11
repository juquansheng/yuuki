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


/**
 *@describe
 *@author  ttbf
 *@date  2018/8/15
 */
@Slf4j
@RestController
@RequestMapping(value = "test")
public class TestController {






    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseVO test() {

            return TTBFResultUtil.success("t","test");

    }
}
