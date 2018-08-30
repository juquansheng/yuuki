package com.malaxiaoyugan.yuukicore.vo;

import com.malaxiaoyugan.yuukicore.entity.Article;

public class ArticleVo extends Article {
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
