package com.rain.blog.classes;

public class BlogQuery {
    private String title;
    private Long TopicId;
    private boolean recommend;

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTopicId() {
        return TopicId;
    }

    public void setTopicId(Long topicId) {
        TopicId = topicId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
