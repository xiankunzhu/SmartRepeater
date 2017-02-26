package com.lefthand.smartrepeater.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lefthand.smartrepeater.binder.MusicPlayerServiceBinder;
import com.lefthand.smartrepeater.model.Music;
import com.lefthand.smartrepeater.R;
import com.lefthand.smartrepeater.model.PlayList;
import com.lefthand.smartrepeater.service.MusicPlayerService;
import com.lefthand.smartrepeater.ui.adapter.MyItemRecyclerViewAdapter;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragPlayList extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private final int CHOOSE_AUDIO_FILE = 1;

    private MusicPlayerService mMusicPlayerService;
    private ServiceConnection mServiceConnection;
    private MusicPlayerServiceBinder mServiceBinder;

    private TextView mSongName;
    private TextView mArtistName;
    private ImageView mPlay;
    private ImageView mPlayNext;
    private RelativeLayout mPlayBarContainer;
    private ImageView mAlbumCover;

    private boolean mBound = false;
    private int mState;
    private PlayList allInPlaylist;
    private Music currentItem;
    private int currentPosition;
    private RecyclerView mList;
    MyItemRecyclerViewAdapter mAdapter;
    private TextView mTvEmpty;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragPlayList() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragPlayList newInstance(int columnCount) {
        FragPlayList fragment = new FragPlayList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_frag_play_list, container, false);
        View view = root.findViewById(R.id.list);

        mTvEmpty = (TextView)root.findViewById(R.id.tv_empty);
        FloatingActionButton addBtn = (FloatingActionButton)root.findViewById(R.id.add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, CHOOSE_AUDIO_FILE);
            }
        });

        allInPlaylist = PlayList.loadAllInPlaylist();
        if(allInPlaylist.getPlaylist() == null || allInPlaylist.getPlaylist().isEmpty()){
            mTvEmpty.setVisibility(View.VISIBLE);
        }else{
            mTvEmpty.setVisibility(View.GONE);
        }
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mList = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mList.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new MyItemRecyclerViewAdapter(allInPlaylist.getPlaylist());
            mList.setAdapter(mAdapter);
        }
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHOOSE_AUDIO_FILE){
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                String title = uri.getLastPathSegment();
                int id = (allInPlaylist.getPlaylist() == null || allInPlaylist.getPlaylist().isEmpty()) ? 0: allInPlaylist.getPlaylist().size();
                Music music = new Music(id, title);
                music.setUri(uri);
                allInPlaylist.addMusicToPlaylist(music);
                mAdapter.notifyDataSetChanged();
                mTvEmpty.setVisibility(View.GONE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
