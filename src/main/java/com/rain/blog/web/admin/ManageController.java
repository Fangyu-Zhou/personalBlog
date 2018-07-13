package com.rain.blog.web.admin;


import com.rain.blog.classes.Blog;
import com.rain.blog.classes.BlogQuery;
import com.rain.blog.service.BlogService;
import com.rain.blog.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ManageController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/blogs")
    public String viewBlogs(BlogQuery blog,
                            @PageableDefault(size = 2, sort="updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        model.addAttribute("topics", topicService.topicList());
        model.addAttribute("page", blogService.blogList(pageable, blog));
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(BlogQuery blog,
                            @PageableDefault(size = 2, sort="updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        model.addAttribute("page", blogService.blogList(pageable, blog));
        return "admin/blogs :: blogList";
    }
}
