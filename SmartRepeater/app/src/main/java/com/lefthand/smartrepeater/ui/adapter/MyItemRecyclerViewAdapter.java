package com.lefthand.smartrepeater.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lefthand.smartrepeater.model.Music;

import java.util.ArrayList;
import java.util.List;
import com.lefthand.smartrepeater.R;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Music> playlist;

    public MyItemRecyclerViewAdapter(List playlist) {
        this.playlist = playlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = playlist.get(position);
        holder.mTitle.setText(playlist.get(position).getTitle());
        holder.mArtist.setText(playlist.get(position).getAuthor());
        if (holder.mItem.getDuration() != null) {
            holder.mDuration.setText(playlist.get(position).getDuration().toString());
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mArtist;
        public final TextView mDuration;
        public final ImageView mThumb_image;
        public Music mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.title);
            mArtist = (TextView) view.findViewById(R.id.artist);
            mDuration = (TextView) view.findViewById(R.id.duration);
            mThumb_image =(ImageView)view.findViewById(R.id.list_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'" + mArtist.getText() + "'" + mDuration.getText();
        }
    }
}
