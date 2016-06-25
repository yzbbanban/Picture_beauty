package com.wangban.yzbbanban.picture_test_40.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.entity.Image;
import com.wangban.yzbbanban.picture_test_40.util.DisplayUtil;

import java.util.ArrayList;

/**
 * Created by YZBbanban on 16/6/5.
 */
public class MainImageAdapter extends BaseAdapter<Image>  {
    private Context context;
    private GridView gridView;
    private DisplayUtil displayUtil;

    public MainImageAdapter(Context context, ArrayList<Image> data, GridView gridView) {
        super(context, data);
        this.context = context;
        this.gridView = gridView;
        this.displayUtil=new DisplayUtil(context,gridView);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image img = getData().get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.main_img_item, null);
            holder.mianImg = (ImageView) convertView.findViewById(R.id.iv_main_image);
            holder.tvTypeImage= (TextView) convertView.findViewById(R.id.tv_type_image);
            holder.tvTypeTitle= (TextView) convertView.findViewById(R.id.tv_type_title);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        holder.tvTypeTitle.setText(img.getTitleType());
        holder.tvTypeImage.setText(img.getTitle());
        displayUtil.mageDisplay(holder.mianImg,img.getPath(),1);
        return convertView;
    }

    private class ViewHolder {

        ImageView mianImg;
        TextView tvTypeTitle;
        TextView tvTypeImage;
    }

    public void stopThread(){
        displayUtil.stopThread();
    }

}
