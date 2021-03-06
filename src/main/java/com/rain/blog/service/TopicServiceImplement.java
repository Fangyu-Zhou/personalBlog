package com.rain.blog.service;

import com.rain.blog.NotFoundException;
import com.rain.blog.classes.Topic;
import com.rain.blog.dao.TopicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicServiceImplement implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Transactional
    @Override
    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic); /*JpaRepository里有save方法可以用来保存到session*/
    }

    @Transactional
    @Override
    public Topic getTopic(Long id) {
        return topicRepository.findByid(id);
    }

    @Transactional
    @Override
    public Page<Topic> topicList(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Topic> topicList() {
        return topicRepository.findAll();
    }

    @Transactional
    @Override
    public List<Topic> topTopics(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC,"blogs.size"));
        return topicRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Topic updateTopic(Long id, Topic topic) {
        Topic t = topicRepository.findByid(id);
        if (t == null) {
            throw new NotFoundException("Topic not found!");
        }
        /*覆盖操作*/
        BeanUtils.copyProperties(topic, t);

        return topicRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Topic getTopicByName(String name) {
        return topicRepository.findByName(name);
    }
}
