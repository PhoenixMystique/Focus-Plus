package org.hashhackers.focus.Activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;

import android.util.Base64;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import java.util.Calendar;
import java.util.List;

import android.os.AsyncTask;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.hashhackers.focus.Features.App_Blocker.App_block;

import org.hashhackers.focus.Features.Dark_Mode.DarKModer;
import org.hashhackers.focus.R;
import org.hashhackers.focus.Features.Usages_Genrator.App_list;
import org.hashhackers.focus.Features.Usages_Genrator.Sorter;
import org.hashhackers.focus.Features.Usages_Genrator.last_Timer;
import org.hashhackers.focus.Features.Usages_Genrator.usagesMaker;
import org.hashhackers.focus.Tools.Date_Format;

import static android.view.View.GONE;

public class DataActivity extends AppCompatActivity  {
    private   ChipNavigationBar chipNavigationBar;
    private App_block app_block;
    private static PackageManager packageManager;
    private static UsageStatsManager usageStatsManager;
    private static List<App_list> lists_user;
    private static Boolean Screen_Check=true;
    private Sorter sort;
    private static PowerManager powerManager;
    private Date_Format date_format;
    boolean outofApp=false;
    private static CardView cardView;
    private  ListView listView;
    private static Intent intent2;
    private TextView totaluse;
    private MyAdapter adapter;
    private long TotalUse;
    private static Calendar cal;
    DarKModer darKModer;
    private usagesMaker Usages_coocker;


private final void Execute_Main_Process() {

    AsyncTask.execute(new Runnable() {
        @Override
        public void run() {
            Usages_coocker = new usagesMaker(DataActivity.this, cal.getTimeInMillis(), System.currentTimeMillis(), usageStatsManager, packageManager, WhiteLister());
            if (Usages_coocker.Maker() == 0) {
                System.out.println("permission Not Given By User For UsagesStats");
                startActivity(intent2);
            }
            lists_user = Usages_coocker.getLists_user();
            TotalUse = Usages_coocker.getTotalUse();
           sort = new Sorter(lists_user);
            sort.sorted();
       runOnUiThread(new Runnable() {
    @Override
    public void run() {
        adapter = new MyAdapter(DataActivity.this, R.layout.custom_list_view,lists_user);
        cardView.setVisibility(GONE);
        listView.setAdapter(adapter);
        date_format.use_Seter(totaluse,TotalUse);
        totaluse.setAlpha(1f);

        }
});

        }
    });


}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Window window = DataActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.Dark));
        cal= Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 00);
        totaluse= findViewById(R.id.totalappuse);
        date_format = new Date_Format();
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        listView = findViewById(R.id.List);
        darKModer = new DarKModer(this,chipNavigationBar,totaluse);
        cardView = findViewById(R.id.cardview);
        usageStatsManager =(UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        packageManager = getPackageManager();
        powerManager = (PowerManager)getSystemService(DataActivity.POWER_SERVICE);
        intent2 = new Intent(this,permit.class);

         //calling Methods
        Execute_Main_Process();
        BackGroung_Initlization();
        chipNavigationBar.setItemSelected(R.id.installed,true);
        Run_Time();
        app_block=new App_block(this,WhiteLister(),packageManager,usageStatsManager,powerManager,intent2,cal.getTimeInMillis());
    }
    private final void BackGroung_Initlization(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                new last_Timer(cal.getTimeInMillis(),System.currentTimeMillis(),usageStatsManager,lists_user);
                navigationClickListner();
                list_listner();
            }
        });

    }
    private void list_listner(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
           app_block.show(lists_user.get(position).getImage(),lists_user.get(position).getAppuseInMillis(),lists_user.get(position).getPackageName());
           iconListner(position);
                return false;
            }
        });


    }
    private void Run_Time(){

        final Handler handler = new Handler();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if(outofApp){app_block.Appblocker();}
                app_block.Appblocker();
                Screen_Check=powerManager.isInteractive();
                if(Screen_Check){
                   TotalUse =1000+TotalUse;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            date_format.use_Seter(totaluse,TotalUse);
                        }
                    });       }
                handler.postDelayed(this,1000);
            }
        });
    }
    private void iconListner(final int position){
    AsyncTask.execute(new Runnable() {
        @Override
        public void run() {
            Bitmap bmp = drawableToBitmap(lists_user.get(position).getImage());
            String icon = BitMapToString(bmp);
            final String Send = icon + "Itzadibanna" + lists_user.get(position).getAppname()
                    + "Itzadibanna" + lists_user.get(position).getPackageName()
                    + "Itzadibanna" + lists_user.get(position).getAppCount()
                    + "Itzadibanna" + lists_user.get(position).getAppuse() + "Itzadibanna" + lists_user.get(position).getLastUsed();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    app_block.ChangeActivity(Send);
                }
            });

        }
    });


    }
    private void navigationClickListner(){
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switch (id){
                    case R.id.installed:
                        break;
                    case R.id.system:
                        Toast.makeText(DataActivity.this, "Not Available In Beta Version", Toast.LENGTH_SHORT).show();
                        chipNavigationBar.setItemSelected(R.id.installed,true);
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

    }
    @Override
    protected void onPostResume() {
        super.onPostResume();

        outofApp=false;
    }
    @Override
    protected void onStop() {
        super.onStop();
        outofApp=true;
    }
    final private String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    private  Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    final private String[] WhiteLister(){
        final   String[] list= new String[12];
        list[1]="Chrome";
        list[2]="Google";
        list[3]="Phone";
        list[4]="File Manager";
        list[5]="YouTube";
        list[6]="Contacts";
        list[7]="Gmail";
        list[8]="Settings";
        list[9]="Play Store";
        list[10]="Video";
        list[11]="Duo";
        list[0]="Music";

        return list;
    }

}