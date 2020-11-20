package org.hashhackers.focus.Features.App_Blocker;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import android.widget.Toast;
import androidx.annotation.RequiresApi;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.hashhackers.focus.Activitys.AppInfo;
import org.hashhackers.focus.Activitys.DataActivity;
import org.hashhackers.focus.Features.Usages_Genrator.App_list;

import org.hashhackers.focus.Features.Usages_Genrator.usagesMaker;
import org.hashhackers.focus.R;


import java.security.SecureRandom;
import java.util.ArrayList;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.content.Context.ACTIVITY_SERVICE;

public class App_block  {
    private Context context;
    private ArrayList<String> Packages;
    private String packagename;
    private static Intent full_info,intent2;
    private NumberPicker HourPicker,MinutePicker,SecondPicker;
    private ArrayList<String> Blocked_Confirm;
    private  int blocklistCount=0;
    private LayoutInflater inflater;
    private ImageView  appimage_to_send;
    private static List<Blocked_List> blocked_lists;
    private static PackageManager packageManager;
    private static UsageStatsManager usageStatsManager;
    private static Boolean Screen_Check=true;
    private static PowerManager powerManager;
    private  View view;
    private NiftyDialogBuilder builder;
    private String[] WhiteList;
    private Button button;
    private ImageView icon;
    long time;


    public App_block(Context context, String[] whiteList,PackageManager packageManager,UsageStatsManager usageStatsManager,PowerManager powerManager,Intent intent2,long Time) {
        this.context = context;
        full_info = new Intent(context, AppInfo.class);
        WhiteList = whiteList;
        this.packageManager=packageManager;
        this.usageStatsManager=usageStatsManager;
        this.powerManager=powerManager;
        blocked_lists = new ArrayList<>();
        Blocked_Confirm = new ArrayList<String>();
        this.intent2=intent2;
        this.time=Time;
        dataUpdater();
    }

    public void show(Drawable drawable, final long Appuse, final String packagename) {
  builder = NiftyDialogBuilder.getInstance( context);
       inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
       view = inflater.inflate(R.layout.time_picker,null);

        builder.setContentView(view);
        builder.withEffect(Effectstype.Fall);


        button=view.findViewById(R.id.button2);
        icon =view.findViewById(R.id.icon);
        HourPicker = (NumberPicker) view.findViewById(R.id.hourPicker);
        MinutePicker =(NumberPicker)   view.findViewById(R.id.minutePicker);
        SecondPicker =  (NumberPicker) view.findViewById(R.id.secondPickerr);
        HourPicker.setMaxValue(10);
        MinutePicker.setMaxValue(59);
        SecondPicker.setMaxValue(59);
        HourPicker.setMinValue(00);
        MinutePicker.setMinValue(00);
        SecondPicker.setMinValue(00);
        HourPicker.setValue(01);
        MinutePicker.setValue(00);
        SecondPicker.setValue(00);
         builder.setCanceledOnTouchOutside(true);
        builder.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               builder.dismiss();
                blocked_lists.add(blocklistCount,new Blocked_List(context, Appuse,packagename,(HourPicker.getValue()*3600000)+(MinutePicker.getValue()*60000)+(SecondPicker.getValue()*1000)));
                blocklistCount++;

            }
        });
        icon.setImageDrawable(drawable);

    }
    private void dataUpdater(){
        final Handler handler = new Handler();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,60000);

                if(Screen_Check) {
                    List<App_list> Updated_ListUser;
                    usagesMaker Updater = new usagesMaker(context, time, System.currentTimeMillis(), usageStatsManager, packageManager, WhiteList);
                    if (Updater.Maker() == 0) {
                        System.out.println("permission Not Given By User For UsagesStats");
                        context.startActivity(intent2);
                    }
                    Updated_ListUser = Updater.getLists_user();

                    for (int f = 0; f < blocked_lists.size(); f++) {
                        for (int k = 0; k < Updated_ListUser.size(); k++) {
                            if (blocked_lists.get(f).getPackage().equals(Updated_ListUser.get(k).getPackageName())) {
                                blocked_lists.get(f).setUsedTime(Updated_ListUser.get(k).getAppuseInMillis());

                            }

                        }
                    }

                }
            }});

    }

    public void Appblocker(){
        Screen_Check=powerManager.isInteractive();
        if (Screen_Check) {
            boolean block__check = false;

            for (int i = 0; i < blocked_lists.size(); i++) {
                if (blocked_lists.get(i).getTimeLimit() <= blocked_lists.get(i).getUsedTime()) {
                    Blocked_Confirm.add(Blocked_Confirm.size(), blocked_lists.get(i).getPackage());
                    block__check = true;

                }


                if (block__check) {
                  getForegroundApp(Blocked_Confirm);
                  blockTheApps();
                }


            }

        }

    }

    public String getForegroundApp(ArrayList<String> BlockedList) {
        this.Packages=BlockedList;
        String currentApp = "NULL";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 500 * 500, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }
     packagename=currentApp;
        return currentApp;
    }
    public void blockTheApps(){
        for(int i=0;i<Packages.size();i++){
            if(packagename.equals(Packages.get(i))){
                Toast.makeText(context, "TimeOut Bitch", Toast.LENGTH_SHORT).show();
                ActivityManager am = (ActivityManager)context.getSystemService(ACTIVITY_SERVICE);
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(startMain);
                am.killBackgroundProcesses(packagename);
            }}
        }
    public void ChangeActivity(final String Send){
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                appimage_to_send = view.findViewById(R.id.icon);
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(appimage_to_send, "Image");
                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);
                full_info.putExtra("Itzadibanna", Send);
                context.startActivity(full_info);
            }
        });

}

}
