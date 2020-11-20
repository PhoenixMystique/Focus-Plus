package org.hashhackers.focus.Features.Usages_Genrator;

import android.graphics.drawable.Drawable;

public class AppUsageInfo {
        Drawable appIcon;
        String appName, packageName;
        long timeInForeground=0;
        int launchCount=0;
        AppUsageInfo(String pName) {
            this.packageName=pName;
        }

}

