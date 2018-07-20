package com.uuworlds.yuuki.service;

public interface VerificationCodeService {
    /**
     * 根据phone生成验证码并保存redis
     * @param phone
     */
    String getAndSaveVerificationCode(String phone);
    /**
     * 判断验证码是否正确验证码
     * @param phone
     * @return
     */
    boolean checkVerifyCode(String phone, String verificationCode);
}
