package com.oldi.football.ui.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.oldi.football.Connection;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class NewsInfo {
    ArrayList<String> descriptions;
    ArrayList<String> headers;
    ArrayList<String> bitmaps;
    ArrayList<String> vids;
    ArrayList<String> autors;
    ArrayList<String> lastnames;
    ArrayList<String> dates;

    public NewsInfo() {
        descriptions = new ArrayList<>();
        headers = new ArrayList<>();
        bitmaps = new ArrayList<>();
        vids = new ArrayList<>();
        autors = new ArrayList<>();
        lastnames = new ArrayList<>();
        dates = new ArrayList<>();
    }

    public void setInfo(String desc, String header, String url, String vid, String autor, String lastname, String date) {
        descriptions.add(desc);
        headers.add(header);
        vids.add(vid);
        autors.add(autor);
        lastnames.add(lastname);
        dates.add(date);
        bitmaps.add(Connection.imagesUrl + url);
    }


    public String getDescription(int index) {
        return descriptions.get(index);
    }

    public String getHeader(int index) {
        return headers.get(index);
    }

    public String getBitmap(int index) {
        return bitmaps.get(index);
    }

    public int getLength() {
        return descriptions.size();
    }
}
