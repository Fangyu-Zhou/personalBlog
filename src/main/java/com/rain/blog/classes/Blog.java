package com.rain.blog.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private String imgUrl;
    private String original;
    private Integer views;
    private boolean tips;
    private boolean canShare;
    private boolean canComment;
    private boolean isPublished;
    private boolean isRecommended;

    @Temporal(TemporalType.TIMESTAMP) /*这里需要将时间类型设置成和数据库中兼容的格式*/
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /*数据库关系维护*/
    @ManyToOne
    private Topic topic;
    @ManyToMany(cascade = {CascadeType.PERSIST}) /*此处代表的是新增关联，即blog中含有新的tag则会在tag表中也新增一个tuple*/
    private List<Tag> tags;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")  /*Many一端是关系的维护端, 理解为One一端中对应*/
    private List<Comment> comments;

    public Blog() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isTips() {
        return tips;
    }

    public void setTips(boolean tips) {
        this.tips = tips;
    }

    public boolean isCanShare() {
        return canShare;
    }

    public void setCanShare(boolean canShare) {
        this.canShare = canShare;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", original='" + original + '\'' +
                ", views=" + views +
                ", tips=" + tips +
                ", canShare=" + canShare +
                ", canComment=" + canComment +
                ", isPublished=" + isPublished +
                ", isRecommended=" + isRecommended +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
