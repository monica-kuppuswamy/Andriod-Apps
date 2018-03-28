package com.example.monic.mysocialapp;

/**
 * Created by monic on 11/18/2017.
 */

public class Posts {
    String userId;
    String id;
    String postTime;
    String postContent;

    public Posts(String userId, String id, String postTime, String postContent) {
        this.userId = userId;
        this.id = id;
        this.postTime = postTime;
        this.postContent = postContent;
    }

    public Posts() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
