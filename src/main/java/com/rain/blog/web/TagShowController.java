package com.rain.blog.web;

import com.rain.blog.classes.Tag;
import com.rain.blog.service.BlogService;
import com.rain.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String showTags(@PathVariable Long id,
                             @PageableDefault(size = 10, sort="updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model) {

        List<Tag> tags = tagService.topTags(1000);
        if (id == -1) {
            id = tags.get(0).getId();
        }

        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.blogListShow(pageable, id));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
