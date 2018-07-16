package com.rain.blog.web;

import com.rain.blog.classes.BlogQuery;
import com.rain.blog.classes.Topic;
import com.rain.blog.service.BlogService;
import com.rain.blog.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TopicShowController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/topics/{id}")
    public String showTopics(@PathVariable Long id,
                             @PageableDefault(size = 10, sort="updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model) {

        List<Topic> topics = topicService.topTopics(1000);
        if (id == -1) {
            id = topics.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTopicId(id);
        model.addAttribute("topics", topics);
        model.addAttribute("page", blogService.blogListShow(pageable, blogQuery));
        model.addAttribute("activeTopicId", id);
        return "topics";
    }
}
