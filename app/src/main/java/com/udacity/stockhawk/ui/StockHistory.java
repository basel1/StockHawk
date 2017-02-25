package com.udacity.stockhawk.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.udacity.stockhawk.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockHistory extends AppCompatActivity {
@BindView(R.id.chart)
LineChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_history);
        ButterKnife.bind(this);
        ArrayList<Entry> entries = new ArrayList<Entry>();
String historyString=getIntent().getStringExtra("history");
        historyString= historyString.replace("\n","");
       String fullHistory[]= historyString.split(", ");
        for(int i=1;i<fullHistory.length;i+=2)
         {

            // turn your data into Entry objects
            entries.add(new Entry(i-1,Float.parseFloat(fullHistory[i])));
        }
        XAxis x=chart.getXAxis();
        x.setTextColor(Color.WHITE);
        chart.setBorderColor(Color.BLUE);
        chart.setBackgroundColor(Color.LTGRAY);
        x.setValueFormatter(new MyXAxisValueFormatter(fullHistory));
        LineDataSet dataSet = new LineDataSet(entries, getResources().getString(R.string.graph_name)); // add entries to dataset
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
        setTitle(getIntent().getStringExtra("symbol")+getResources().getString(R.string.history));
    }
}
 class MyXAxisValueFormatter implements IAxisValueFormatter {

     private String[] mValues;

     public MyXAxisValueFormatter(String[] values) {
         this.mValues = values;
     }

     @Override
     public String getFormattedValue(float value, AxisBase axis) {
         // "value" represents the position of the label on the axis (x or y)
         return mValues[(int) value];
     }

 }
