package com.wangban.yzbbanban.picture_test_40.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.contast.Contast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.*;

/**
 * Created by YZBbanban on 16/6/9.
 */
public class DisplayUtil implements Contast {
    private Context context;
    private GridView gridView;
    private ViewPager viewPager;
    private Map<String, SoftReference<Bitmap>> cache = new HashMap<String, SoftReference<Bitmap>>();
    private List<ImageLoadTask> tasks = new ArrayList<ImageLoadTask>();

    private boolean isLoop = true;
    private Thread workThread;
    private int type;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_LOAD_BITMAP_SUCCESSS:
                    ImageLoadTask task = (ImageLoadTask) msg.obj;
                    Bitmap bitmap = task.bm;
                    ImageView imageView = null;
                    if (gridView != null) {
                        imageView = (ImageView) gridView.findViewWithTag(task.path);
                    }
                    if (viewPager != null) {
                        imageView = (ImageView) viewPager.findViewWithTag(task.path);
                    }
                    Log.i(TAG, "handleMessage: 1111111");
                    if (imageView != null) {
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        } else {
                            imageView.setImageResource(R.drawable.katong);
                        }
                    }

                    break;


            }
        }
    };

    public DisplayUtil(Context context, ViewPager viewpager) {
        startThread();
        this.context = context;
        this.viewPager = viewpager;
    }

    public DisplayUtil(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
        startThread();

    }

    private void startThread() {
        workThread = new Thread() {
            @Override
            public void run() {
                while (isLoop) {
                    if (!tasks.isEmpty()) {
                        ImageLoadTask task = tasks.remove(0);
                        String path = task.path;
                        Bitmap bitmap = loadBitmap(path);
                        task.bm = bitmap;
                        Message msg = Message.obtain();
                        msg.what = HANDLER_LOAD_BITMAP_SUCCESSS;
                        msg.obj = task;
                        handler.sendMessage(msg);
                        Log.i(TAG, "run: hello");
                    } else {
                        try {
                            synchronized (workThread) {
                                workThread.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        workThread.start();

    }

    public void mageDisplay(ImageView mianImg, String path, int type) {
        this.type = type;
        //Log.w(TAG, "mageDisplay: "+type);
        SoftReference<Bitmap> ref = cache.get(path);
        if (ref != null) {
            Bitmap bitmap = ref.get();
            if (bitmap != null) {
                mianImg.setImageBitmap(bitmap);
                return;
            }

        }
        Log.i(TAG, "mageDisplay: context: " + context.getCacheDir().getAbsolutePath());
        File file = new File(context.getCacheDir()
                , path);

        Bitmap bitmap = BitmapUtil.loadBitmap(file.getAbsolutePath());

        if (bitmap != null) {
            mianImg.setImageBitmap(bitmap);
            cache.put(path, new SoftReference<Bitmap>(bitmap));
            return;
        }

        mianImg.setTag(path);
        ImageLoadTask task = new ImageLoadTask();
        task.path = path;
        tasks.add(task);
        if (workThread == null) {
            return;
        } else {
            synchronized (workThread) {
                workThread.notify();
            }
        }
        return;
    }

    public Bitmap loadBitmap(String path) {
        try {
            InputStream is = HttpUtil.get(path);
            Bitmap bitmap = BitmapUtil.loadBitmap(is, type, 200, 300);
            cache.put(path, new SoftReference<Bitmap>(bitmap));
            // Log.e(TAG, "loadBitmap: 天呢！！！！！");
            String fileName = path;
            File file = new File(context.getCacheDir(), fileName);
            BitmapUtil.saveImage(file.getAbsolutePath(), bitmap);

            //Log.i(TAG, "loadBitmap: " + path);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生产模式
     */
    class ImageLoadTask {
        String path;
        Bitmap bm;
    }


    public void stopThread() {
        isLoop = false;
        if (workThread == null) {
            return;
        } else {
            synchronized (workThread) {
                workThread.notify();
            }
            workThread = null;
        }
    }


}
