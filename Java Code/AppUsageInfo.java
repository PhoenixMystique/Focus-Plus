package org.hashhackers.focus;

import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;

public class AppUsageInfo {
        Drawable appIcon;
        String appName, packageName;
        long timeInForeground=0;
        int launchCount=0;
        AppUsageInfo(String pName) {
            this.packageName=pName;
        }

}

