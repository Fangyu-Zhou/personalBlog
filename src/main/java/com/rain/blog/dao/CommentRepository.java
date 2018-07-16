package com.rain.blog.dao;

import com.rain.blog.classes.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByid(Long id);

    List<Comment> findByBlogIdAndParentCommentNull(Long BlodId, Sort sort);
}
