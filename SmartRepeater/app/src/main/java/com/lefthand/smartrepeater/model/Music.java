package com.lefthand.smartrepeater.model;

import android.net.Uri;

import java.io.Serializable;

import javax.xml.datatype.Duration;

/**
 * Created by v-zxiank on 11/14/2016.
 */

public class Music implements Serializable{
    private int mId;
    private String mTitle;
    private String mAuthor;// artist
    private Uri uri; // or file path
    private String duration;

    private String mAlbum;
    public Music(int id, String title){
        setId(id);
        setTitle(title);
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album){
        this.mAlbum = album;
    }

}
