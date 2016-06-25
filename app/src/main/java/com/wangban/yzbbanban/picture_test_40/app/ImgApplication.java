package com.wangban.yzbbanban.picture_test_40.app;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.entity.DetialImage;
import com.wangban.yzbbanban.picture_test_40.fragment.FragmentNew;

import java.util.ArrayList;

/**
 * Created by YZBbanban on 16/6/5.
 */
public class ImgApplication extends Application implements Contast {
    private ArrayList<DetialImage> images;
    private int position;
    @Override
    public void onCreate() {
        //to be continue;
    }

    public ArrayList<DetialImage> getImageData() {
        return images;
    }

    public void setImageData(ArrayList<DetialImage> images) {
        this.images = images;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
