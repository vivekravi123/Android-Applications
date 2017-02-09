package com.example.vivek.inclass6;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Comparator;


public class Feed implements Serializable,Comparator<Feed> {
    String title,description,link, pubdate, thumbnail, image;
    Bitmap thumbnail_bitmap;

    public Bitmap getThumbnail_bitmap() {
        return thumbnail_bitmap;
    }

    public void setThumbnail_bitmap(Bitmap thumbnail_bitmap) {
        this.thumbnail_bitmap = thumbnail_bitmap;
    }

    public Bitmap getImage_bitmap() {
        return image_bitmap;
    }

    public void setImage_bitmap(Bitmap image_bitmap) {
        this.image_bitmap = image_bitmap;
    }

    Bitmap image_bitmap;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate)
    {
        String dateFormatted = "";

        dateFormatted = dateFormatted + pubdate.substring(5,7) + "/" + pubdate.substring(8,10)+ "/"+pubdate.substring(0,4);

        if(Integer.parseInt(pubdate.substring(11,13))>12)
        {
            Integer i=Integer.parseInt(pubdate.substring(11,13))-12;
            dateFormatted +=" "+i.toString()+":"+pubdate.substring(14,16)+" PM";

        }
        else
        {
            dateFormatted +=" "+Integer.parseInt(pubdate.substring(11,13))+":"+pubdate.substring(14,16)+" AM";
        }


        this.pubdate = dateFormatted;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int compare(Feed o1, Feed o2) {
        return 0;
    }
}
