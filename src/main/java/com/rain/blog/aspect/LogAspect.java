package com.rain.blog.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*这两个注解是为了让spring boot通过找到LogAspect这个类来处理logger*/
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*该注解是定义将此方法定义为切面，并在括号中申明切面需要拦截的范围*/
    /*切面会拦截Controller下的所有对方法的请求也就是说在执行web.indexcontroller中的方法之前,之后,返回时在该类中定义对应的方法*/
    @Pointcut("execution(* com.rain.blog.web.*.*(..))")
    public void log() {
    }

    /*该注解标明doBefore方法会在log()切面之前执行*/
    @Before("log()")
    public void doBefore() {
        logger.info("***************Before**************");
    }

    @After("log()")
    public void doAfter() {
        logger.info("*************After**************");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("*******************Returned result : {}", result);
    }
}
