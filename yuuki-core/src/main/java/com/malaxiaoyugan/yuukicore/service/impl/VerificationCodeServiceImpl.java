package com.malaxiaoyugan.yuukicore.service.impl;



import com.malaxiaoyugan.yuukicore.constants.PassPortConst;
import com.malaxiaoyugan.yuukicore.service.RedisService;
import com.malaxiaoyugan.yuukicore.service.SmsService;
import com.malaxiaoyugan.yuukicore.service.VerificationCodeService;
import com.malaxiaoyugan.yuukicore.utils.GsonUtils;
import com.malaxiaoyugan.yuukicore.constants.Params;
import com.malaxiaoyugan.yuukicore.utils.StringListUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    private static final Logger logger = LoggerFactory.getLogger(VerificationCodeServiceImpl.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private SmsService smsService;

    @Override
    public String getAndSaveVerificationCode(String phone) {
        String code = RandomStringUtils.randomNumeric(6);
        smsService.sendMessageByAliyun(phone, code);
        redisService
                .set(PassPortConst.CAPTCHA + phone, GsonUtils.getGson().toJson(StringListUtils
                                .oneStringToList(code)), 120, TimeUnit.SECONDS,
                        PassPortConst.CAPTCHA);
        return code;

    }

    @Override
    public boolean checkVerifyCode(String phone,String verificationCode) {
        String code = StringListUtils.listToOneString(GsonUtils.getGson().fromJson(redisService
                .get(PassPortConst.CAPTCHA + phone, PassPortConst.CAPTCHA), List
                .class));
        return verificationCode.equals(code);
    }
}
