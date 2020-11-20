package org.hashhackers.focus.Activitys;
        import android.app.usage.UsageStatsManager;
        import android.content.Context;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Base64;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

        import com.github.mikephil.charting.charts.PieChart;
        import com.github.mikephil.charting.data.PieEntry;

        import java.util.ArrayList;
        import java.util.Calendar;

        import org.hashhackers.focus.Tools.Date_Format;
        import org.hashhackers.focus.Features.Charts.Pie_chart;
        import org.hashhackers.focus.Features.Usages_Genrator.Uses_By_Day;
        import org.hashhackers.focus.R;

public class AppInfo extends AppCompatActivity {
    Uses_By_Day Piedata;
    String Package_Name;
    Pie_chart pieChart;
    PieChart chart;
    Date_Format format;
    Calendar cal;  ArrayList<PieEntry> Chart_Data;
    static PackageManager packageManager;
    static UsageStatsManager usageStatsManager;
    public class ChartMaker extends AsyncTask<String, Void ,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            pieChart.show();
        }

        @Override
        protected String doInBackground(String... strings) {

         Chart_Data  = new ArrayList<>();
            for(int i=0;i<7;i++) {
                cal= Calendar.getInstance();
                if(i==0){
                    cal.add(Calendar.DAY_OF_MONTH, -i);
                }
                else {
                    cal.add(Calendar.DAY_OF_MONTH, -(i+1));
                }
                cal.set(Calendar.HOUR_OF_DAY, 00);
                cal.set(Calendar.MINUTE, 00);
                cal.set(Calendar.SECOND, 00);
                cal.set(Calendar.MILLISECOND, 00);
                long startTime = cal.getTimeInMillis();
                long currTime;
                cal= Calendar.getInstance();
                if(i==0){
                    currTime = System.currentTimeMillis();
                }
                else{cal.add(Calendar.DAY_OF_MONTH, -i);
                cal.set(Calendar.HOUR_OF_DAY, 00);
                cal.set(Calendar.MINUTE, 00);
                cal.set(Calendar.SECOND, 00);
                cal.set(Calendar.MILLISECOND, 00);
                 currTime = cal.getTimeInMillis();}
                Piedata = new Uses_By_Day(Package_Name, AppInfo.this, startTime, currTime, usageStatsManager, packageManager);
                int value = Piedata.getTotalUse();
                String day="";
              try{  day = format.DateToDayConvertor(format.getFormattedDate_fixday(AppInfo.this,currTime));}catch (Exception e){}

                Chart_Data.add(i,new PieEntry(value,day));
            }

          pieChart = new Pie_chart(Chart_Data,chart);


            return null;
        }
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info);
        usageStatsManager =(UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        packageManager = getPackageManager();


// Getting Datr From Another Activity
        String extras = getIntent().getStringExtra("Itzadibanna");
        String[] get = extras.split("Itzadibanna");
        Bitmap bmp =StringToBitMap(get[0]);



        // Intilization Dataa
        ImageView image = findViewById(R.id.info_image);
         TextView Appname = findViewById(R.id.Appname);
         TextView Appcount = findViewById(R.id.AppCountDisplay2);
         TextView Appuse = findViewById(R.id.TimeUsed);
         TextView PackageName = findViewById(R.id.packageName);
         TextView Last_used = findViewById(R.id.last_used);
        chart = findViewById(R.id.pichart);
        chart.setVisibility(View.INVISIBLE);
        format = new Date_Format();
//Setting Data in their Positions
         Appcount.setText(get[3]+" times");

         if(get[4].length()<=3){
             Appuse.setText(get[4]+" sec");
         }
         else if(get[4].length()==5||get[4].length()==4 ){
             Appuse.setText(get[4]+" min");
         }
       else {
             Appuse.setText(get[4]+" hr");
         }
        Last_used.setText(get[5]);
         Appname.setText(get[1]);
         PackageName.setText(get[2]);
          Package_Name =get[2];
        image.setImageBitmap(bmp);
      // DOne

        // Calling Method
        ChartMaker Make = new ChartMaker();
        Make.execute("Run");
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}