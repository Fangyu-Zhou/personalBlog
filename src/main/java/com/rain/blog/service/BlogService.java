package com.rain.blog.service;

import com.rain.blog.classes.Blog;
import com.rain.blog.classes.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);

    Blog getDetailBlog(Long id);

    Page<Blog> blogList(Pageable pageable, BlogQuery blog);
    Page<Blog> blogList(Pageable pageable);

    /*blogListShow 使用于用户端显示published blog用的*/

    Page<Blog> blogListShow(Pageable pageable, BlogQuery blog);

    Page<Blog> blogListShow(Pageable pageable, Long tagId);

    /*archive用的方法*/
    Map<String, List<Blog>> archiveBlog();

    Long countArchiveBlog();

    /*用于user端显示published的blogs*/
    Page<Blog> publishedBlogList(Pageable pageable);

    /*全局搜索用，String query是查询关键字*/
    Page<Blog> blogList(String query, Pageable pageable);

    List<Blog> listRecommendedBlog(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
    
}
