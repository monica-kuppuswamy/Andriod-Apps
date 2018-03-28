package com.example.monic.creatorapp;

import java.io.Serializable;

/**
 * Created by sai shanmukhi on 9/11/2017.
 */

public class Profile implements Serializable {
        String imageTag;
        String name;
        String email;
        String department;
        String emotion;

        public Profile(String imageTag, String name, String email, String department, String emotion) {
            this.imageTag = imageTag;
            this.name = name;
            this.email = email;
            this.department = department;
            this.emotion = emotion;
        }

    @Override
    public String toString() {
        return "Profile{" +
                "imageTag='" + imageTag + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", emotion='" + emotion + '\'' +
                '}';
    }
}
