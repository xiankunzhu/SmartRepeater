package com.lefthand.smartrepeater.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lefthand.smartrepeater.DataType.Music;
import com.lefthand.smartrepeater.fragment.FragPlayList.OnListFragmentInteractionListener;
import com.lefthand.smartrepeater.fragment.dummy.DummyContent.DummyItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import com.lefthand.smartrepeater.R;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Music> musics;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = musics.get(position);
        holder.mTitle.setText(musics.get(position).getTitle());
        holder.mArtist.setText(musics.get(position).getAuthor());
        if (holder.mItem.getDuration() != null) {
            holder.mDuration.setText(musics.get(position).getDuration().toString());
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return musics.size();
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
