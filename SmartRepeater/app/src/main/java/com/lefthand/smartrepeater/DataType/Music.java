package com.lefthand.smartrepeater.DataType;

import javax.xml.datatype.Duration;

/**
 * Created by v-zxiank on 11/14/2016.
 */

public class Music {
    private int mId;
    private String mTitle;
    private String mAuthor;
    private String url;
    private Duration duration;
    private int current;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
