package org.hashhackers.focus;

import java.util.List;

public class Sort {
    List<App_list> List;

    public Sort(List<App_list> list) {
        List = list;
    }
    private App_list Sorter(){

        for (int i=0;i<List.size();i++){

            System.out.println(List.get(i));


        }

        return (App_list) List;
    }
}
