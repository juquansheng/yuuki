package com.malaxiaoyugan.yuukicore.vo;

import com.malaxiaoyugan.yuukicore.entity.Article;

public class ArticleVo extends Article {
    private String nickName;

    private String contentString;

    private String createTimeString;

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public String getContentString() {
        return contentString;
    }

    public void setContentString(String contentString) {
        this.contentString = contentString;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
