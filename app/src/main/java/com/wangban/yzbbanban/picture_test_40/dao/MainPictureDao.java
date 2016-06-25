package com.wangban.yzbbanban.picture_test_40.dao;


import android.os.AsyncTask;
import android.util.Log;

import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.entity.Image;
import com.wangban.yzbbanban.picture_test_40.util.HttpUtil;
import com.wangban.yzbbanban.picture_test_40.util.JsoupUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by YZBbanban on 16/6/6.
 */
public class MainPictureDao implements Contast {
    private AsyncTask<String, String, List<Image>> task;
    public MainPictureDao() {
    }


    public void findImageGridView(final Callback callback, final String webPath) {
        task = new AsyncTask<String, String, List<Image>>() {
            List<Image> images = new ArrayList<Image>();

            @Override
            protected List<Image> doInBackground(String... params) {
                if (isCancelled()) {
                    return null;
                }
                try {
                    //Log.i(TAG, "doInBackground: " + webPath);
                    images = JsoupUtil.downLoadData(webPath);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return images;
            }
            @Override
            protected void onPostExecute(List<Image> images) {
                callback.onImageDownload(images);
            }
        };
        task.execute();
    }

    public interface Callback {
        void onImageDownload(List<Image> list);
    }

    public void cancleAsyncTask() {
        //TODO
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            //标记为 cancle 状态，不是取消执行
            task.cancel(true);
        }
        task=null;
    }

}
