package com.lefthand.smartrepeater.service;

import com.lefthand.smartrepeater.model.Music;
import java.util.List;


public interface MusicPlayerServiceInterface {
    void addMusicToQueue(Music bean);
    void addMusicToQueue(List<Music> songs);
    void removeMusicFromQueue(Music bean);
    void removeMusicFromQueue(List<Music> songs);
    void skipToPoint(int point);
    void play(int position);
    void play();
    void pause();
}
