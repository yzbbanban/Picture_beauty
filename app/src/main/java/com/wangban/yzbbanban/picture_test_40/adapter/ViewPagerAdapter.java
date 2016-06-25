package com.wangban.yzbbanban.picture_test_40.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import android.app.WallpaperManager;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.activity.ImageActivity;
import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.entity.DetialImage;
import com.wangban.yzbbanban.picture_test_40.ui.TouchImageView;
import com.wangban.yzbbanban.picture_test_40.util.BitmapUtil;
import com.wangban.yzbbanban.picture_test_40.util.DisplayUtil;
import com.wangban.yzbbanban.picture_test_40.util.HttpUtil;

/**
 * Created by YZBbanban on 16/6/14.
 */
public class ViewPagerAdapter extends PagerAdapter implements Contast {
    private Context context;
    private List<DetialImage> images;
    private TouchImageView ivSingleimg;
    private ProgressBar pb;
    private ProgressBar pbSave;
    private MyAsyncTask mTask;
    private Bitmap bm;
    private String url;
    private Thread thread;
    private int currentPosition;
    private DetialImage detialImage;
    private DisplayUtil displayUtil;
    private ViewPager viewPager;

    public ViewPagerAdapter(Context context, List<DetialImage> images, ViewPager viewPager) {
        this.context = context;
        this.images = images;
        this.viewPager = viewPager;
        this.displayUtil = new DisplayUtil(context, viewPager);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
//        mTask.cancel(true);
    }

    @Override
    public int getCount() {
        if (images != null) {
            return images.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.image_detial_item, null);
        ivSingleimg = (TouchImageView) view.findViewById(R.id.iv_single_img);
        pb = (ProgressBar) view.findViewById(R.id.progressBar);
        pbSave = (ProgressBar) view.findViewById(R.id.pb_save);

        currentPosition = position;
        //66666666666666666765=Log.i(TAG, "instantiateItem: current:  "+currentPosition);
        detialImage = images.get(position);
        //Log.i(TAG, "instantiateItem: "+images.get(position).getHrefPath());
        //asyncTaskStart();

        displayUtil.mageDisplay(ivSingleimg, detialImage.getHrefPath(), 3);

        //Log.i(TAG, "instantiateItem: 2222222");

        container.addView(view);

        ivSingleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageActivity) context).finish();
                ((ImageActivity) context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
            }
        });
        ivSingleimg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialogMenu();
                return true;
            }
        });

        return view;
    }

    //    public void setPositon(int position){
//        currentPosition=position;
//    }
    private void asyncTaskStart() {
        url = detialImage.getHrefPath();
        mTask = new MyAsyncTask();
        mTask.execute(url);
    }

    /**
     * 此方法无用
     */

    class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);

        }


        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                String url = params[0];
                InputStream is = HttpUtil.get(url);
                bm = BitmapFactory.decodeStream(is);
                return bm;
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 100; i++) {
                if (isCancelled()) {
                    break;
                }
                publishProgress(i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            pb.setVisibility(View.GONE);
            ivSingleimg.setImageBitmap(bitmap);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (isCancelled()) {
                return;
            }
            pb.setProgress(values[0]);
        }

    }

    public void onPause() {
        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            //标记为 cancle 状态，不是取消执行
            mTask.cancel(true);
        }
        thread = null;
    }

    private AlertDialog.Builder builder;
    private void dialogMenu() {
        builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.icon_menu2).setTitle("图片设置").setMessage("您要保存吗？");
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //handler.sendEmptyMessage(HANDLER_SAVE_START);
                pbSave.setVisibility(View.VISIBLE);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            saveImage2();
                            handler.sendEmptyMessage(HANDLER_SAVE_IMGER_TO_SD);
                        } catch (IOException e) {
                            Toast.makeText(context, "无法链接网络！", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton("设为壁纸", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pbSave.setVisibility(View.VISIBLE);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            setWallpaper();

                        } catch (IOException e) {
                            //Toast.makeText(context, "无法链接网络！", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();

            }
        });

        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        showProgress();
    }

    private void showProgress() {
        pbSave.setVisibility(View.VISIBLE);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case HANDLER_SAVE_START:
//                    pbSave.setVisibility(View.VISIBLE);
//                    break;
                case HANDLER_SAVE_IMGER_TO_SD:
                    pbSave.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                    break;

                case HANDLER_SET_WALLPAPER:

                    try {
                        Bitmap b = (Bitmap) msg.obj;
                        WallpaperManager wpManager = WallpaperManager.getInstance(context);
                        //Log.i(TAG, "setWallpaper: " + b.toString());
                        wpManager.setBitmap(b);
                        pbSave.setVisibility(View.INVISIBLE);
                        Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    /**
     * system suggest method
     *
     * @throws IOException
     */
    private void saveImage2() throws IOException {

        Bitmap bitmap = loadBitmap();
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "title", "description");

    }

    private Bitmap loadBitmap() throws IOException {
        InputStream is = HttpUtil.get(images.get(currentPosition - 1).getHrefPath());
        Bitmap bitmap = BitmapUtil.loadBitmap(is, 3, 683, 1024);
        return bitmap;
    }


    public void stopThread() {
        displayUtil.stopThread();
    }


    private void setWallpaper() throws IOException {
        Bitmap b = loadBitmap();
        Message msg = Message.obtain();
        msg.obj = b;
        msg.what=HANDLER_SET_WALLPAPER;
        handler.sendMessage(msg);

    }

}
