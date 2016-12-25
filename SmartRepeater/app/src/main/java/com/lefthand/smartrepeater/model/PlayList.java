package com.lefthand.smartrepeater.model;

import java.util.ArrayList;

/**
 * Created by v-zxiank on 12/24/2016.
 */

public class PlayList {

    private String listname;

    public ArrayList<Music> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(ArrayList<Music> playlist) {
        this.playlist = playlist;
    }

    private ArrayList<Music> playlist;

    pubic PlayList(String listname){
        this.listname = listname;
        playlist = new ArrayList<>();
    }

    public static PlayList loadAllInPlaylist(){
        String name = "All in"; // it should be different languages
        PlayList allInPlaylist = new PlayList(name);

        return allInPlaylist;
    }
}
