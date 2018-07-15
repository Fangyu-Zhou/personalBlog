package com.rain.blog.service;

import com.rain.blog.classes.Blog;
import com.rain.blog.classes.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    Blog getBlog(Long id);

    Blog getDetailBlog(Long id);

    Page<Blog> blogList(Pageable pageable, BlogQuery blog);

    Page<Blog> blogList(Pageable pageable);

    /*用于user端显示published的blogs*/
    Page<Blog> publishedBlogList(Pageable pageable);

    /*全局搜索用，String query是查询关键字*/
    Page<Blog> blogList(String query, Pageable pageable);

    List<Blog> listRecommendedBlog(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
    
}
