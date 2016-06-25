package com.wangban.yzbbanban.picture_test_40.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.adapter.MainImageAdapter;
import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.dao.MainPictureDao;
import com.wangban.yzbbanban.picture_test_40.entity.Image;
import com.wangban.yzbbanban.picture_test_40.activity.DetialActivity;

import java.util.*;

/**
 * Created by YZBbanban on 16/6/5.
 */
public class FragmentNew extends Fragment implements Contast, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    /**
     * 控件初始化
     */
    private GridView gvNewImage;
    private ImageButton ibtnNextPage;
    private ImageButton ibtnPrevousPage;
    private MainImageAdapter mainImageAdapter;
    private List<Image> imgs;
    private SwipeRefreshLayout mSwipeLayout;
    private MainPictureDao pic;
    //代表页数
    private int pagePositon = 1;
    /**
     * 回调显示界面
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    if (pagePositon == 1) {
                        pic.findImageGridView(new MainPictureDao.Callback() {

                            @Override
                            public void onImageDownload(List<Image> imgs) {
                                setAdapter(imgs);
                            }
                        }, NEW);
                        stopRefThe();
                        break;
                    } else {
                        stopRefThe();
                        break;
                    }
            }
        }
    };

    /**
     * 关掉线程和刷新，更新界面
     */
    private void stopRefThe() {
        mainImageAdapter.notifyDataSetChanged();
        mSwipeLayout.setRefreshing(false);
        mainImageAdapter.stopThread();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new, container, false);
        initView(view);
        setListener();
        return view;
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        gvNewImage.setOnItemClickListener(this);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        /**
         * 点击下一页：pagePosition为1则为第一页，不需要刷新（有下拉刷新）
         * 若 callback 的返回集合的长度为0，则没获取数据，且将 pagePosition 置为当前页（pagePosition--），
         * 直接 return
         *
         * 若返回集合 size 不为0，则设置适配器，初始化数据
         */
        ibtnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonInvisible();
                mainImageAdapter.stopThread();
                pagePositon++;
                //Log.i(TAG, "onClick: pagePositon: " + pagePositon);
                String pagePath = NEW + "/page/" + pagePositon;
                pic = new MainPictureDao();
                pic.findImageGridView(new MainPictureDao.Callback() {

                    @Override
                    public void onImageDownload(List<Image> imgs) {
                        if (imgs.size() == 0) {
                            pagePositon--;
                            Toast.makeText(getActivity(), "已没有数据", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            setAdapter(imgs);
                            setButtonVisible();
                        }
                    }
                }, pagePath);
               // Log.i(TAG, "onClick: pagePath: " + pagePath);
                mainImageAdapter.notifyDataSetChanged();
                mainImageAdapter.stopThread();

            }
        });
        /**
         * 点击上一页：pagePosition为1则为第一页，不需要刷新（有下拉刷新），且将 pagePosition 置为1
         */
        ibtnPrevousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonInvisible();
                mainImageAdapter.stopThread();
                pagePositon--;
                String pagePath = NEW + "/page/" + pagePositon;
                if (pagePositon <= 0) {
                    pagePath = NEW;
                    pagePositon = 1;
                    Toast.makeText(getActivity(), "已是第一页", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pic.findImageGridView(new MainPictureDao.Callback() {

                        @Override
                        public void onImageDownload(List<Image> imgs) {
                            setAdapter(imgs);
                            setButtonVisible();
                        }
                    }, pagePath);
                    mainImageAdapter.notifyDataSetChanged();
                    mainImageAdapter.stopThread();
                }
            }
        });
        //滑动
        gvNewImage.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        setButtonVisible();
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        setButtonInvisible();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });


    }
    //设置按钮可见
    private void setButtonVisible() {
        ibtnNextPage.setVisibility(View.VISIBLE);
        ibtnPrevousPage.setVisibility(View.VISIBLE);
    }
    //设置按钮不可见
    private void setButtonInvisible() {
        ibtnNextPage.setVisibility(View.INVISIBLE);
        ibtnPrevousPage.setVisibility(View.INVISIBLE);
    }
    //初始化控件以及适配器
    private void initView(View view) {

        gvNewImage = (GridView) view.findViewById(R.id.gv_new);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_new);
        ibtnNextPage = (ImageButton) view.findViewById(R.id.btn_next_page_new);
        ibtnPrevousPage = (ImageButton) view.findViewById(R.id.btn_previous_page_new);
        setButtonVisible();
        pic = new MainPictureDao();
        /**
         * 第一次获取数据
         */
        pic.findImageGridView(new MainPictureDao.Callback() {

            @Override
            public void onImageDownload(List<Image> imgs) {
                setAdapter(imgs);
            }
        }, NEW);

    }
    //刷新构造方法
    private void refearchAdapter() {
    }

    /**
     * 显示界面
     * @param images
     */
    private void setAdapter(List<Image> images) {
        //Log.i(TAG, "setAdapter: "+images.get(0).getPath());
        imgs = images;
        mainImageAdapter = new MainImageAdapter(getActivity(), (ArrayList<Image>) images, gvNewImage);
        gvNewImage.setAdapter(mainImageAdapter);


    }
    //点击界面中的图片，跳转界面
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetialActivity.class);
        intent.putExtra(EXTRA_PATH, imgs.get(position).getSkipPagePath());
        //Log.i(TAG, "onItemClick: path: "+imgs.get(position).getSkipPagePath());
        startActivity(intent);
        setEnterTransition(getExitTransition());
    }
    //关闭线程
    @Override
    public void onDestroyView() {
        mainImageAdapter.stopThread();
        pic.cancleAsyncTask();
        super.onDestroy();
    }
    //发送刷新通知
    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 3000);
    }
}
