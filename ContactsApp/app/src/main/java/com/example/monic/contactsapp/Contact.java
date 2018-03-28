package com.example.monic.contactsapp;

import android.net.Uri;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by sai shanmukhi on 9/17/2017.
 */

public class Contact implements Serializable {

    String firstName, lastName, phoneNumber, company, emailID, urlName, localAddress, birthDate, nickName, fbUrl;
    String twitterUrl, skypeId, youTube;
    String imageURI;

    public Uri getImageURI() {
        if(TextUtils.isEmpty(imageURI))
            return null;
        return  Uri.parse(imageURI);
    }

    public void setImageURI(Uri imageURI) {
        if(imageURI!=null)
            this.imageURI = imageURI.toString();
    }

    public String getEmailID() {
        return emailID;
    }

    public String getUrlName() {
        return (TextUtils.isEmpty(urlName))? null: urlName.startsWith("http://")?urlName: "http://"+urlName;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getNickName() {
        return nickName;
    }

    public String getFbUrl() {
        return (TextUtils.isEmpty(fbUrl))? null: fbUrl.startsWith("http://")?fbUrl: "http://"+fbUrl;
    }

    public String getTwitterUrl() {
        return (TextUtils.isEmpty(twitterUrl))? null: twitterUrl.startsWith("http://")?twitterUrl: "http://"+twitterUrl;
    }

    public String getSkypeId() {
        return (TextUtils.isEmpty(skypeId))? null: skypeId.startsWith("http://")?skypeId: "http://"+skypeId;
    }

    public String getYouTube() {
        return (TextUtils.isEmpty(youTube))? null: youTube.startsWith("http://")?youTube: "http://"+youTube;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Contact(String firstName, String lastName, String company, String phoneNumber, String emailID, String urlName, String localAddress, String birthDate, String nickName, String fbUrl, String twitterUrl, String skypeId, String youTube, Uri imageURI) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.emailID = emailID;
        this.urlName = urlName;
        this.localAddress = localAddress;
        this.birthDate = birthDate;
        this.nickName = nickName;
        this.fbUrl = fbUrl;
        this.twitterUrl = twitterUrl;
        this.skypeId = skypeId;
        this.youTube = youTube;
        if(imageURI!=null)
            this.imageURI = imageURI.toString();
    }

    public Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailID='" + emailID + '\'' +
                ", urlName='" + urlName + '\'' +
                ", localAddress='" + localAddress + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", nickName='" + nickName + '\'' +
                ", fbUrl='" + fbUrl + '\'' +
                ", twitterUrl='" + twitterUrl + '\'' +
                ", skypeId='" + skypeId + '\'' +
                ", youTube='" + youTube + '\'' +
                '}';
    }
}

