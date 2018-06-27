package com.rain.blog.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice //会拦截所有发生异常错误的页面
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());  //选择slf4j类型的Logger

    /*@表明此函数为ExceptionHandler并且括号中表示拦截的错误为Exception级别*/
    @ExceptionHandler({Exception.class})
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request Url: {}, Exception: {}", request.getRequestURL(),e);

        /*因为我们在这个函数中将所有异常一起处理了 所以当我们想要单独处理NotFound异常是需要先定义一个Notfound
        * 异常然后再这个大异常处理中将notFound异常单独处理*/
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        /*ModelAndView类是一个可返回的相当于网页*/
        /*获取异常相关的信息放入modelAndView Object中*/
        mv.addObject("Url", request.getRequestURL());
        mv.addObject("Exception", e);

        /*设置出现异常错误之后的导向页面*/
        mv.setViewName("error/error");
        return mv;
    }
}
