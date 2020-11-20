package org.hashhackers.focus.Features.App_Blocker;

import android.content.Context;

public class Blocked_List {
    private Context context;
    private String Package;
 private long usedTime;
 private long TimeLimit ;

    public Blocked_List(Context context,long UsedTime, String Package,long timeLimit) {
        this.context = context;
        this.usedTime=UsedTime;
        this.Package = Package;
        this.TimeLimit=timeLimit;

    }

    public Context getContext() {
        return context;
    }

    public String getPackage() {
        return Package;
    }

    public long getUsedTime() {
        return usedTime;
    }

    public long getTimeLimit() {
        return TimeLimit;
    }

    public void setUsedTime(long usedTime) {
        this.usedTime = usedTime;
    }
}
