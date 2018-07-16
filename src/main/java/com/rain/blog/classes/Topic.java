package com.rain.blog.classes;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_Topic")
public class Topic {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Topic cannot be empty!") /*这里是给数据库中的表加限制条件 不能为空*/
    private String name;

    /*数据库关系维护*/
    @OneToMany(mappedBy = "topic")
    private List<Blog> blogs;

    @Transient
    private List<Blog> publishedBlogs = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getPublishedBlogs() {
        initPublishedBlogs();
        return publishedBlogs;
    }

    /*public void setPublishedBlogs(List<Blog> publishedBlogs) {
        this.publishedBlogs = publishedBlogs;
    }*/

    private void initPublishedBlogs() {
        List<Blog> source = this.getBlogs();
        for (Blog b : source) {
            if (b.isPublished() && !publishedBlogs.contains(b)) {
                this.publishedBlogs.add(b);
            }
        }
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
