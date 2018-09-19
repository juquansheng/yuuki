package com.malaxiaoyugan.yuukicore.vo;

import com.malaxiaoyugan.yuukicore.entity.Comment;

public class CommentVo extends Comment {
    private String contentString;

    private String createTimeString;

    private String nickName;

    public String getContentString() {
        return contentString;
    }

    public void setContentString(String contentString) {
        this.contentString = contentString;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
