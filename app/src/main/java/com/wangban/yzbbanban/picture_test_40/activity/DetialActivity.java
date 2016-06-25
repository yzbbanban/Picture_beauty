package com.wangban.yzbbanban.picture_test_40.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.adapter.DetialImageAdapter;
import com.wangban.yzbbanban.picture_test_40.app.ImgApplication;
import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.dao.DetialPictureDao;
import com.wangban.yzbbanban.picture_test_40.entity.DetialImage;

import java.util.ArrayList;
import java.util.List;


public class DetialActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, Contast {
    private GridView gvDetial;
    private Button btnBack;
    private TextView tvName;
    private DetialPictureDao detialPictureDao = new DetialPictureDao();
    private String detialPath;
    private ImgApplication app;
    private ArrayList<DetialImage> imgs;
    private DetialImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detial);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        detialPictureDao.findDetilImageGridView(new DetialPictureDao.Callback() {


            @Override
            public void onImageDownload(List<DetialImage> imgs) {
                DetialActivity.this.imgs = (ArrayList<DetialImage>) imgs;
                setAdapter(imgs);
            }
        }, detialPath);


    }

    @Override
    protected void onDestroy() {
        adapter.stopThread();
        detialPictureDao.cancleAsyncTask();
        super.onDestroy();
    }

    private void setListener() {
        gvDetial.setOnItemClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void setAdapter(List<DetialImage> images) {
        imgs= (ArrayList<DetialImage>) images;
        app= (ImgApplication) getApplication();
        app.setImageData(imgs);
        tvName.setText(images.get(0).getDetTitle());
        adapter = new DetialImageAdapter(this, (ArrayList<DetialImage>) images, gvDetial);
        gvDetial.setAdapter(adapter);

    }

    private void initView() {
        gvDetial = (GridView) findViewById(R.id.gv_detial);
        btnBack = (Button) findViewById(R.id.btn_back);
        tvName = (TextView) findViewById(R.id.tv_beauty_pic_name);

        Intent intent = getIntent();
        detialPath = intent.getStringExtra(EXTRA_PATH);
        //Log.i(TAG, "initView: " + detialPath);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,ImageActivity.class);
        app.setPosition(position);
        intent.putExtra(EXTRA_IMAGE_PATH, imgs.get(position).getHrefPath());
        intent.putExtra(EXTRA_IMAGE_POSITION, position);
        startActivity(intent);
        overridePendingTransition(R.anim.fade, R.anim.hold);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
               // Toast.makeText(this, "return", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
