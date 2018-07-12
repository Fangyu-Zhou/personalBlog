package com.rain.blog.dao;

import com.rain.blog.classes.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByid(Long id);

    Tag findByName(String name);


}
