package com.malaxiaoyugan.yuukicore.vo;

public class QiniuVo {

    private String token;
    private String domain;
    private String imgUrl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "QiniuVo{" +
                "token='" + token + '\'' +
                ", domain='" + domain + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
