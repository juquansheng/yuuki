package com.uuworlds.yuuki;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class yuukiController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String getString(){
        return "yuuki";
    }
}
