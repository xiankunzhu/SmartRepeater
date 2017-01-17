package com.lefthand.smartrepeater.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.lefthand.smartrepeater.receiver.HeadPhoneBroadcastReceiver;
import com.lefthand.smartrepeater.binder.MusicPlayerServiceBinder;
import com.lefthand.smartrepeater.model.Music;
import com.lefthand.smartrepeater.model.PlayList;
import com.lefthand.smartrepeater.util.CommonUtils;
import java.io.IOException;
import java.util.List;


public class MusicPlayerService extends Service implements MusicPlayerServiceInterface{

    public static final String MUSIC_PLAYER_SERVICE_NAME = "com.lefthand.smartrepeater.service.MusicPlayerService";
    public static final String ACTION_PLAY = "com.lefthand.smartrepeater.PLAY";

    private final String LOG_TAG = MusicPlayerService.class.getSimpleName();

    public final static int PAUSED = 0;
    public final static int PLAYING = 1;
    public final static String ACTIVITY_INDENTIFY = "ACTIVITY_INDENTIFY";


    public final static String PlayingNumber = "PlayingNumber"; // 播放的序号

    public final static int PLAY_MUSIC_NOTIFICATION_ID = 1;



    private int state = PAUSED;

    private Handler mSeekBarHandler;
    private AsyncTask<Integer, Void, Void> mSeekBarTracker;

    private MusicPlayerServiceBinder mBinder;
    private List<Music> list;
    private PlayList playList;
    private int mPlayPosition = 0;

    private MediaPlayer mMediaPlayer;

    private HeadPhoneBroadcastReceiver mHeadPhoneBroadcastReceiver;

    @Override
    public void onCreate() {
        Log.i(LOG_TAG, "onCreate");
        super.onCreate();
//        registerBroadcastReceiver();
        playList = PlayList.loadAllInPlaylist();
        list = playList.getPlaylist();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand");
        if (intent.getAction().equals(ACTION_PLAY)) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playNext();
                }
            });
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mBinder.setTotalTime(list.get(mPlayPosition).getDuration().toString());
                    mBinder.setMusicTitle(list.get(mPlayPosition).getTitle());
                    mBinder.setMusicAlbum(list.get(mPlayPosition).getAlbum());
                    mBinder.setMusicArtist(list.get(mPlayPosition).getAuthor());

                    startSeekBarTracker(mMediaPlayer.getDuration());
                    play();
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // state Error, need reset to idle
                    return true;
                }
            });

            mSeekBarHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        case 0:
                            int ms = (int) msg.obj;
                            if (mBinder != null || ms == 0){
                                mBinder.setCurrentTime(CommonUtils.timeFormatMs2Str(ms));
                            }
                            break;
                        case 1:
                            Log.d(LOG_TAG, "handle message fail match.");
                            break;
                    }
                }
            };
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    public IBinder onBind(Intent intent) {

        mBinder = new MusicPlayerServiceBinder(this, this);
        return mBinder;

    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mSeekBarTracker != null)
            mSeekBarTracker.cancel(true);
        mSeekBarTracker = null;

        Log.i(LOG_TAG, "onUnbind with state:　" + ((state == PLAYING) ? "PLAYING" : "PAUSED" ));
        return true;
    }

    @Override
    public void onDestroy() {

        Log.i(LOG_TAG, " onDestroy");
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        super.onDestroy();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    public int getPlayingPosition(){
        if (mMediaPlayer != null)
            return mMediaPlayer.getCurrentPosition();
        else
            return 0;
    }


    public int getCurrentPosition(){
        return mPlayPosition;
    }


    @Override
    public void skipToPoint(int point) {
        mMediaPlayer.seekTo(point);
    }

    @Override
    public void play() {

        mMediaPlayer.start();
        if (mBinder != null)
            mBinder.play();

    }

    @Override
    public void pause() {

        mMediaPlayer.pause();
        if (mBinder != null)
            mBinder.pause();

    }

    public boolean registerBroadcastReceiver(){
        // register the BroadcastReceiver
        mHeadPhoneBroadcastReceiver = new HeadPhoneBroadcastReceiver();
        registerReceiver(mHeadPhoneBroadcastReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
        mHeadPhoneBroadcastReceiver.registerMusicPlayerService(this);
        return true;
    }


    public int changeState(){
        switch (state){
            case PLAYING:
                pause();
                break;
            case PAUSED:
                play();
                break;
        }
        return state;
    }



    public int getState(){
        return state;
    }

    @Override
    public void play(int position) {
        this.mPlayPosition = position;

        playFetched(list.get(position).getUri().getPath());
    }

    public synchronized void playNext(){
        if ((mPlayPosition +1) == list.size())
            mPlayPosition = 0;
        else
            mPlayPosition++;
        playFetched(list.get(mPlayPosition).getUri().getPath());

    }

    public synchronized void playPrevious(){
        if ((mPlayPosition -1) == -1)
            mPlayPosition = list.size()-1;
        else
            mPlayPosition--;
        playFetched(list.get(mPlayPosition).getUri().getPath());

    }

    // play music selected
    public synchronized void playFetched(String path) {
        playSetting(path);
        playList.storeListToFile();

    }

    public void playSetting(String path){
        try{

            if (mMediaPlayer.isPlaying()){
                // 切换状态，切换图标
                changeState();
            }
            if (mSeekBarTracker != null)
                mSeekBarTracker.cancel(true);
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepareAsync();

        } catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (SecurityException e){
            e.printStackTrace();
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 追踪 MediaPlayer 的播放状态，更新 Activity 的 SeekBar
     * @param duration
     */
    public void startSeekBarTracker (int duration) {
        if (mSeekBarTracker != null){
            mSeekBarTracker.cancel(true);
        }
        mSeekBarTracker = null;
        mSeekBarTracker = new AsyncTask<Integer, Void, Void>() {

            int ms = 0;
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    while(mMediaPlayer != null && mMediaPlayer.getCurrentPosition() < params[0]){
                        if (state == PLAYING){
                            ms = mMediaPlayer.getCurrentPosition();
//                            mSeekBar.setProgress(ms);
                            // send to ui thread
                            mSeekBarHandler.obtainMessage(0, ms).sendToTarget();
                        }
                        Thread.sleep(100);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                return null;
            }
        };
        mSeekBarTracker.execute(duration);
    }
}
