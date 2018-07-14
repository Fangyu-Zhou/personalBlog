package com.rain.blog.service;

import com.rain.blog.classes.Blog;
import com.rain.blog.classes.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    Blog getBlog(Long id);

    Page<Blog> blogList(Pageable pageable, BlogQuery blog);

    Page<Blog> blogList(Pageable pageable);

    List<Blog> listRecommendedBlog(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
    
}
