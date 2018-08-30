package controller;

import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.entity.User;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.service.ArticleService;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public ResponseVO insert(@RequestBody Article article) {
        //获取用户id
        Object principals = SecurityUtils.getSubject().getPrincipals();
        Long id = Long.parseLong(principals.toString());
        if (article.getId() == null){
            Article inset = articleService.inset(article, id);
            if (inset == null){
                return TTBFResultUtil.error("添加失败");
            }
            return TTBFResultUtil.success("发布成功",inset);
        }else {
            if (article.getId() != id){
                return TTBFResultUtil.error("没有修改权限");
            }
            Article update = articleService.update(article, id);
            if (update == null){
                return TTBFResultUtil.error("修改失败");
            }
            return TTBFResultUtil.success("修改成功",update);
        }
    }

    @RequestMapping(value = "/getdetail",method = RequestMethod.GET)
    public ResponseVO getDetail(@RequestParam("id") Long id) {
        ArticleVo articleVo = articleService.get(id);
        if (articleVo == null){
            return TTBFResultUtil.error("未获取到文章内容");
        }
        return TTBFResultUtil.success("获取成功",articleVo);

    }
}
