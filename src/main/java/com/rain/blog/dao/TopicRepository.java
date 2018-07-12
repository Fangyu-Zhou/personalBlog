package com.rain.blog.dao;

import com.rain.blog.classes.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Topic findByid(Long id);
}
