package com.rain.blog.dao;

import com.rain.blog.classes.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*接口实现多继承*/
public interface BlogRepository extends JpaRepository<Blog, Long>,JpaSpecificationExecutor<Blog> {

    Blog findByid(Long id);
}
