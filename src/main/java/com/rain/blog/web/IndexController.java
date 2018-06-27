package com.rain.blog.web;

import com.rain.blog.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*********Controller********/
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
//        int i = 9 / 0;
//        String blog = null;
//        if (blog == null) {
//            throw new NotFoundException("*******This blog is no longer here********");
//        }
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
