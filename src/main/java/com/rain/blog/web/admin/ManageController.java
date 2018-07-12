package com.rain.blog.web.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ManageController {

    @GetMapping("/manage")
    public String viewBlogs() {
        return "admin/manage";
    }
}
