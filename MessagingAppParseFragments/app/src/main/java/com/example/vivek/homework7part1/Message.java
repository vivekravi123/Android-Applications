package com.example.vivek.homework7part1;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Message implements Parcelable {

    String userName, id, type, message, imageUrl, createdAt, fromname, fromuser, toname, touser,read, date;

    public Message(String id, String type, String message, String read, String date, String touser, String fromname, String toname) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.read = read;
        this.date = date;
        this.touser=touser;
        this.fromname= fromname;
        this.toname= toname;
    }

   /* public Message(String userName, String id, String type, String message, String imageUrl, String createdAt, String fromname, String fromuser, String toname, String touser, String read, String date) {
        this.userName = userName;
        this.id = id;
        this.type = type;
        this.message = message;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.fromname = fromname;
        this.fromuser = fromuser;
        this.toname = toname;
        this.touser = touser;
        this.read = read;
        this.date = date;
    }
*/
    public Message(){

    }

    protected Message(Parcel in) {
        userName = in.readString();
        id = in.readString();
        type = in.readString();
        message = in.readString();
        imageUrl = in.readString();
        createdAt = in.readString();
        fromname = in.readString();
        fromuser = in.readString();
        toname = in.readString();
        touser = in.readString();
        read = in.readString();
        date = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

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
        result.put("read", read);
        result.put("date", date);
        result.put("touser",touser);
        result.put("fromname", fromname);
        result.put("toname", toname);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(fromuser);
        parcel.writeString(touser);
        parcel.writeString(date);
        parcel.writeString(message);

    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getFromuser() {
        return fromuser;
    }

    public void setFromuser(String fromuser) {
        this.fromuser = fromuser;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Creator<Message> getCREATOR() {
        return CREATOR;
    }
}
