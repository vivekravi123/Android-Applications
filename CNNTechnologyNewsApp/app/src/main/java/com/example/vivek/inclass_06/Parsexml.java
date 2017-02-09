package com.example.vivek.inclass_06;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by vivek on 9/26/2016.
 */
public class Parsexml {

    public static class Parsexmlnews extends DefaultHandler
    {
        static ArrayList<News> newslist ;
        int flag=0;
        StringBuilder hold;
        String url;
        String largeurl;
        int maxhight=0;
        double maxwidth=0;
        double h,w;

        News news ;
        public static ArrayList<News> getNewslist() {
            return newslist;
        }


        public static ArrayList<News> Parsemethod(InputStream in) throws SAXException, IOException {
            Parsexmlnews px = new Parsexmlnews();
            Xml.parse(in,Xml.Encoding.UTF_8,px);

            return Parsexmlnews.getNewslist();
        }
        @Override
        public void startDocument() throws SAXException {

            super.startDocument();
            newslist = new ArrayList<News>();
            hold=new StringBuilder();

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if(localName.equals("item"))
            {
                newslist.add(news);

                Log.d("demo",hold.toString());

            }
            else if(localName.equals("title"))
            {
               // news = new News();
                if(flag==1) {
                    news.setTitle(hold.toString());

                    Log.d("demo", hold.toString());
                }

            }
            else if(localName.equals("description"))
            {
                if(flag==1) {
                    news.setDescription(hold.toString());
                }
            }
            else if(localName.equals("link"))
            {
                if(flag==1) {
                    news.setLink(hold.toString());
                }
            }
            else if(localName.equals("pubDate"))
            {
                if(flag==1) {
                    news.setPubdate(hold.toString());
                    Log.d("pubdate", hold.toString());
                }
            }
            else if(localName.equals("content")){
                if(h==w){

                    news.setImage(url);
                    Log.d("url",url);
                }
                if(w>=maxwidth)
                {
                    maxwidth=w;
                    news.setLargeimage(largeurl);

                }
            }



            hold.setLength(0);

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("item"))
            {
                news = new News();
                flag=1;
                Log.d("inside image","imageurl");


            }
            else if(localName.equals("group")){

                Log.d("inside media","hii");
                //String media=attributes.getValue("content");
                //Log.d("media",media);

            }
            else if(localName.equals("content")){
                h = Double.parseDouble(attributes.getValue("height"));
                w = Double.parseDouble(attributes.getValue("width"));

                url = attributes.getValue("url");
                largeurl=attributes.getValue("url");
                Log.d("demo-H"," " + h +" " + w);
            }



        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            hold.append(ch,start,length);
           // hold.append("hold");
        }
    }

}
