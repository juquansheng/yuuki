package com.uuworlds.yuuki.service;

public interface SmsService {

    /**
     * 阿里云短息服务
     * @param mobile
     * @param code
     */
    void sendMessageByAliyun(String mobile, String code);

}
