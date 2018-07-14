package com.rain.blog.web;

import com.rain.blog.NotFoundException;
import com.rain.blog.classes.BlogQuery;
import com.rain.blog.classes.Topic;
import com.rain.blog.service.BlogService;
import com.rain.blog.service.TagService;
import com.rain.blog.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*********Controller********/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort="updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.blogList(pageable));
        model.addAttribute("topics", topicService.topTopics(5));
        model.addAttribute("tags", tagService.topTags(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendedBlog(6));
        System.out.println("-----------------Index------------------");
        return "index";
    }

    @GetMapping("/blogDetail")
    public String blogDetail() {
//        int i = 9 / 0;
//        String blog = null;
//        if (blog == null) {
//            throw new NotFoundException("*******This blog is no longer here********");
//        }
        System.out.println("-----------------blog Detail------------------");
        return "blogDetail";
    }
}
