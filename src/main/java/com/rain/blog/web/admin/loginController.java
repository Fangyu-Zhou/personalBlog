package com.rain.blog.web.admin;

import com.rain.blog.classes.User;
import com.rain.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin") /*这里的意思是处理localhost/admin地址下的请求*/
public class loginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginpage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkuser(username, password);
        if (user != null) {
            user.setPassword(null); /*这一步是为了防止密码泄露，因为后面的session可以获得该user的信息同时包括password，所以将其设置为不可获得 */

            session.setAttribute("user", user);
            return "admin/manageIndex";
        } else {
            /*将信息交给重新定向之后的页面，如果用model来获取信息，redirect之后的页面拿不到信息*/
            attributes.addFlashAttribute("message", "Invbalid Username or password");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
