package com.rain.blog.web.admin;

import com.rain.blog.classes.Topic;
import com.rain.blog.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequestMapping("/admin")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /*这里通过PageableDefault对分页查询进行设置，一页8条通过id倒序排列*/
    @GetMapping("/topics")
    public String list(@PageableDefault(size = 8, sort = {"id"}, direction = DESC) Pageable pageable, Model model) {

        /*这里是把分页查询的对象传递给网页前端让前端可以get到*/
        model.addAttribute("page", topicService.topicList(pageable));

        return "admin/topicManage";
    }

    @GetMapping("/topics/input")
    public String addTopic() {
        return "admin/addTopic";
    }

    @PostMapping("/topics")
    public String post(Topic topic) {
        Topic t = topicService.saveTopic(topic);
        if (t == null) {

        } else {

        }
        return "redirect:/admin/topics";
    }
}
