package com.rain.blog.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_Tag")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    /*数据库关系维护*/
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs;

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
