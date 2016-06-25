package com.wangban.yzbbanban.picture_test_40.entity;

import java.io.Serializable;

/**
 * Created by YZBbanban on 16/6/5.
 */
public class Image implements Serializable {
    private String path;
    private int width;
    private int height;
    private String skipPagePath;
    private String localPath;
    private String title;
    private String titleType;

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image(String path, int width, int height, String skipPagePath, String localPath, String title) {
        this.path = path;
        this.width = width;
        this.height = height;
        this.skipPagePath = skipPagePath;
        this.localPath = localPath;
        this.title = title;

    }


    public Image() {
    }

    public void setSetSkipPagePath(String setSkipPagePath) {
        this.skipPagePath = setSkipPagePath;
    }

    public String getSkipPagePath() {
        return skipPagePath;
    }

    public void setSkipPagePath(String skipPagePath) {
        skipPagePath = skipPagePath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return path;
    }
}
