package controller;

import com.malaxiaoyugan.yuukicore.entity.Article;
import com.malaxiaoyugan.yuukicore.framework.object.ResponseVO;
import com.malaxiaoyugan.yuukicore.service.ArticleService;
import com.malaxiaoyugan.yuukicore.utils.ListUtils;
import com.malaxiaoyugan.yuukicore.utils.TTBFResultUtil;
import com.malaxiaoyugan.yuukicore.vo.ArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 添加修改文章
     * @param articleVo
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public ResponseVO insert(ArticleVo articleVo) throws UnsupportedEncodingException {
        Article article = new Article();
        BeanUtils.copyProperties(articleVo,article);
        article.setContent(articleVo.getContentString().getBytes("UTF-8"));
        String introduce;
        if (articleVo.getContentString().length() > 20){
            introduce = articleVo.getContentString().substring(0, 20);
        }else {
            introduce = articleVo.getContentString();
        }

        //转string
        //String isoString = new String(articleVo.getContentString().getBytes("UTF-8"),"UTF-8");
        //获取用户id
        Object principals = SecurityUtils.getSubject().getPrincipals();
        //Long id = Long.parseLong(principals.toString());
        Long id = 1L;
        if (article.getId() == null){
            Article inset = articleService.inset(article, introduce,id);
            if (inset == null){
                return TTBFResultUtil.error("添加失败");
            }
            return TTBFResultUtil.success("发布成功",inset);
        }else {
            if (article.getUserId() != id){
                return TTBFResultUtil.error("没有修改权限");
            }
            Article update = articleService.update(article,introduce, id);
            if (update == null){
                return TTBFResultUtil.error("修改失败");
            }
            return TTBFResultUtil.success("修改成功",update);
        }
    }

    /**
     * 获取文章内容
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/getdetail",method = RequestMethod.GET)
    public ResponseVO getDetail(@RequestParam("id") Long id) throws UnsupportedEncodingException {
        ArticleVo articleVo = articleService.getDetail(id);
        if (articleVo == null){
            return TTBFResultUtil.error("未获取到文章内容");
        }
        return TTBFResultUtil.success("获取成功",articleVo);
    }

    /**
     * 获取文章列表
     * @param article
     * @return
     */
    @RequestMapping(value = "/getlist",method = RequestMethod.GET)
    public ResponseVO getList(@RequestBody Article article,@RequestParam("page") Integer page,
                              @RequestParam("rows") Integer rows) {
        List<Article> list = articleService.getList(article, page, rows);
        if (ListUtils.isEmpty(list)){
            return TTBFResultUtil.error("未获取到文章");
        }
        return TTBFResultUtil.success("获取成功",list);
    }
}