package com.rain.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
    public void doBefore(JoinPoint joinPoint) {
        /*通过getRequestAttributes方法获得参数*/
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        /*再通过getRequest方法获得request*/
        HttpServletRequest request = attributes.getRequest();
        /*现在可通过HttpServletRequest带的方法来获得请求用户的信息*/
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();

        /*通过joinPoint获得拦截的类名和方法名*/
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        /*获得参数*/
        Object[] args = joinPoint.getArgs();

        /*创建返回类*/
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);

        logger.info("***************Before**************Request: {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
        logger.info("*************After**************");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("*******************Returned result : {}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
