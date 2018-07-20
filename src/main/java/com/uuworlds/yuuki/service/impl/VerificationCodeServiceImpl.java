package com.uuworlds.yuuki.service.impl;


import com.uuworlds.yuuki.service.RedisService;
import com.uuworlds.yuuki.service.SmsService;
import com.uuworlds.yuuki.service.VerificationCodeService;
import com.uuworlds.yuuki.utils.GsonUtils;
import com.uuworlds.yuuki.utils.Params;
import com.uuworlds.yuuki.utils.StringListUtils;
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
                .set(Params.VERIFYCODE_KEY + phone, GsonUtils.getGson().toJson(StringListUtils
                                .oneStringToList(code)), 120, TimeUnit.SECONDS,
                        Params.VERIFYCODE_KEY);
        return code;

    }

    @Override
    public boolean checkVerifyCode(String phone,String verificationCode) {
        String code = StringListUtils.listToOneString(GsonUtils.getGson().fromJson(redisService
                .get(Params.VERIFYCODE_KEY + phone, Params.VERIFYCODE_KEY), List
                .class));
        return verificationCode.equals(code);
    }
}
