package com.rain.blog.service;

import com.rain.blog.NotFoundException;
import com.rain.blog.classes.Tag;
import com.rain.blog.dao.TagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImplement implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag); /*JpaRepository里有save方法可以用来保存到session*/
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findByid(id);
    }

    @Transactional
    @Override
    public Page<Tag> tagList(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findByid(id);
        if (t == null) {
            throw new NotFoundException("Tag not found!");
        }
        /*覆盖操作*/
        BeanUtils.copyProperties(tag, t);

        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }
}
