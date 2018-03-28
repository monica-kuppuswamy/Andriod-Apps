package com.example.monic.mysocialapp;

import java.util.Comparator;

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

//    @Override
//    public int compare(Posts posts, Posts t1) {
//        return 0;
//    }
//
//    @Override
//    public int compare(Posts o1, Posts o2) {
//        return (o1.postTime.equals(o2.postTime) && o2.po.equals(o2.overview))?0:-1; }
//
////    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (!Movie.class.isAssignableFrom(obj.getClass())) {
//            return false;
//        }
//        final Movie other = (Movie) obj;
//        if ((this.moviename == null) ? (other.moviename != null) : !this.moviename.equals(other.moviename)) {
//            return false;
//        }
//        if ((this.overview == null) ? (other.overview != null) : !this.overview.equals(other.overview)) {
//            return false;
//        }
//
//        return true;
//    }
}
