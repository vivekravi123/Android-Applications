package com.example.vivek.inclass_06;

import java.io.Serializable;

/**
 * Created by vivek on 9/26/2016.
 */

public class News implements Serializable{
    String title, image,description,link,largeimage,pubdate;

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getLargeimage() {
        return largeimage;
    }

    public void setLargeimage(String largeimage) {
        this.largeimage = largeimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", largeimage='" + largeimage + '\'' +
                ", pubdate='" + pubdate + '\'' +
                '}';
    }
}
