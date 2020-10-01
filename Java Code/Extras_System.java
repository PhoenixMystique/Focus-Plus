package org.hashhackers.focus;

import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class Extras_System {
    ListView list;

    public Extras_System(ListView list) {
        this.list = list;
    }
    public  void Remove(){
        list.removeAllViews();
    }}


    //  ----------------------------------------------------Sorter-----------------------------------------------------------------------------------------------------
class Sorter{

   List<App_list> list;


    public Sorter(List<App_list> list) {
                this.list = list;
            }
            public void sorted(){
                int size =list.size();
                for (int i=0;i<size;i++){
                    long num= list.get(i).appuse;
                        for(int k=0;k<size;k++){
                            long num2 = list.get(k).appuse;

                        if(num>num2){
                            App_list take=list.get(i);
                            list.set(i,list.get(k));
                            list.set(k,take);
                        }
                    }
        } }}