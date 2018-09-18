package controller;

import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.entity.Comment;
import com.malaxiaoyugan.yuukicore.entity.Reply;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.service.ArticleService;
import com.malaxiaoyugan.yuukicore.service.ReplyService;
import com.malaxiaoyugan.yuukicore.utils.ListUtils;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;
import com.malaxiaoyugan.yuukicore.vo.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping(value = "reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    /**
     * 添加回复
     * @param reply
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public ResponseVO insert(@RequestBody Reply reply){
        Reply insert = replyService.insert(reply);
        if (insert == null){
            return TTBFResultUtil.error("添加回复失败");
        }
        return TTBFResultUtil.success("回复成功",insert);
    }

    /**
     * 删除回复
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResponseVO getDetail(@RequestParam("id") Long id){
        boolean delete = replyService.delete(id);
        if (delete){
            return TTBFResultUtil.success("删除成功");
        }
        return TTBFResultUtil.error("删除失败");
    }

    /**
     * 根据评论id获取评论列表
     * @param id
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getlist",method = RequestMethod.POST)
    public ResponseVO getList(@RequestParam("id") Long id,@RequestParam("page") Integer page,
                              @RequestParam("rows") Integer rows) {
        PageBean pageBean = replyService.list(id, page, rows);
        return TTBFResultUtil.success("获取成功",pageBean);
    }

}
