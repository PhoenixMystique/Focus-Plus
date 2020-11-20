package org.hashhackers.focus.Features.Usages_Genrator;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.usage.UsageStatsManager.INTERVAL_DAILY;

public class usagesMaker  {
        private  ArrayList<AppUsageInfo> Datause;
        private List<App_list> lists_user;
        private UsageStatsManager usageStatsManager;
        private  PackageManager packageManager;
        private  String[] WhiteList;
        private  int countfor_installed=0;
    private static int TotalUse=0;
        private static long startTime,currTime;
         private  static Context context;
        public usagesMaker(Context context,long startTime, long CurrentTime, UsageStatsManager usageStatsManager, PackageManager packageManager, String[] WhiteList) {
            lists_user = new ArrayList<>();
            Datause = new ArrayList<>();
            this.WhiteList=WhiteList;
            this.usageStatsManager = usageStatsManager;
            this.packageManager = packageManager;
            this.startTime=startTime;
            this.currTime=CurrentTime;
            this.context=context;


        }


        public int Maker() {
            final List<UsageStats> stats = usageStatsManager.queryUsageStats(INTERVAL_DAILY, startTime,currTime);

            if (stats.size() < 1) {
                return 0;
            }

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
                    String key = currentEvent.getPackageName();

                    if (map.get(key) == null) map.put(key, new AppUsageInfo(key));
                }
            }

            for (int i = 0; i < allEvents.size() - 1; i++) {
                UsageEvents.Event E0 = allEvents.get(i);
                UsageEvents.Event E1 = allEvents.get(i + 1);


                if (!E0.getPackageName().equals(E1.getPackageName()) && E1.getEventType() == 1) {
// if true, E1 (launch event of an app) app launched
                    map.get(E1.getPackageName()).launchCount++;
                }

//for UsageTime of apps in time range
                if (E0.getEventType() == 1 && E1.getEventType() == 2
                        && E0.getClassName().equals(E1.getClassName())) {
                    long diff = E1.getTimeStamp() - E0.getTimeStamp();
                    TotalUse += diff; //gloabl Long var for total usagetime in the timerange
                    map.get(E0.getPackageName()).timeInForeground += diff;
                }
            }

            Datause = new ArrayList<>(map.values());
            for (int h = 0; h < Datause.size(); h++) {
                try {
                    ApplicationInfo appInfo = packageManager.getApplicationInfo(Datause.get(h).packageName, 0);
                    if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                        for (int next = 0; next < WhiteList.length; next++) {
                            String AppName = (String) appInfo.loadLabel(packageManager);
                            if (AppName.equalsIgnoreCase(WhiteList[next])) {
                                String packageName = Datause.get(h).packageName;
                                int launchCount = Datause.get(h).launchCount;
                                long timeInForeground = Datause.get(h).timeInForeground;
                                Drawable Icon = appInfo.loadIcon(packageManager);
                                lists_user.add(countfor_installed, new App_list(context,AppName, Icon, timeInForeground, packageName, launchCount));
                                countfor_installed++;
                            }
                        }
                    } else {
                        String packageName = Datause.get(h).packageName;
                        int launchCount = Datause.get(h).launchCount;
                        long timeInForeground = Datause.get(h).timeInForeground;
                        String AppName = (String) appInfo.loadLabel(packageManager);
                        Drawable Icon = appInfo.loadIcon(packageManager);
                        lists_user.add(countfor_installed, new App_list(context,AppName, Icon, timeInForeground, packageName, launchCount));
                        countfor_installed++;
                    }
                } catch (Exception e) {

                    System.out.println("Problem in Usages Maker" + e);
                }


            }


            return 1;   }

        public  List<App_list> getLists_user() {
            return lists_user;
        }

        public  int getTotalUse() {
            return TotalUse;
        }



    }


