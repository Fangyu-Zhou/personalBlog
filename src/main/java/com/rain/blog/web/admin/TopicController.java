package com.rain.blog.web.admin;

import com.rain.blog.classes.Topic;
import com.rain.blog.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequestMapping("/admin")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /*这里通过PageableDefault对分页查询进行设置，一页8条通过id倒序排列*/
    @GetMapping("/topics")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = DESC) Pageable pageable, Model model) {

        /*这里是把分页查询的对象传递给网页前端让前端可以get到，传递的输入域名称是"page"*/
        model.addAttribute("page", topicService.topicList(pageable));

        return "admin/topicManage";
    }

    @GetMapping("/topics/input")
    public String addTopic(Model model) {
        model.addAttribute("topic", new Topic());
        return "admin/addTopic";
    }

    /*这里用到的PathVariable是用来对应到域名中的id*/
    /*这里的参数id是从前端thymleaf模板中传回来的，根源都在于pageable的json格式变量page中*/
    @GetMapping("/topics/{id}/input")
    public String editTopic(@PathVariable Long id, Model model) {
        model.addAttribute(topicService.getTopic(id));
        return "admin/addTopic";
    }

    @GetMapping("/topics/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        topicService.deleteTopic(id);
        redirectAttributes.addFlashAttribute("message","Topic deleted" );
        return "redirect:/admin/topics";
    }

    /*Valid注解是用来查看该变量在与数据库交互的过程中是否会有校验失败, BindingResult是Valid注解的变量的校验信息*/
    /*BindingResult一定要在需要验证的成员变量边上才会有验证效果*/
    @PostMapping("/topics")
    public String post(@Valid Topic topic, BindingResult result, RedirectAttributes redirectAttributes) {
        Topic topic1 = topicService.getTopicByName(topic.getName());
        if (topic1 != null) {
            /*这里是手动向result中添加一些默认没有的错误, 第一个参数是校验的成员变量名, 第二个是自定义的错误名称, 第三个是错误提示信息*/
            result.rejectValue("name","nameError","This topic is already exist!");
        }
        /*判断是否存在默认的校验错误*/
        if (result.hasErrors()) {
            return "admin/addTopic";
        }
        Topic t = topicService.saveTopic(topic);
        /*如果要给redirect的页面传递消息 需要用到RedirectAttributes类*/
        if (t != null) {
            redirectAttributes.addFlashAttribute("message", "New topic added!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Fail to add topic");
        }
        return "redirect:/admin/topics";
    }

    @PostMapping("/topics/{id}")
    public String post(@Valid Topic topic, BindingResult result,
                       @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Topic topic1 = topicService.getTopicByName(topic.getName());
        if (topic1 != null) {
            /*这里是手动向result中添加一些默认没有的错误, 第一个参数是校验的成员变量名, 第二个是自定义的错误名称, 第三个是错误提示信息*/
            result.rejectValue("name","nameError","This topic is already exist!");
        }
        /*判断是否存在默认的校验错误*/
        if (result.hasErrors()) {
            return "admin/addTopic";
        }
        Topic t = topicService.updateTopic(id, topic);
        /*如果要给redirect的页面传递消息 需要用到RedirectAttributes类*/
        if (t != null) {
            redirectAttributes.addFlashAttribute("message", "Topic updated");
        } else {
            redirectAttributes.addFlashAttribute("message", "Fail to update topic");
        }
        return "redirect:/admin/topics";
    }
}
