package com.rain.blog.dao;

import com.rain.blog.classes.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*接口实现多继承*/
public interface BlogRepository extends JpaRepository<Blog, Long>,JpaSpecificationExecutor<Blog> {

    Blog findByid(Long id);

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);
}
