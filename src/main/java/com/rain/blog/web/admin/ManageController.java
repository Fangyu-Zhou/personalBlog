package com.rain.blog.web.admin;


import com.rain.blog.classes.Blog;
import com.rain.blog.classes.BlogQuery;
import com.rain.blog.classes.User;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class ManageController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TagService tagService;

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

    @GetMapping("/blogs/publish")
    public String publish(Model model) {
        model.addAttribute("topics", topicService.topicList());
        model.addAttribute("tags", tagService.tagList());
        model.addAttribute("blog", new Blog());
        return "admin/publish";
    }

    @GetMapping("/blogs/{id}/publish")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("topics", topicService.topicList());
        model.addAttribute("tags", tagService.tagList());
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return "admin/publish";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setTopic(topicService.getTopic(blog.getTopic().getId()));
        blog.setTags(tagService.tagList(blog.getTagIds()));

        Blog b = blogService.saveBlog(blog);
        if (b != null) {
            redirectAttributes.addFlashAttribute("message", "Success!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed!");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message", "successfully deleted!");
        return "redirect:/admin/blogs";
    }
}
