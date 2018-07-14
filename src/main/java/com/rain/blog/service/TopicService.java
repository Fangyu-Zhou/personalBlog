package com.rain.blog.service;

import com.rain.blog.classes.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicService {
    /*保存topic的方法*/
    Topic saveTopic(Topic topic);

    /*获得topic的方法*/
    Topic getTopic(Long id);

    /*分页查询应当返回Page类型的变量*/
    Page<Topic> topicList(Pageable pageable);

    /*返回List Topic给blog分页查询时用*/
    List<Topic> topicList();

    /*这里是首页用的前几个Topic显示*/
    List<Topic> topTopics(Integer size);

    /*更新topic的方法*/
    Topic updateTopic(Long id, Topic topic);

    /*删除topic的方法*/
    void deleteTopic(Long id);

    /*通过名称返回Topic*/
    Topic getTopicByName(String name);

}
