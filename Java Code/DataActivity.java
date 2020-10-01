package org.hashhackers.focus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import static android.app.usage.UsageStatsManager.INTERVAL_DAILY;

public class DataActivity<stats> extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    PackageManager packageManager;
    UsageStatsManager usageStatsManager;
    List<App_list> lists_user;
    List<App_list> lists_system;
    Sorter sort;
    ListView listView;
    Intent intent2;
    boolean permision=true;
    ArrayList<AppUsageInfo> Datause;
    Background_Task task;
    Extras_System remove;
    MyAdapter adapter;
    int countfor_installed=0,Countfor_notinstalled=0;
    long time_used,TotalUse;
    public class Background_Task extends AsyncTask<String, Void ,String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Run();
    }
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        protected String doInBackground(String... data) {
           Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            lists_user = new ArrayList<>();
            lists_system = new ArrayList<>();

    remove = new Extras_System(listView);

            List<UsageStats> stats = usageStatsManager.queryUsageStats(INTERVAL_DAILY, System.currentTimeMillis()-1000*3600*10,System.currentTimeMillis());
            if(stats.size()<1){
                startActivity(intent2);
            }
            UsageEvents.Event currentEvent;
            List<UsageEvents.Event> allEvents = new ArrayList<>();
            HashMap<String, AppUsageInfo> map = new HashMap <String, AppUsageInfo> ();

         Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 0);
            cal.set(Calendar.HOUR_OF_DAY,00);
            cal.set(Calendar.MINUTE,00);
            cal.set(Calendar.SECOND,00);
            cal.set(Calendar.MILLISECOND,00);
            long currTime = System.currentTimeMillis();
            long startTime = cal.getTimeInMillis();
            assert usageStatsManager != null;

            UsageEvents usageEvents = usageStatsManager.queryEvents(startTime, currTime);


            while (usageEvents.hasNextEvent()) {
                currentEvent = new UsageEvents.Event();
                usageEvents.getNextEvent(currentEvent);
                if (currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND ||
                        currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                    allEvents.add(currentEvent);
                    String key = currentEvent.getPackageName();
// taking it into a collection to access by package name
                    if (map.get(key)==null) map.put(key,new AppUsageInfo(key));
                }
            }

//iterating through the arraylist
            for (int i=0;i<allEvents.size()-1;i++){
                UsageEvents.Event E0=allEvents.get(i);
                UsageEvents.Event E1=allEvents.get(i+1);

//for launchCount of apps in time range
                if (!E0.getPackageName().equals(E1.getPackageName()) && E1.getEventType()==1){
// if true, E1 (launch event of an app) app launched
                    map.get(E1.getPackageName()).launchCount++;
                }

//for UsageTime of apps in time range
                if (E0.getEventType()==1 && E1.getEventType()==2
                        && E0.getClassName().equals(E1.getClassName())){
                    long diff = E1.getTimeStamp()-E0.getTimeStamp();
                    TotalUse+=diff; //gloabl Long var for total usagetime in the timerange
                    map.get(E0.getPackageName()).timeInForeground+= diff;
                }
            }

          Datause = new ArrayList<>(map.values());
for(int h=0;h<Datause.size();h++){
   try {
       ApplicationInfo appInfo = packageManager.getApplicationInfo(Datause.get(h).packageName, 0);
       if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1){
         String packageName= Datause.get(h).packageName;
         int  launchCount=Datause.get(h).launchCount;
          long timeInForeground =Datause.get(h).timeInForeground;
          String AppName= (String) appInfo.loadLabel(packageManager);
          Drawable Icon = appInfo.loadIcon(packageManager);
          lists_system.add(Countfor_notinstalled,new App_list(AppName,Icon,timeInForeground,packageName,launchCount));
       Countfor_notinstalled++;}
       else {
           String packageName= Datause.get(h).packageName;
           int  launchCount=Datause.get(h).launchCount;
           long timeInForeground =Datause.get(h).timeInForeground;
           String AppName= (String) appInfo.loadLabel(packageManager);
           Drawable Icon = appInfo.loadIcon(packageManager);
           lists_user.add(countfor_installed,new App_list(AppName,Icon,timeInForeground,packageName,launchCount));
       countfor_installed++;}
   }catch (Exception e){
       System.out.println("Problem Occured\n\n\n\nProblem Occured" +e);
   }

    System.out.println("Done");

}
            return null;   }
    }

