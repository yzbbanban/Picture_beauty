package com.wangban.yzbbanban.picture_test_40.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.wangban.yzbbanban.picture_test_40.contast.Contast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by YZBbanban on 16/6/7.
 */
public class BitmapUtil implements Contast {
    public static Bitmap loadBitmap(InputStream is, int imageType, int width, int height) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];

        int length;
        while ((length = is.read(buffer)) != -1) {
            bos.write(buffer, 0, length);
            bos.flush();
        }
        byte[] bytes = bos.toByteArray();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);

        int w = opts.outWidth / width;
        int h = opts.outHeight / height;
        int scale = 1;
        if (imageType == 1) {
            scale = w > h ? h : w;
            //Log.i(TAG, "loadBitmap:type " + scale);
        } else if (imageType == 2) {
            scale = w > h ? w : h;
            //Log.i(TAG, "loadBitmap:type " + scale);
        }else if(imageType==3){
            scale=1;
        }
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);

        return bitmap;
    }

    public static void saveImage(String path, Bitmap bitmap) throws IOException {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
    }

    public static Bitmap loadBitmap(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }

}
