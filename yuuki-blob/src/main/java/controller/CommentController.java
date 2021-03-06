package controller;


import com.malaxiaoyugan.yuukicore.entity.Comment;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.service.CommentService;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.vo.CommentVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@Slf4j
@RestController
@RequestMapping(value = "comment")
public class CommentController {
    @Autowired
    private CommentService commentService;


    /**
     * 添加评论
     * @param commentVo
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public ResponseVO insert(@RequestBody CommentVo commentVo) throws UnsupportedEncodingException {
        //获取用户id
        Object principals = SecurityUtils.getSubject().getPrincipals();
        long id = Long.parseLong(principals.toString());
        if (commentVo.getArticleId() == null){
            return TTBFResultUtil.error("要评论文章不存在");
        }
        commentVo.setUserId(id);
        commentVo.setContent(commentVo.getContentString().getBytes("UTF-8"));
        CommentVo insert = commentService.insert(commentVo);
        if (insert == null){
            return TTBFResultUtil.error("添加评论失败");
        }
        return TTBFResultUtil.success("评论成功",insert);
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResponseVO getDetail(@RequestParam("id") Long id){
        boolean delete = commentService.delete(id);
        if (delete){
            return TTBFResultUtil.success("删除成功");
        }
        return TTBFResultUtil.error("删除失败");
    }

    /**
     * 根据文章id获取评论列表
     * @param id
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getlist",method = RequestMethod.GET)
    public ResponseVO getList(@RequestParam("id") Long id,@RequestParam("page") Integer page,
                              @RequestParam("rows") Integer rows) throws UnsupportedEncodingException {
        PageBean pageBean = commentService.list(id, page, rows);
        return TTBFResultUtil.success("获取成功",pageBean);
    }


}
