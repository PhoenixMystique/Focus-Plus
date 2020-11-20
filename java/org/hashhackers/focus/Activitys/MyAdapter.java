package org.hashhackers.focus.Activitys;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.hashhackers.focus.R;
import org.hashhackers.focus.Features.Usages_Genrator.App_list;

import java.util.List;


class MyAdapter extends ArrayAdapter {
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
    public View getView(int position, @Nullable View nvertView, @NonNull ViewGroup parent) {
        Context context;
        LayoutInflater inflater = LayoutInflater.from(cont);
        View view = inflater.inflate(R.layout.custom_list_view,null);
        TextView appuse = view.findViewById(R.id.App_Usages);
        TextView appname = view.findViewById(R.id.Appname);
        final ImageView appimage = view.findViewById(R.id.appimage);
        App_list list =  arrayList.get(position);
        appname.setText(list.getAppname());
        appimage.setImageDrawable(list.getImage());
        appuse.setText( list.getAppuse());

        return view;
    }
}


//------------------------------------------------------------------------------------------------------------------------------------


