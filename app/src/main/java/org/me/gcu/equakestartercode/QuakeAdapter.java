package org.me.gcu.equakestartercode;
//Aaron Amjid S1626987
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class QuakeAdapter extends ArrayAdapter<QuakeData> {
/*
Custom ArrayAdapter for displaying Quakedata in a listview.
Creates list items with different colour codings as follows from weakest to strongest:
Cyan
Blue
Green
Yellow
Red
 */
    public QuakeAdapter(Context context, ArrayList<QuakeData> quakelist) {
        super(context, 0, quakelist);
    }

    @Override
    public View getView(int position, View QuakeView, ViewGroup parent) {
        QuakeData quake = getItem(position);
        if (QuakeView == null) {
            QuakeView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_layout, parent, false);
        }
        TextView Name = (TextView) QuakeView.findViewById(R.id.quakename);
        LinearLayout layout = (LinearLayout) QuakeView.findViewById(R.id.itemlayout) ;
        double mag = quake.getMagnitude();
        //switch statement doesn't accept double values, need to use ugly nested if statements
        if(mag < 1){
            layout.setBackgroundColor(Color.CYAN);
            Name.setTextColor(Color.BLACK);
        }
        else if(mag >= 1 && mag < 1.5){
            layout.setBackgroundColor(Color.BLUE);
            Name.setTextColor(Color.WHITE);
        }
        else if(mag >= 1.5 && mag < 2){
            layout.setBackgroundColor(Color.GREEN);
            Name.setTextColor(Color.BLACK);
        }
        else if(mag >= 2 && mag < 2.5){
            layout.setBackgroundColor(Color.YELLOW);
            Name.setTextColor(Color.BLACK);
        }
        else{
            layout.setBackgroundColor(Color.RED);
            Name.setTextColor(Color.BLACK);
        }
        Name.setText(quake.getTitle());
        return QuakeView;
    }

}
