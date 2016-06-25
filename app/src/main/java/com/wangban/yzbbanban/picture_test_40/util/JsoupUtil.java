package com.wangban.yzbbanban.picture_test_40.util;

import android.util.Log;

import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.entity.DetialImage;
import com.wangban.yzbbanban.picture_test_40.entity.Image;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YZBbanban on 16/6/9.
 */
public class JsoupUtil implements Contast{
    /**
     * 网页beauty类图片链接等解析
     * @param url
     * @return
     * @throws IOException
     */
    public static List<Image> downLoadData(String url) throws IOException {
        List<Image> images = new ArrayList<Image>();
        Document doc = Jsoup.connect(url).get();
        Elements e1 = doc.getElementsByClass("post-thumb");
        Elements e2=doc.getElementsByClass("entry-cats");
        for (int i = 0; i < e1.size(); i++) {
            Elements a2 = e1.get(i).getElementsByTag("a");
            String skipPagePath = a2.first().attr("href");

            String width2 = a2.first().getElementsByTag("img").attr("width");
            String height2 = a2.first().getElementsByTag("img").attr("height");

            String path2 = a2.first().getElementsByTag("img").attr("src");
            String title=a2.first().getElementsByTag("img").attr("alt");

            Elements a1=e2.get(i).getElementsByTag("a");
            String titleType=a1.text();
            int width = Integer.parseInt(width2);
            int height = Integer.parseInt(height2);
            // Log.i(TAG, "run: " + "path2  " + path2 + "\n" + "width " + width2 + "\nheight " + height2);
            Image img = new Image();
            img.setSetSkipPagePath(skipPagePath);
            img.setPath(path2);
            img.setHeight(height);
            img.setWidth(width);
            img.setLocalPath(url);
            img.setTitle(title);
            img.setTitleType(titleType);
            images.add(img);
            //Log.i(TAG, "getData: path:"+ i+";"+img.getPath());
        }
        return images;
    }

    /**
     * 详细图片解析
     * @param url
     * @return
     * @throws IOException
     */
    public static List<DetialImage> downDetilLoadData(String url) throws IOException {
        List<DetialImage> images = new ArrayList<DetialImage>();
        Document doc = Jsoup.connect(url).get();
        Elements el = doc.getElementsByTag("title");
        String title = el.first().text();
        Elements e = doc.getElementsByClass("rgg-imagegrid");
        Elements a = e.first().getElementsByTag("a");
        int index = 0;
        for (Element element : a) {
            String hrefpath=a.get(index).attr("href");
            String path = a.get(index).getElementsByTag("img").attr("src");
            String width = a.get(index).getElementsByTag("img").attr("width");
            String height = a.get(index).getElementsByTag("img").attr("height");
            index++;
            int width2 = Integer.parseInt(width);
            int height2 = Integer.parseInt(height);
            DetialImage image = new DetialImage();
            image.setDetPath(path);
            image.setDetWidth(width2);
            image.setDetHeight(height2);
            image.setDetTitle(title);
            image.setHrefPath(hrefpath);
            images.add(image);
            Log.i(TAG, "downDetilLoadData: "+"hrefPath: "+hrefpath+"\npath: "+path+"\nwidth: "+width2+"\nheight: "+height2+"\ntitle: "+title);
        }
        return images;
    }

}
