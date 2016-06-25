package com.wangban.yzbbanban.picture_test_40.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.entity.DetialImage;
import com.wangban.yzbbanban.picture_test_40.util.JsoupUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YZBbanban on 16/6/6.
 */
public class DetialPictureDao implements Contast {
    private AsyncTask<String, String, List<DetialImage>> task;

    public DetialPictureDao() {
    }


    public void findDetilImageGridView(final Callback callback, final String webPath) {
        task = new AsyncTask<String, String, List<DetialImage>>() {
            List<DetialImage> images = new ArrayList<DetialImage>();

            @Override
            protected List<DetialImage> doInBackground(String... params) {
                if (isCancelled()) {
                    return null;
                }
                    try {
                        //Log.i(TAG, "doInBackground: hello" + webPath);
                        images = JsoupUtil.downDetilLoadData(webPath);
                        return images;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                return null;
            }

            @Override
            protected void onPostExecute(List<DetialImage> images) {
                callback.onImageDownload(images);
            }
        };
        task.execute();
    }

    public interface Callback {
        void onImageDownload(List<DetialImage> list);
    }

    public void cancleAsyncTask() {
        //TODO
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            //标记为 cancle 状态，不是取消执行
            task.cancel(true);
        }
    }

}
