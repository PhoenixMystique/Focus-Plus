package org.hashhackers.focus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.usage.UsageStatsManager.INTERVAL_DAILY;

public class MainActivity extends AppCompatActivity {
    UsageStatsManager usageStatsManager;


    public static final int key = 1;
    public static final String Pre_Name = "MyCode";
    private Intent intent;
    private Intent intent2;
    private EditText username;
private EditText password;
private ArrayList<String> temp;
private ArrayList<String> user;
protected boolean logincheck=true;
public void login(View login){

  String usernam = username.getText().toString();
  String pass = password.getText().toString();
    for(int i=0;i<user.size();i++){
if(usernam.equalsIgnoreCase(user.get(i))){System.out.println(user.get(i));
    for(int k=0;k<temp.size();k++){
        if(pass.equalsIgnoreCase(temp.get(k))){
            logincheck=false;
            List<UsageStats> stats = usageStatsManager.queryUsageStats(INTERVAL_DAILY, System.currentTimeMillis()-1000*3600*10,System.currentTimeMillis());
if(stats.size()<1){
                startActivity(intent2);

            }
      else {      Toast.makeText(this, "Successful Login as "+user.get(i), Toast.LENGTH_SHORT).show();
          startActivity(intent);
          MainActivity.this.finish();}
        }
             }

    if(logincheck){

        Toast.makeText(this, "Wrong Username Or Password ", Toast.LENGTH_SHORT).show();}
    }
}

    }
public void temprory(){
        temp.add("Aditya");
        temp.add("password");
        temp.add("pass");
        user.add("Hashhacker");
        user.add("Admin");


}
public void signup(View view){
    Toast.makeText(this, "Signup not Available in beta-Test", Toast.LENGTH_SHORT).show();
}
public void Linkedin(View view){
    Toast.makeText(this, "Linkedin log-in is not Available in beta-Test", Toast.LENGTH_SHORT).show();
    }
public void Facebook(View view){
    Toast.makeText(this, "Facebook log-in is not Available in beta-Test", Toast.LENGTH_SHORT).show();
}
public void twitter(View view){
    Toast.makeText(this, "Twitter log-in is not Available in beta-Test", Toast.LENGTH_SHORT).show();
}
public void google(View view){
    Toast.makeText(this, "Google log-in is not Available in beta-Test", Toast.LENGTH_SHORT).show();
}

@Override
    protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    //checkPermission

        setContentView(R.layout.activity_main);
    intent = new Intent(this,DataActivity.class);
    intent2 = new Intent(this,permit.class);
        SharedPreferences settings = getSharedPreferences(MainActivity.Pre_Name,0);
        boolean OkLoggedIn = settings.getBoolean("OkLoggedIn",false);
        if(OkLoggedIn){
            startActivity(intent);
            MainActivity.this.finish();
        }
  usageStatsManager =(UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
   
   temp  = new ArrayList<>();
   user  = new ArrayList<>();
   temprory();
    username = findViewById(R.id.username);
    password = findViewById(R.id.pass);


    }
}
