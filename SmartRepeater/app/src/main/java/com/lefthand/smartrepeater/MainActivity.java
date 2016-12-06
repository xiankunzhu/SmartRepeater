package com.lefthand.smartrepeater;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lefthand.smartrepeater.DataType.Music;
import com.lefthand.smartrepeater.fragment.FragPlayList;
import com.lefthand.smartrepeater.fragment.FragRepeater;
import com.lefthand.smartrepeater.fragment.MyFragmentPagerAdapter;
import com.lefthand.smartrepeater.fragment.dummy.DummyContent;
import com.lefthand.smartrepeater.widget.MyListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragPlayList.OnListFragmentInteractionListener, FragRepeater.OnFragmentInteractionListener, View.OnClickListener {

    private ImageView mBtnPlayList, mBtnRepeater, mBtnDiscover, mBtnMe;
    private ImageView mSelBg;
    private LinearLayout mTab_item_container;
    private FragmentManager mFM = null;

    private FragPlayList mFragPlayList;
    private FragPlayList mFragRepeater;
    private FragPlayList mFragDiscover;
    private FragPlayList mFragMe;

    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

/*
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new MyListAdapter(MainActivity.this));
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init(){

        mTab_item_container = (LinearLayout) findViewById(R.id.tab_item_container);

        mBtnPlayList = (ImageView) findViewById(R.id.tab_btn_playlist);
        mBtnRepeater = (ImageView) findViewById(R.id.tab_btn_repeater);
        mBtnDiscover = (ImageView) findViewById(R.id.tab_btn_discover);
        mBtnMe = (ImageView) findViewById(R.id.tab_btn_me);

        mBtnPlayList.setOnClickListener(this);
        mBtnRepeater.setOnClickListener(this);
        mBtnDiscover.setOnClickListener(this);
        mBtnMe.setOnClickListener(this);

        mSelBg = (ImageView) findViewById(R.id.tab_bg_view);
        ViewGroup.LayoutParams lp = mSelBg.getLayoutParams();
        lp.width = mTab_item_container.getWidth() / 4;

        viewPager = (ViewPager)findViewById(R.id.view_pager);

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        FragPlayList mFragPlayList = FragPlayList.newInstance(1);
        FragRepeater mFragRepeater = FragRepeater.newInstance("para1","para2");
        FragPlayList mFragDiscover = FragPlayList.newInstance(1);
        FragPlayList mFragMe = FragPlayList.newInstance(1);
        fragmentList.add(mFragPlayList);
        fragmentList.add(mFragRepeater);
        fragmentList.add(mFragDiscover);
        fragmentList.add(mFragMe);

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragmentPagerAdapter);

        //changeTo(mFragPlayList);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        ViewGroup.LayoutParams lp = mSelBg.getLayoutParams();
        lp.width = mTab_item_container.getWidth() / 4;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Music item) {
        // interaction with the fragement
    }

    @Override
    public void onFragmentInteraction(Music item) {

    }

    private int mSelectIndex = 0;
    private View last, now;
    View v1, v2;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_btn_playlist:
                last = mTab_item_container.getChildAt(mSelectIndex);
                now = mTab_item_container.getChildAt(0);
                startAnimation(last, now);
                mSelectIndex = 0;

//                changeTo(mFragPlayList);
                break;
            case R.id.tab_btn_repeater:
                last = mTab_item_container.getChildAt(mSelectIndex);
                now = mTab_item_container.getChildAt(1);
                startAnimation(last, now);
                mSelectIndex = 1;

//                changeTo(mFragRepeater);
                break;
            case R.id.tab_btn_discover:
                last = mTab_item_container.getChildAt(mSelectIndex);
                now = mTab_item_container.getChildAt(2);
                startAnimation(last, now);
                mSelectIndex = 2;

//                changeTo(mFragDiscover);

                break;
            case R.id.tab_btn_me:
                last = mTab_item_container.getChildAt(mSelectIndex);
                now = mTab_item_container.getChildAt(3);
                startAnimation(last, now);
                mSelectIndex = 3;

//                changeTo(mFragMe);
                break;
            default:
                break;
        }
        viewPager.setCurrentItem(mSelectIndex);
    }

    private void startAnimation(View last, View now) {
        TranslateAnimation ta = new TranslateAnimation(last.getLeft(), now.getLeft(), 0, 0);
        ta.setDuration(300);
        ta.setFillAfter(true);
        mSelBg.startAnimation(ta);
    }

    private void changeTo(Fragment frag){
        if (null == mFM)
            mFM = this.getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.view_pager, frag);
        ft.commit();
    }


}
