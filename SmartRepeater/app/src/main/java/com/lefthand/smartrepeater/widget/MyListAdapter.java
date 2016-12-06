package com.lefthand.smartrepeater.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lefthand.smartrepeater.R;

import com.lefthand.smartrepeater.DataType.Music;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by v-zxiank on 11/8/2016.
 */

public class MyListAdapter extends BaseAdapter {
    private List<Music> musics;
    private Activity activity;

    public MyListAdapter(Activity activity){
        this.activity = activity;
        musics = new ArrayList<Music>();
        musics.add(new Music(1,"test one"));
        musics.add(new Music(2,"test two"));
        musics.add(new Music(3,"test three"));
        musics.add(new Music(4,"test four"));
        musics.add(new Music(5,"test five"));
        musics.add(new Music(6,"test six"));
        musics.add(new Music(7,"test seven"));
        musics.add(new Music(8,"test eight"));
        musics.add(new Music(9,"test nine"));
        musics.add(new Music(10,"test ten"));
    }

    public int getCount() {
        return musics.size();
    }

    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView title = (TextView)convertView.findViewById(R.id.title); // title
        TextView artist = (TextView)convertView.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)convertView.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)convertView.findViewById(R.id.list_image);

        Music m = musics.get(position);
        title.setText(m.getTitle());
        artist.setText(m.getAuthor());
        if(m.getDuration()!=null) {
            duration.setText(m.getDuration().toString());
        }
        return convertView;
    }
}
