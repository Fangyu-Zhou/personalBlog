package com.rain.blog.dao;

import com.rain.blog.classes.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Topic findByid(Long id);

    Topic findByName(String name);

    @Query("select t from Topic t")
    List<Topic> findTop(Pageable pageable);
}
