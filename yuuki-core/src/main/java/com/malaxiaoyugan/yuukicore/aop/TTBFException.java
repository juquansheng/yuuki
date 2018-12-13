package com.malaxiaoyugan.yuukicore.aop;

/**
 * 全局异常（传入需要返回给前台的状态码和回显信息）
 */
public class TTBFException extends RuntimeException {

    private int status;

    public TTBFException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
