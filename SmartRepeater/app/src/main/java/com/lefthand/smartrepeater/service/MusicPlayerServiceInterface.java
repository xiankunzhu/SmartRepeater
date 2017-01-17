package com.lefthand.smartrepeater.service;

import com.lefthand.smartrepeater.model.Music;
import java.util.List;


public interface MusicPlayerServiceInterface {
    void skipToPoint(int point);
    void play(int position);
    void play();
    void pause();
}
