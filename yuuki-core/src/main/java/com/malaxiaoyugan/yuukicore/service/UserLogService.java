package com.malaxiaoyugan.yuukicore.service;

import com.malaxiaoyugan.yuukicore.entity.UserLog;
import com.malaxiaoyugan.yuukicore.vo.PageBean;

/**
 *@describe 操作日志
 *@author  ttbf
 *@date  2018/9/25
 */
public interface UserLogService {
    /**
     * 添加操作记录
     * @param title
     * @param type
     * @param userId
     * @param mainId
     * @param timeString
     */
    void insert(String title,Integer type,Long userId,Long mainId,String timeString);

    /**
     * 获取操作记录
     * @param userId
     * @param page
     * @param rows
     * @return
     */
    PageBean getList(Long userId, int page, int rows);
}
