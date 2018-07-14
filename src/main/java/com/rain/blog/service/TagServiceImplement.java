package com.rain.blog.service;

import com.rain.blog.NotFoundException;
import com.rain.blog.classes.Tag;
import com.rain.blog.dao.TagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImplement implements TagService {
    @Autowired
    private TagRepository tagRepository;

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (ids != null && !"".equals(ids)) {
            for (String num : ids.split(",")) {
                list.add(new Long(num));
            }
        }
        return list;
    }

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
    public List<Tag> tagList() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> tagList(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> topTags(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC,"blogs.size"));
        return tagRepository.findTop(pageable);
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
