package com.rain.blog.dao;

import com.rain.blog.classes.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*接口实现多继承*/
public interface BlogRepository extends JpaRepository<Blog, Long>,JpaSpecificationExecutor<Blog> {

    Blog findByid(Long id);

    @Query("select b from Blog b where b.published = true")
    Page<Blog> findAllPublished(Pageable pageable);

    @Query("select b from Blog b where b.recommend = true and b.published = true" )
    List<Blog> findTop(Pageable pageable);

    /*这里的?1是指把第一个参数传入？中*/
    @Query("select b from Blog b where b.published = true and (b.title like ?1 or b.content like ?1)")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);

}
