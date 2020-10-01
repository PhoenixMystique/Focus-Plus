package org.hashhackers.focus;

import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;

import java.util.ArrayList;

public class App_list {
    Drawable image;
    String PackageName;
    String appname="";
    long appuse;
    int AppCount;

   public App_list(String appname, Drawable image,long Appuse,String PackageName,int AppCount){
        this.appname =appname;
        this.appuse=Appuse;
        this.image=image;
        this.PackageName=PackageName;
        this.AppCount=AppCount;

    }

    public String getAppname() {
        return appname;
    }


    public String getPackageName() {
        return PackageName;
    }

    public Drawable getImage() {
        return image;
    }

    public int getAppCount() {
        return AppCount;
    }

    public String getAppuse() {
        return DateUtils.formatElapsedTime(appuse/1000);
    }
}
