package org.hashhackers.focus.Features.Usages_Genrator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;

import org.hashhackers.focus.Tools.Date_Format;

public class App_list {
   private Drawable image;
    private  String PackageName;
    private String appname="";
public    long appuse,lastUsed;
  private   int AppCount;
   private Context context;

   public App_list(Context context,String appname, Drawable image, long Appuse, String PackageName, int AppCount){
        this.appname =appname;
        this.appuse=Appuse;
        this.image=image;
        this.PackageName=PackageName;
        this.AppCount=AppCount;
        this.context=context;

    }
    public String getLastUsed() {
        Date_Format date_format = new Date_Format();
        String out = date_format.getFormattedDate(context,lastUsed);
        return out;
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

    public long getAppuseInMillis() {
        return appuse;
    }
}