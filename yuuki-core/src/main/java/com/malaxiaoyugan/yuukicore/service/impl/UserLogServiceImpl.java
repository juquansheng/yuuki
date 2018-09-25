package com.malaxiaoyugan.yuukicore.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.entity.UserLog;
import com.malaxiaoyugan.yuukicore.entity.UserLogExample;
import com.malaxiaoyugan.yuukicore.mapper.UserLogMapper;
import com.malaxiaoyugan.yuukicore.service.UserLogService;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserLogServiceImpl implements UserLogService {
    @Autowired
    private UserLogMapper userLogMapper;
    @Override
    public void insert(String title,Integer type,Long userId,Long mainId,String timeString) {
        //添加操作记录
        UserLog userLog = new UserLog();
        userLog.setCreateTime(new Date());
        //操作类型-1修改密码0发表文章1发表评论2收藏3发表回复4修改文章5删除文章6删除评论
        if (type == -1){
            userLog.setContent("您于"+timeString+"修改了密码");
        }else if (type == 0){
            userLog.setContent("您于"+timeString+"发表了文章《"+title+"》");
        }else if (type == 1){
            userLog.setContent("您于"+timeString+"评论了文章《"+title+"》");
        }else if (type == 2){
            userLog.setContent("您于"+timeString+"收藏了《"+title+"》");
        }else if (type == 3){
            userLog.setContent("您于"+timeString+"恢复了用户"+title+"");
        }else if (type == 4){
            userLog.setContent("您于"+timeString+"修改了文章《"+title+"》");
        }else if (type == 5){
            userLog.setContent("您于"+timeString+"删除了文章《"+title+"》");
        }else {
            userLog.setContent("");
        }

        userLog.setOpType(type);
        userLog.setUserId(userId);
        userLog.setMainId(mainId);
        userLogMapper.insertSelective(userLog);
    }

    @Override
    public PageBean getList(Long userId, int page, int rows) {
        UserLogExample userLogExample = new UserLogExample();
        userLogExample.createCriteria().andUserIdEqualTo(userId);
        if (page != 0 && rows != 0) {
            PageHelper.startPage(page, rows);
        }
        userLogExample.setOrderByClause("create_time desc");
        List<UserLog> userLogList = userLogMapper.selectByExample(userLogExample);
        PageBean<UserLog> pageBean = new PageBean<>();
        PageInfo<UserLog> pageInfo = new PageInfo<>(userLogList);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setTotalPages(pageInfo.getPages());
        pageBean.setPageNumber(pageInfo.getPageNum());
        pageBean.setPageSize(pageInfo.getSize());
        pageBean.setPageDatas(userLogList);
        return pageBean;

    }
}
