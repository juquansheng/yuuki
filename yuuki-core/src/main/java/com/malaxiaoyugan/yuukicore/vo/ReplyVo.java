package com.malaxiaoyugan.yuukicore.vo;

import com.malaxiaoyugan.yuukicore.entity.Reply;

public class ReplyVo extends Reply {
    private String contentString;

    private String createTimeString;

    private String nickName;

    private String toNickName;

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

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }
}
