package com.lefthand.smartrepeater.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.TextView;

import com.lefthand.smartrepeater.R;
import com.lefthand.smartrepeater.model.Music;
import com.lefthand.smartrepeater.ui.fragment.FragPlayList;
import com.lefthand.smartrepeater.ui.fragment.FragRepeater;
import com.lefthand.smartrepeater.ui.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragPlayList.OnListFragmentInteractionListener, FragRepeater.OnFragmentInteractionListener, View.OnClickListener {

    private TextView mBtnPlayList, mBtnRepeater, mBtnDiscover, mBtnMe;
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

        mBtnPlayList = (TextView) findViewById(R.id.tab_btn_playlist);
        mBtnRepeater = (TextView) findViewById(R.id.tab_btn_repeater);
        mBtnDiscover = (TextView) findViewById(R.id.tab_btn_discover);
        mBtnMe = (TextView) findViewById(R.id.tab_btn_me);

        mBtnPlayList.setOnClickListener(this);
        mBtnRepeater.setOnClickListener(this);
        mBtnDiscover.setOnClickListener(this);
        mBtnMe.setOnClickListener(this);

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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (mSelectIndex) {
                    case 0:
                        mBtnPlayList.setTypeface(null,Typeface.NORMAL);
                        break;
                    case 1:
                        mBtnRepeater.setTypeface(null,Typeface.NORMAL);
                        break;
                    case 2:
                        mBtnDiscover.setTypeface(null,Typeface.NORMAL);
                        break;
                    case 3:
                        mBtnMe.setTypeface(null,Typeface.NORMAL);
                        break;
                }
                switch (position) {
                    case 0:
                        mSelectIndex = 0;
                        mBtnPlayList.setTypeface(null,Typeface.BOLD);
                        break;
                    case 1:
                        mSelectIndex = 1;
                        mBtnRepeater.setTypeface(null,Typeface.BOLD);
                        break;
                    case 2:
                        mSelectIndex = 2;
                        mBtnDiscover.setTypeface(null,Typeface.BOLD);
                        break;
                    case 3:
                        mSelectIndex = 3;
                        mBtnMe.setTypeface(null,Typeface.BOLD);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
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
        //set older one to normal
        switch (mSelectIndex) {
            case 0:
                mBtnPlayList.setTypeface(null,Typeface.NORMAL);
                break;
            case 1:
                mBtnRepeater.setTypeface(null,Typeface.NORMAL);
                break;
            case 2:
                mBtnDiscover.setTypeface(null,Typeface.NORMAL);
                break;
            case 3:
                mBtnMe.setTypeface(null,Typeface.NORMAL);
                break;
        }
        switch (v.getId()) {
            case R.id.tab_btn_playlist:
                mSelectIndex = 0;
                mBtnPlayList.setTypeface(null,Typeface.BOLD);
                break;
            case R.id.tab_btn_repeater:
                mSelectIndex = 1;
                mBtnRepeater.setTypeface(null,Typeface.BOLD);
                break;
            case R.id.tab_btn_discover:
                mSelectIndex = 2;
                mBtnDiscover.setTypeface(null,Typeface.BOLD);
                break;
            case R.id.tab_btn_me:
                mSelectIndex = 3;
                mBtnMe.setTypeface(null,Typeface.NORMAL);
                break;
            default:
                break;
        }
        viewPager.setCurrentItem(mSelectIndex);
    }

}
