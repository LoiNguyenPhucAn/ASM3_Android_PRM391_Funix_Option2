package com.example.animal;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;


public class AnimalType {

    private String title;
    private Bitmap icon;
    private String tag;
    private String detail;
    private Bitmap imagedetail;
    private String iconBitmapPath;

    public AnimalType(String title, Bitmap icon, String iconBitmapPath, String tag, String detail, Bitmap imagedetail) {
        this.title = title;
        this.icon = icon;
        this.tag = tag;
        this.detail = detail;
        this.imagedetail = imagedetail;
        this.iconBitmapPath = iconBitmapPath;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDetail() {
        return detail;
    }

    public Bitmap getImagedetail() {
        return imagedetail;
    }

    public String getIconBitmapPath() {
        return iconBitmapPath;
    }
}
