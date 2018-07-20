package com.uuworlds.yuuki.controller;


import com.uuworlds.yuuki.service.VerificationCodeService;
import com.uuworlds.yuuki.utils.TTBFResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *@describe 验证码相关接口
 *@author  ttbf
 *@date  2018/7/11
 */
@RestController
@RequestMapping(value = "/code")
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;
    private static final Logger logger = LoggerFactory.getLogger(VerificationCodeController.class);

    @RequestMapping(value = "/getCode",method = RequestMethod.GET)
    public TTBFResult getCode(@Param("phone") String phone){
        String code = verificationCodeService.getAndSaveVerificationCode(phone);
        logger.info(code);
        if (code!=null){
            return TTBFResult.build(200,"获取验证码成功");
        }else {
            return TTBFResult.build(505,"获取验证码失败");
        }
    }

    @RequestMapping(value = "/check",method = RequestMethod.GET)
    public TTBFResult check(@Param("phone") String phone,@Param("code") String code){
        boolean checkVerifyCode = verificationCodeService.checkVerifyCode(phone, code);
        if (checkVerifyCode){
            return TTBFResult.build(200,"验证码正确");
        }else {
            return TTBFResult.build(505,"验证码错误");
        }
    }
}
