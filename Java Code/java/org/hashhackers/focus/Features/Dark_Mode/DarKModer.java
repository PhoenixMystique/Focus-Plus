package org.hashhackers.focus.Features.Dark_Mode;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.hashhackers.focus.R;

public class DarKModer {
    private Context context;
    private View view;
   ChipNavigationBar chipNavigationBar;
   TextView text;
   TextView text2;
   TextView text3;
   ListView listView;

  static int number=0;

    public DarKModer(final Context context,View view,View view2) {
        this.context = context;
        this.view = view;

        chipNavigationBar =view.findViewById(R.id.chipNavigationBar);

        text =view2.findViewById(R.id.totalappuse);


       text.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public boolean onLongClick(View v) {
               change();
                return false;
            }
        });
    }





    private void change(){

        switch(number) {
            case 0:     chipNavigationBar.setBackgroundResource(R.drawable.navigation);
                                text.setTextColor(ContextCompat.getColor(context,R.color.White));

            number++;
                break;
            case 1:     chipNavigationBar.setBackgroundResource(R.drawable.navigate_bg_2);
                text.setTextColor(ContextCompat.getColor(context,R.color.Color2));

                number++;break;
            case 2:     chipNavigationBar.setBackgroundResource(R.drawable.navigate_bg_3);
                text.setTextColor(ContextCompat.getColor(context,R.color.Color3));
                number++;break;
            case 3:     chipNavigationBar.setBackgroundResource(R.drawable.navigate_bg_4);
                text.setTextColor(ContextCompat.getColor(context,R.color.Color4));
               number++;break;
            case 4:     chipNavigationBar.setBackgroundResource(R.drawable.navigate_bg_5);
                text.setTextColor(ContextCompat.getColor(context,R.color.Color5));
               number++;break;
            case 5:     chipNavigationBar.setBackgroundResource(R.drawable.navigate_bg_6);
                 text.setTextColor(ContextCompat.getColor(context,R.color.Color6));
         number=0;break;
        }

    }
}
