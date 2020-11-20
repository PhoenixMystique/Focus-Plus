package org.hashhackers.focus.Tools;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import org.hashhackers.focus.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Date_Format {
   private static Context context;
   private static long smsTimeInMilis;
    private static  TextView totaluse;

    public Date_Format() {
        this.context = context;
        this.smsTimeInMilis = smsTimeInMilis;
    }

    public static String getFormattedDate(Context context, long smsTimeInMilis) {
            Calendar smsTime = Calendar.getInstance();
            smsTime.setTimeInMillis(smsTimeInMilis);

            Calendar now = Calendar.getInstance();

            final String timeFormatString = "h:mm aa";
            final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
            final long HOURS = 60 * 60 * 60;
            if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
                return "Yesterday";
            }  else {
                return (String) android.text.format.DateFormat.format(timeFormatString, smsTime);
            }
        }
    public static String getFormattedDate_fixday(Context context, long smsTimeInMilis) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(new Date(smsTimeInMilis));

    }
    public static String DateToDayConvertor(String input) throws ParseException {
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = inFormat.parse(input);
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        return      outFormat.format(date);

    }
    public static void use_Seter(View totaluseView,long TotalUse){
totaluse = totaluseView.findViewById(R.id.totalappuse);
        String data[] = DateUtils.formatElapsedTime(TotalUse / 1000).split(":");
        if (data[0].length()==1){
            data[0]="0"+data[0];
        }
        if(data.length>=3){

            totaluse.setText(data[0]+" : "+data[1]+" : "+data[2]);

        }
        else{
            totaluse.setText("00"+" : "+data[0]+" : "+data[1]);
        }

    }

}
