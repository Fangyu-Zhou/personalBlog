package com.rain.blog.dao;

import com.rain.blog.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;


/*dao包下是框架对数据库的操作 所以extend JpaRepository*/
public interface UserRepository extends JpaRepository<User, Long> {
    /*这里用到的方法原本框架没有，所以自定义方法需要将前缀格式与存在的方法匹配，即findBy，这样框架才会识别该方法对数据库进行操作*/
    User findByUsernameAndPassword(String username, String password);
}
