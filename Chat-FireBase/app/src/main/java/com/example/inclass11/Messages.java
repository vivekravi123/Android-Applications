package com.example.inclass11;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Messages {

    String userName, id, type, message, imageUrl, createdAt;

    public Messages(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("userName", userName);
        result.put("type", type);
        result.put("message", message);
        result.put("createdAt", createdAt);
        result.put("imageUrl",imageUrl);

        return result;
    }
}
