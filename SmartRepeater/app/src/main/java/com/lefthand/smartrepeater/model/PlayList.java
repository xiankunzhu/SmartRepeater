package com.lefthand.smartrepeater.model;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by v-zxiank on 12/24/2016.
 *  there is only one list all in at first
 *  maybe support customized list
 *  and here have static factory function to keep it singleton
 */

public class PlayList {
    public final static String PATH = "/sdcard/smartRepeater/";
    public final static String FILE_EXT = ".txt";
    public final static String ALL_IN_PLAY_LIST_TAG = "allInPlaylist";

    public final static String LOG_TAG = PlayList.class.getSimpleName();

    // only one play list so far
    private static PlayList allInPlaylist;

    private String listTag;
    private ArrayList<Music> list;

    public String getListTag() {
        return listTag;
    }

    public void setListTag(String listTag) {
        this.listTag = listTag;
    }

    public ArrayList<Music> getPlaylist() {
        return list;
    }

    public void setPlaylist(ArrayList<Music> playlist) {
        this.list = playlist;
    }


    private PlayList(String tag){
        this.listTag = tag;
        list = new ArrayList<>();
    }

    public static PlayList loadAllInPlaylist(){
        if(allInPlaylist == null) {
            String tag = new String(ALL_IN_PLAY_LIST_TAG); // it should be different languages
            allInPlaylist = new PlayList(tag);
            try{
                File path = new File(PATH);
                File file = new File(PATH + allInPlaylist.listTag + FILE_EXT);
                if(!path.exists()){
                    path.mkdir();
                }
                if(!file.exists()){
                    file.createNewFile();
                }
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                allInPlaylist.list = (ArrayList<Music>)objectInputStream.readObject();
                Log.d(LOG_TAG, "success to load play list");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return allInPlaylist;
    }

    //Serializable
    public boolean storeListToFile(){
        List<Music> stores = new ArrayList<>();

        try {
            File path = new File(PATH);
            File file = new File(PATH + listTag + FILE_EXT);
            if (!path.exists()){
                path.mkdir();
            }
            // if exist, delete and create
            if (file.exists()){
                file.delete();
                file = new File(PATH + listTag + FILE_EXT);
            }else {
                file.createNewFile();
            }

            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            stores.addAll(list);
            oos.writeObject(stores);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        Log.d(LOG_TAG, "success to do play list serializable");
        return true;
    }

    public void addMusicToPlaylist(Music music) {
        list.add(music);
        storeListToFile();
        Log.d(LOG_TAG, "add one music to play list");
    }

    public void addMusicToPlaylist(List<Music> musics) {
        list.addAll(musics);
        storeListToFile();
    }

    public void removeMusicFromPlaylist(Music music) {
        list.remove(music);
        storeListToFile();
        Log.d(LOG_TAG, "remove one music to play list");
    }

    public void removeMusicFromPlaylist(List<Music> musics) {
        list.removeAll(musics);
        storeListToFile();
    }

    public void clearPlaylist(){
        list.clear();
        storeListToFile();

    }
}
