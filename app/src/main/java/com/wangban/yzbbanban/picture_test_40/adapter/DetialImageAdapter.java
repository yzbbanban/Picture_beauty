package com.wangban.yzbbanban.picture_test_40.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.entity.DetialImage;
import com.wangban.yzbbanban.picture_test_40.util.DisplayUtil;

import java.util.*;

/**
 * Created by YZBbanban on 16/6/5.
 */
public class DetialImageAdapter extends BaseAdapter<DetialImage> {
    private Context context;
    private List<DetialImage> data;
    private DisplayUtil displayUtil;
    private GridView gridView;

    public DetialImageAdapter(Context context, ArrayList<DetialImage> data,GridView gridView) {
        super(context, data);
        this.context=context;
        this.data=data;
        this.gridView=gridView;
        displayUtil=new DisplayUtil(context,gridView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.detial_img_item, null);
            holder.DetailImg = (ImageView) convertView.findViewById(R.id.iv_detial_image);
            convertView.setTag(holder);
        }
            DetialImage image=getData().get(position);
            holder= (ViewHolder) convertView.getTag();

            displayUtil.mageDisplay(holder.DetailImg,image.getDetPath(),2);


        return convertView;
    }

    private class ViewHolder {
        ImageView DetailImg;


    }

    public void stopThread(){
        displayUtil.stopThread();
    }

}
