package com.lefthand.smartrepeater.binder;

import android.content.Context;
import android.os.Binder;

import com.lefthand.smartrepeater.service.MusicPlayerService;

public class MusicPlayerServiceBinder extends Binder {
    private MusicPlayerService mService;
    private Context mContext;
    private MusicPlayerServiceBinderCallBack mCallback;

    public MusicPlayerServiceBinder(MusicPlayerService service, Context context){
        mService = service;
        mContext = context;
    }

    public MusicPlayerService getService(MusicPlayerServiceBinderCallBack seekBarTextCallBack){
        mCallback = seekBarTextCallBack;
        return mService;
    }

    public void setTotalTime(String time){
        if (mContext != null && mCallback != null)
            mCallback.setTotalTime(time);
    }

    public void setCurrentTime(String time){
        if (mContext != null && mCallback != null)
            mCallback.setCurrentTime(time);
    }

    public void setMusicTitle(String title){
        if (mContext != null && mCallback != null)
            mCallback.setMusicTitle(title);
    }

    public void setAlbumCover(int songId, int albumId){
        if (mContext != null && mCallback != null)
            mCallback.setAlbumCover(songId, albumId);
    }

    public void setMusicAlbum(String album){
        if (mContext != null && mCallback != null)
            mCallback.setMusicAlbum(album);
    }

    public void setMusicArtist(String artist){
        if (mContext != null && mCallback != null)
            mCallback.setMusicArtist(artist);
    }

    public void play(){
        if (mContext != null && mCallback != null)
            mCallback.setImagePlay();
    }

    public void pause(){
        if (mContext != null && mCallback != null)
            mCallback.setImagePaused();
    }

}
