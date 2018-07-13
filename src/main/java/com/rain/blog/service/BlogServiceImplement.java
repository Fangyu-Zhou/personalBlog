package com.rain.blog.service;

import com.rain.blog.NotFoundException;
import com.rain.blog.classes.Blog;
import com.rain.blog.classes.BlogQuery;
import com.rain.blog.classes.Topic;
import com.rain.blog.dao.BlogRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
public class BlogServiceImplement implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findByid(id);
    }

    @Override
    public Page<Blog> blogList(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            /*criteriaQuery变量相当于一个条件容器，将需要查询的条件放入该变量中，cB变量是具体设置条件表达式的*/
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (blog.getTitle() != null && "".equals(blog.getTitle())) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTopicId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Topic>get("topic").get("id"), blog.getTopicId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("isRecommended"), blog.isRecommend()));
                }

                /*查询语句SQL*/
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogRepository.findByid(id);
        if (blog1 == null) {
            throw new NotFoundException("Blog not Found this time!");
        }

        BeanUtils.copyProperties(blog1, blog);
        return blogRepository.save(blog1);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);

    }
}
