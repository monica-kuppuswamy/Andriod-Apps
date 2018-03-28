package com.example.monic.mysocialapp;

/**
 * Created by monic on 11/18/2017.
 */

public class Friends {
    String dbId;
    String userId;
    String userName;
    String friendId;
    String friendName;
    String friendType;

    public Friends(String userId, String userName, String friendId, String friendName, String friendType) {
        this.userId = userId;
        this.userName = userName;
        this.friendId = friendId;
        this.friendName = friendName;
        this.friendType = friendType;
    }

    public Friends() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendName(String userId) {
        return friendName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendType() {
        return friendType;
    }

    public void setFriendType(String friendType) {
        this.friendType = friendType;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "userId='" + userId + '\'' +
                ", friendId='" + friendId + '\'' +
                ", friendName='" + friendName + '\'' +
                ", friendType='" + friendType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friends friends = (Friends) o;

        return userId.equals(friends.friendId);

    }

    @Override
    public int hashCode() {
        return friendId.hashCode();
    }
}
