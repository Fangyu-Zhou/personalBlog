package com.rain.blog.service;

import com.rain.blog.classes.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    /*保存topic的方法*/
    Tag saveTag(Tag tag);

    /*获得topic的方法*/
    Tag getTag(Long id);

    /*分页查询应当返回Page类型的变量*/
    Page<Tag> tagList(Pageable pageable);

    List<Tag> tagList();

    /*前端传回来的tagid的集合是一个String类型的变量*/
    List<Tag> tagList(String ids);

    /*更新topic的方法*/
    Tag updateTag(Long id, Tag tag);

    /*删除topic的方法*/
    void deleteTag(Long id);

    /*通过名称返回Topic*/
    Tag getTagByName(String name);
}
