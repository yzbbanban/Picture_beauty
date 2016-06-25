package com.wangban.yzbbanban.picture_test_40.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.adapter.ViewPagerAdapter;
import com.wangban.yzbbanban.picture_test_40.app.ImgApplication;
import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.dao.DetialPictureDao;
import com.wangban.yzbbanban.picture_test_40.entity.DetialImage;
import com.wangban.yzbbanban.picture_test_40.ui.TouchImageView;

import java.util.*;

public class ImageActivity extends Activity implements Contast {
    //init View class

    private String url;

    //viewPager
    private ViewPager viewPager;

    private TouchImageView ivSingleimg;
    private ArrayList<DetialImage> images;
    private ImgApplication app;

    private ViewPagerAdapter adapter;
    private int currentPosition;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        //Log.w(TAG, "onCreate:hello ");
        initView();
        initData();
        setListener();
    }


    private void setListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initData() {

        //TODO

        adapter = new ViewPagerAdapter(ImageActivity.this,images,viewPager);
        viewPager.setAdapter(adapter);
        position = getIntent().getIntExtra(EXTRA_IMAGE_POSITION, 0);

        //viewPager.setCurrentItem(currentPosition);
        viewPager.setCurrentItem(position);
       // adapter.setPositon(position);

        //Log.i(TAG, "initData: position: "+currentPosition);
        Log.i(TAG, "initData: position: "+position);
       // Log.i(TAG, "initData: "+this.getCacheDir().getAbsolutePath());
    }

    private void initView() {
        app = (ImgApplication) getApplication();
        images = app.getImageData();
        viewPager = (ViewPager) findViewById(R.id.pager);
        //Log.i(TAG, "initView: "+images.get(0).getHrefPath());

    }

    @Override
    protected void onDestroy() {
        adapter.onPause();
        adapter.stopThread();
        super.onDestroy();
    }

}
