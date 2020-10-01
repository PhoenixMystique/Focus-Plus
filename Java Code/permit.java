package org.hashhackers.focus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import static android.app.usage.UsageStatsManager.INTERVAL_DAILY;

public class permit extends AppCompatActivity {
     Intent intent;
    UsageStatsManager usageStatsManager;
    public void allow(View view){
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));

    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        List<UsageStats> stats = usageStatsManager.queryUsageStats(INTERVAL_DAILY, System.currentTimeMillis()-1000*3600*10,System.currentTimeMillis());
        if(stats.size()<1){
            Toast.makeText(this, "Permission By User Grannted", Toast.LENGTH_SHORT).show();
        }
        else {

  startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit);
intent = new Intent(this,DataActivity.class);
        usageStatsManager =(UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
    }
}