package org.hashhackers.focus.Features.Charts;

import android.graphics.Color;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Pie_chart {
    PieChart chart;
    ArrayList<PieEntry> Chart_data;
    View view;
    PieData data;

    public Pie_chart(ArrayList<PieEntry> Chart_data,PieChart chart) {

       this.Chart_data = Chart_data;
        this.chart =chart;
  Chart_data = new ArrayList<>();
  chart();
    }
    private void chart(){

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);


        chart.setDrawHoleEnabled(true);
        chart.setHoleRadius(90f);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleRadius(0f);
chart.isUsePercentValuesEnabled();
        chart.setDrawSliceText(false);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);



        PieDataSet dataSet = new PieDataSet(Chart_data, "Usages Graph");
        dataSet.setValueTextSize(0);
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
dataSet.setValueLineWidth(1f);

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
       data = new PieData(dataSet);

    }
    public  void show(){
        chart.setData(data);
        chart.setVisibility(View.VISIBLE);
        chart.animateY(700,Easing.EaseInCirc);
    }
}
