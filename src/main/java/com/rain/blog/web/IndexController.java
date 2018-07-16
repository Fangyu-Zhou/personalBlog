package com.rain.blog.web;

import com.rain.blog.NotFoundException;
import com.rain.blog.classes.BlogQuery;
import com.rain.blog.classes.Topic;
import com.rain.blog.service.BlogService;
import com.rain.blog.service.CommentService;
import com.rain.blog.service.TagService;
import com.rain.blog.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;

/*********Controller********/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort="updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.publishedBlogList(pageable));
        model.addAttribute("topics", topicService.topTopics(5));
        model.addAttribute("tags", tagService.topTags(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendedBlog(6));
        System.out.println("-----------------Index------------------");
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 10, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, /*这里是讲前端input标签内的query变量拿到后端*/
                         Model model) {
        model.addAttribute("page", blogService.blogList("%" + query + "%", pageable));
        model.addAttribute("query", query);

        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blogDetail(@PathVariable Long id, Model model) {
        /*每次查看blog更新views要在每次get blogdetail的时候updateblog信息*/
        model.addAttribute("blog", blogService.getDetailBlog(id));
//        model.addAttribute("comments", commentService.listCommentByBlogId(id));
        System.out.println("-----------------blog Detail------------------");
        return "blogDetail";
    }
}
