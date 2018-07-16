package com.rain.blog.service;

import com.rain.blog.NotFoundException;
import com.rain.blog.classes.Blog;
import com.rain.blog.classes.BlogQuery;
import com.rain.blog.classes.Topic;
import com.rain.blog.dao.BlogRepository;
import com.rain.blog.util.MarkdownUtils;
import com.rain.blog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.transaction.TransactionScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class BlogServiceImplement implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findByid(id);
    }


    @Transactional
    @Override
    public Blog getDetailBlog(Long id) {
        Blog blog = blogRepository.findByid(id);
        if (blog == null) {
            throw new NotFoundException("blog not found");
        }
        /*这里有一个小问题，我们不希望改变原数据库中的内容，即原数据库中应该仍然保持markdown文本格式而不是html格式*/
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        content = MarkdownUtils.markdownToHtmlExtensions(content);
//        System.out.println(content);
        b.setContent(content);
        blogRepository.updateViews(id);
        return b;
    }

    @Transactional
    @Override
    public Page<Blog> blogList(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            /*criteriaQuery变量相当于一个条件容器，将需要查询的条件放入该变量中，cB变量是具体设置条件表达式的*/
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+ blog.getTitle()+"%"));
                }
                if (blog.getTopicId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Topic>get("topic").get("id"), blog.getTopicId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }

                /*查询语句SQL*/
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Page<Blog> blogListShow(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            /*criteriaQuery变量相当于一个条件容器，将需要查询的条件放入该变量中，cB变量是具体设置条件表达式的*/
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+ blog.getTitle()+"%"));
                }
                if (blog.getTopicId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Topic>get("topic").get("id"), blog.getTopicId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                predicates.add(criteriaBuilder.equal(root.<Boolean>get("published"), true));

                /*查询语句SQL*/
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Page<Blog> blogListShow(Pageable pageable, Long tagId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.<Boolean>get("published"), true));
                predicates.add(criteriaBuilder.equal(join.get("id"), tagId));

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Page<Blog> blogList(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Page<Blog> publishedBlogList(Pageable pageable) {
        return blogRepository.findAllPublished(pageable);
    }

    @Transactional
    @Override
    public Page<Blog> blogList(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Transactional
    @Override
    public List<Blog> listRecommendedBlog(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "updateTime"));
        return blogRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        /*新增一条blog需要给一些成员变量的初始值*/
        /*新增和修改blog需要区分开*/
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogRepository.findByid(id);
        if (blog1 == null) {
            throw new NotFoundException("Blog not Found this time!");
        }

        BeanUtils.copyProperties(blog, blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return blogRepository.save(blog1);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);

    }
}