private void Run() {
   sort = new Sorter(lists_user);
    sort.sorted();
  sort = new Sorter(lists_system);
  sort.sorted();
    TextView totaluse = findViewById(R.id.totalappuse);
    ImageView image = findViewById(R.id.tt);
    image.setAlpha(1f);

    switch (chipNavigationBar.getSelectedItemId()) {
        case R.id.installed:
            adapter = new MyAdapter(DataActivity.this, R.layout.custom_list_view, lists_user);
            break;
        case R.id.system:
            adapter = new MyAdapter(DataActivity.this, R.layout.custom_list_view, lists_system);
            break;
    }
    listView.setAdapter(adapter);
    String data = DateUtils.formatElapsedTime(TotalUse / 1000);
    totaluse.setText(data);


}

    public class MyAdapter extends ArrayAdapter {
        Context cont;
        List<App_list> arrayList;
        int resouses;
        public MyAdapter( Context context, int resource, List<App_list> arrayList) {
            super(context, resource, arrayList);
            this.arrayList=arrayList;
            this.resouses=resource;
            this.cont=context;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Context context;
            LayoutInflater inflater = LayoutInflater.from(cont);
            View view = inflater.inflate(R.layout.custom_list_view,null);
              TextView appuse = view.findViewById(R.id.App_Usages);
            TextView appname = view.findViewById(R.id.Appname);
            ImageView appimage = view.findViewById(R.id.appimage);
            App_list list =  arrayList.get(position);
            appname.setText(list.getAppname());
            appimage.setImageDrawable(list.getImage());
            appuse.setText( list.getAppuse());

            return view;
        }
    }




    private boolean checkWriteExternalPermission()
    {
        String permission = Manifest.permission.PACKAGE_USAGE_STATS;
        int res = DataActivity.this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
remove = new Extras_System(listView);
        Window window = DataActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));


        //ok make the login activity bypass
        SharedPreferences settings = getSharedPreferences(MainActivity.Pre_Name,0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("OkLoggedIn",true);
        editor.commit();
        /// doene

//
 chipNavigationBar = findViewById(R.id.chipNavigationBar);
chipNavigationBar.setItemSelected(R.id.installed,true);
chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
    @Override
    public void onItemSelected(int id) {
        switch (id){
            case R.id.installed:
                 remove = new Extras_System(listView);
                 adapter = new MyAdapter(DataActivity.this, R.layout.custom_list_view,lists_user);
                listView.setAdapter(adapter);
                break;
            case R.id.system:
                remove = new Extras_System(listView);//need to intilized one time
                 adapter = new MyAdapter(DataActivity.this, R.layout.custom_list_view,lists_system);
                listView.setAdapter(adapter);
                break;
            case R.id.alert:
                Toast.makeText(DataActivity.this, "Not Available In Beta Version", Toast.LENGTH_SHORT).show();
                chipNavigationBar.setItemSelected(R.id.installed,true);
                break;
            case R.id.Alarm:
                Toast.makeText(DataActivity.this, "Not Available In Beta version", Toast.LENGTH_SHORT).show();
                chipNavigationBar.setItemSelected(R.id.installed,true);

        }
    }
});

// Defining Items

        listView = findViewById(R.id.List);
        usageStatsManager =(UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        packageManager = getPackageManager();

intent2 = new Intent(this,permit.class);

      task = new Background_Task();
     task.execute("Run");

// click Listner for listview
final Intent full_info = new Intent(this,Full_Info.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 startActivity(full_info);


            }
        });




    }
}