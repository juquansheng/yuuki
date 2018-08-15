package com.malaxiaoyugan.yuukiadmin.controller;


import com.malaxiaoyugan.yuukicore.constants.PassPortConst;
import com.malaxiaoyugan.yuukicore.service.RedisService;
import com.malaxiaoyugan.yuukicore.utils.VerifyCodeUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

@RestController
public class CaptchaController {


    @Autowired
    private RedisService redisService;

    @Autowired
    public CaptchaController( RedisService redisService) {
        this.redisService = redisService;
    }

    @RequestMapping(value = "/verifyImage")
    public ModelAndView getKaptchaImage(@RequestParam String timeStamp, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        try {
            String verifyCode = VerifyCodeUtils.outputVerifyImage(200, 50, out, 4);
            redisService.set(PassPortConst.VERIFYCODE_KEY + timeStamp, verifyCode, 60 * 2, TimeUnit.SECONDS,
                    PassPortConst.VERIFYCODE_KEY);
            Cookie cookie = new Cookie("verifyKey", timeStamp);
            cookie.setMaxAge(300);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }
}