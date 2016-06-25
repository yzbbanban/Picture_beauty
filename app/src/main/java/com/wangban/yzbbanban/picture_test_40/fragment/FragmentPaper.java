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
import android.widget.TextView;
import android.widget.Toast;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.activity.DetialActivity;
import com.wangban.yzbbanban.picture_test_40.adapter.MainImageAdapter;
import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.dao.MainPictureDao;
import com.wangban.yzbbanban.picture_test_40.entity.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YZBbanban on 16/6/5.
 */
public class FragmentPaper extends Fragment implements Contast, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private GridView gvPaperImage;
    private MainImageAdapter mainImageAdapter;
    private ImageButton ibtnNextPage;
    private ImageButton ibtnPrevousPage;
    private int pagePositon = 1;

    private List<Image> imgs;
    private SwipeRefreshLayout mSwipeLayout;
    private  MainPictureDao pic;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    MainPictureDao pic = new MainPictureDao();

                    pic.findImageGridView(new MainPictureDao.Callback() {

                        @Override
                        public void onImageDownload(List<Image> imgs) {
                            setAdapter(imgs);
                        }
                    }, PAPER);
                    mainImageAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    mainImageAdapter.stopThread();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paper, container, false);
        initView(view);
        setListener();
        return view;
    }

    private void setListener() {
        gvPaperImage.setOnItemClickListener(this);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        //点击下一页
        ibtnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonInvisible();
                mainImageAdapter.stopThread();
                pagePositon++;
                //Log.i(TAG, "onClick: pagePositon: " + pagePositon);
                String pagePath = PAPER + "/page/" + pagePositon;
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
                //Log.i(TAG, "onClick: pagePath: " + pagePath);
                mainImageAdapter.notifyDataSetChanged();
                mainImageAdapter.stopThread();

            }
        });
        //点击上一页
        ibtnPrevousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonInvisible();
                mainImageAdapter.stopThread();
                pagePositon--;
                String pagePath = PAPER + "/page/" + pagePositon;
                if (pagePositon <= 0) {
                    pagePath = PAPER;
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
        gvPaperImage.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void initView(View view) {

        gvPaperImage = (GridView) view.findViewById(R.id.gv_paper);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_paper);
        ibtnNextPage = (ImageButton) view.findViewById(R.id.btn_next_page_paper);
        ibtnPrevousPage = (ImageButton) view.findViewById(R.id.btn_previous_page_paper);

        pic = new MainPictureDao();

        pic.findImageGridView(new MainPictureDao.Callback() {

            @Override
            public void onImageDownload(List<Image> imgs) {
                setAdapter(imgs);
            }
        }, PAPER);

    }

    private void setAdapter(List<Image> images) {
        imgs = images;
        mainImageAdapter = new MainImageAdapter(getActivity(), (ArrayList<Image>) images, gvPaperImage);
        gvPaperImage.setAdapter(mainImageAdapter);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetialActivity.class);
        intent.putExtra(EXTRA_PATH, imgs.get(position).getSkipPagePath());
        //Log.i(TAG, "onItemClick: path: " + imgs.get(position).getSkipPagePath());
        startActivity(intent);
        setEnterTransition(getExitTransition());
    }

    @Override
    public void onDestroyView() {
        mainImageAdapter.stopThread();
        pic.cancleAsyncTask();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 3000);
    }
}
