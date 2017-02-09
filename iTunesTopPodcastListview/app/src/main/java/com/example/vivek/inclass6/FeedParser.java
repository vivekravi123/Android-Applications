package com.example.vivek.inclass6;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class FeedParser {

    static ArrayList<Feed> ParseNews (InputStream in) throws XmlPullParserException, IOException {

        ArrayList<Feed> newsList;

        Feed feed = null;
        XmlPullParser parser= XmlPullParserFactory.newInstance().newPullParser();

        parser.setInput(in,"UTF-8");

        newsList= new ArrayList<Feed>();

        int event=parser.getEventType();
        while(event!=XmlPullParser.END_DOCUMENT)
        {
            switch(event)
            {
                case XmlPullParser.START_TAG:
                    if(parser.getName().equals("entry"))
                    {

                        feed=new Feed();

                    }else  if(parser.getName().equals("title")){

                        if(feed!=null) {

                            feed.setTitle(parser.nextText().trim());

                            Log.d("demo","ytitle"+feed.getTitle());
                        }

                    }else  if(parser.getName().equals("summary")){

                        if(feed!=null) {

                            feed.setDescription(parser.nextText().trim());

                            Log.d("demo","Desc"+feed.getDescription());
                        }

                    }else  if(parser.getName().equals("im:releaseDate")){

                        if(feed!=null) {

                            feed.setPubdate(parser.nextText().trim());

                            Log.d("demo","Pubdate"+feed.getPubdate());
                        }

                    }else  if(parser.getName().equals("im:image")){

                        if(feed.getThumbnail()==null||feed.getImage()==null)
                        {

                            if(feed.getImage()==null){

                                feed.setImage(parser.nextText().trim());

                                Log.d("demo","img"+feed.getImage());
                            }else{

                                feed.setThumbnail(parser.nextText().trim());

                                Log.d("demo","Thumb"+feed.getThumbnail());
                            }
                        }


                    }
                    break;

                case XmlPullParser.END_TAG:

                    if(parser.getName().equals("entry"))
                    {
                        newsList.add(feed);
                    }
                    break;

                default:

                    break;
            }

             event=parser.next();
        }


       return newsList;


    }

}
