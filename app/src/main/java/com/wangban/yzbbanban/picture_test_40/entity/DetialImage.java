package com.wangban.yzbbanban.picture_test_40.entity;

/**
 * Created by YZBbanban on 16/6/5.
 */
public class DetialImage {
    private String detPath;
    private int detWidth;
    private int detHeight;
    private String detTitle;
    private String hrefPath;
    public DetialImage() {
    }


    public String getDetPath() {
        return detPath;
    }

    public void setDetPath(String detPath) {
        this.detPath = detPath;
    }

    public int getDetWidth() {
        return detWidth;
    }

    public void setDetWidth(int detWidth) {
        this.detWidth = detWidth;
    }

    public int getDetHeight() {
        return detHeight;
    }

    public void setDetHeight(int detHeight) {
        this.detHeight = detHeight;
    }

    public String getDetTitle() {
        return detTitle;
    }

    public void setDetTitle(String detTitle) {
        this.detTitle = detTitle;
    }

    public String getHrefPath() {
        return hrefPath;
    }

    public void setHrefPath(String hrefPath) {
        this.hrefPath = hrefPath;
    }

    @Override
    public String toString() {
        return  detPath;
    }
}
