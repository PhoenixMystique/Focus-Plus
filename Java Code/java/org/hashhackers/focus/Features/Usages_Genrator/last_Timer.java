package org.hashhackers.focus.Features.Usages_Genrator;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.text.format.DateUtils;

import java.util.List;

import static android.app.usage.UsageStatsManager.INTERVAL_DAILY;

public class last_Timer{
      private static List<App_list> lists_user;
      private static UsageStatsManager usageStatsManager;
      private static long startTime,currTime;

      public last_Timer(long startTime,long CurrentTime,UsageStatsManager usageStatsManager,List<App_list> Lists_User) {
          lists_user = Lists_User;
          this.usageStatsManager = usageStatsManager;
          this.startTime=startTime;
          this.currTime=CurrentTime;
find_last_Time();


      }
      private void find_last_Time(){

          final List<UsageStats> stats = usageStatsManager.queryUsageStats(INTERVAL_DAILY, startTime, currTime);
          System.out.println(DateUtils.formatElapsedTime(startTime/1000));
      for(int i=0;i<stats.size();i++){
          UsageStats pkgStats = stats.get(i);
          for(int run=0;run<lists_user.size();run++){

              if(pkgStats.getPackageName().equals(lists_user.get(run).getPackageName())){
                lists_user.get(run).lastUsed=pkgStats.getLastTimeUsed();

              }
          }
      }}

    public static List<App_list> getLists_user() {
        return lists_user;
    }
}
