package com.lefthand.smartrepeater.ui.fragment;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
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

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FragPlayList extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

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
    private List<Music> allInPlaylist;
    private Music currentItem;
    private int currentPosition;

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

        allInPlaylist = PlayList.loadAllInPlaylist().getPlaylist();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(allInPlaylist, mListener));
        }
        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Music item);
    }
}
