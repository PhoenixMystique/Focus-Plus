package org.hashhackers.focus.Features.Usages_Genrator;

import java.util.List;

//  ----------------------------------------------------Sorter-----------------------------------------------------------------------------------------------------
public class Sorter{

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
                    if(num>=num2){
                        App_list take=list.get(i);
                        list.set(i,list.get(k));
                        list.set(k,take);
                    }
                }
    } }}
