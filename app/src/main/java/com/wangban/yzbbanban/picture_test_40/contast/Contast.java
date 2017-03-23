package com.wangban.yzbbanban.picture_test_40.contast;

import android.os.Environment;

/**
 * Created by YZBbanban on 16/6/5.
 */
public interface Contast {
    int HANDLER_PICDAL_SUCCESS = 1;

    String NEW = "http://m.xxxiao.com/new";
    String SEXY = "http://m.xxxiao.com/cat/xinggan";
    String YOUNG = "http://m.xxxiao.com/cat/shaonv";
    String PU = "http://m.xxxiao.com/cat/mrxt";
    String LEG = "http://m.xxxiao.com/cat/swmt";
    String PORTRAIT = "http://m.xxxiao.com/cat/xgtx";
    String JAPAN = "http://m.xxxiao.com/cat/rihandongya";
    String COLLECTION = "http://m.xxxiao.com/cat/collection";
    String FOREIGN = "http://m.xxxiao.com/cat/oumei";
    String PAPER = "http://m.xxxiao.com/cat/wallpaper";

    String BEAUTY_PATH = Environment.getExternalStorageDirectory() + "/DCIM/";

    int MAX_SIZE = 250;

    String TAG = "supergirl";
    String TAG2 = "superman";

    int HANDLER_LOAD_BITMAP_SUCCESSS = 1;
    int HANDLER_SAVE_IMGER_TO_SD = 3;
    int HANDLER_SAVE_START=6;
    int HANDLER_SET_WALLPAPER=8;
    int REFRESH_COMPLETE = 2;
    int TOUCH_STOP = 50;
    int TOUCH_MOVE=150;
    int TOUCH_FINISH=150;
    int TOUCH_MOVE_X=70;
    int TOUCH_MOVE_Y=70;
    int MOVE_TO_RIGHT=4;
    int MOVE_TO_LEFT=5;
    int FINISH_IMG=7;

    String EXTRA_PATH = "path";
    String EXTRA_IMAGE_PATH = "image_path";
    String EXTRA_IMAGE_POSITION="image_position";
   // String EXTRA_IMAGES="images";

}
