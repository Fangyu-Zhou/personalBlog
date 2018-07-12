package com.rain.blog.web.admin;

import com.rain.blog.classes.Tag;
import com.rain.blog.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;

    /*这里通过PageableDefault对分页查询进行设置，一页8条通过id倒序排列*/
    @GetMapping("/tags")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = DESC) Pageable pageable, Model model) {

        /*这里是把分页查询的对象传递给网页前端让前端可以get到，传递的输入域名称是"page"*/
        model.addAttribute("page", tagService.tagList(pageable));

        return "admin/tagManage";
    }

    @GetMapping("/tags/input")
    public String addTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/addTag";
    }

    /*这里用到的PathVariable是用来对应到域名中的id*/
    /*这里的参数id是从前端thymleaf模板中传回来的，根源都在于pageable的json格式变量page中*/
    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id, Model model) {
        model.addAttribute(tagService.getTag(id));
        return "admin/addTag";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","Tag deleted" );
        return "redirect:/admin/tags";
    }

    /*Valid注解是用来查看该变量在与数据库交互的过程中是否会有校验失败, BindingResult是Valid注解的变量的校验信息*/
    /*BindingResult一定要在需要验证的成员变量边上才会有验证效果*/
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            /*这里是手动向result中添加一些默认没有的错误, 第一个参数是校验的成员变量名, 第二个是自定义的错误名称, 第三个是错误提示信息*/
            result.rejectValue("name","nameError","This tag is already exist!");
        }
        /*判断是否存在默认的校验错误*/
        if (result.hasErrors()) {
            return "admin/addTag";
        }
        Tag t = tagService.saveTag(tag);
        /*如果要给redirect的页面传递消息 需要用到RedirectAttributes类*/
        if (t != null) {
            redirectAttributes.addFlashAttribute("message", "New tag added!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Fail to add tag");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String post(@Valid Tag tag, BindingResult result,
                       @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            /*这里是手动向result中添加一些默认没有的错误, 第一个参数是校验的成员变量名, 第二个是自定义的错误名称, 第三个是错误提示信息*/
            result.rejectValue("name","nameError","This tag is already exist!");
        }
        /*判断是否存在默认的校验错误*/
        if (result.hasErrors()) {
            return "admin/addTag";
        }
        Tag t = tagService.updateTag(id, tag);
        /*如果要给redirect的页面传递消息 需要用到RedirectAttributes类*/
        if (t != null) {
            redirectAttributes.addFlashAttribute("message", "Tag updated");
        } else {
            redirectAttributes.addFlashAttribute("message", "Fail to update tag");
        }
        return "redirect:/admin/tags";
    }
}
