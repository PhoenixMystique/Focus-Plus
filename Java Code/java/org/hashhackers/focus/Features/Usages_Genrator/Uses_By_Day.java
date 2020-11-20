package org.hashhackers.focus.Features.Usages_Genrator;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Uses_By_Day  {

    private static UsageStatsManager usageStatsManager;
    private static PackageManager packageManager;
    private  static String package_name;
    public   int TotalUse=0;
    private static long startTime,currTime;
    Context context;

    public Uses_By_Day(String package_name,Context context,long startTime, long CurrentTime, UsageStatsManager usageStatsManager, PackageManager packageManager) {

        this.package_name=package_name;
        this.usageStatsManager = usageStatsManager;
        this.packageManager = packageManager;
        this.startTime=startTime;
        this.currTime=CurrentTime;
        this.context=context;

Maker();
    }


    protected void Maker() {

        UsageEvents.Event currentEvent;
        List<UsageEvents.Event> allEvents = new ArrayList<>();
        HashMap<String, AppUsageInfo> map = new HashMap<String, AppUsageInfo>();


        assert usageStatsManager != null;

        UsageEvents usageEvents = usageStatsManager.queryEvents(startTime, currTime);
        while (usageEvents.hasNextEvent()) {
            currentEvent = new UsageEvents.Event();
            usageEvents.getNextEvent(currentEvent);
            if (currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND ||
                    currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                allEvents.add(currentEvent);
            }}
//iterating through the arraylist
                for (int i = 0; i < allEvents.size() - 1; i++) {
                    UsageEvents.Event E0 = allEvents.get(i);
                    UsageEvents.Event E1 = allEvents.get(i + 1);
//for UsageTime of apps in time range
                    if (E0.getPackageName().equalsIgnoreCase(package_name) || E1.getPackageName().equalsIgnoreCase(package_name)){
                        if (E0.getEventType() == 1 && E1.getEventType() == 2
                                && E0.getClassName().equals(E1.getClassName())) {
                            long diff = E1.getTimeStamp() - E0.getTimeStamp();
                            TotalUse += diff; //gloabl Long var for total usagetime in the timerange

                        }
                }  }



            }


    public int getTotalUse(){


     return  (TotalUse)/60*1000;


    }
}


