package com.udacity.stockhawk.myWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.PrefUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Basel on 03/02/2017.
 */

public class WidgetListAdapter implements RemoteViewsService.RemoteViewsFactory {
    public String[] IMAGES;
    private Context context;
    private int []appWidgetIds;
    private final DecimalFormat dollarFormatWithPlus;
    private final DecimalFormat dollarFormat;
    private final DecimalFormat percentageFormat;
    Cursor cur;
    public WidgetListAdapter(Context context, Intent intent)
    {
         cur =context.getContentResolver().query(Contract.Quote.URI, Contract.Quote.QUOTE_COLUMNS.toArray(new String[]{}),
                null, null, Contract.Quote.COLUMN_SYMBOL);
        int numColumns = cur.getCount();
        //cur.close();

        this.context = context;
        appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");

    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
cur.close();
    }

    @Override
    public int getCount() {
        if(cur!=null)
            return cur.getCount();
        else{ Log.d("my trace","data size in widget= 0");
            return 0;}
    }

    @Override
    public RemoteViews getViewAt(int position) {
        cur.moveToPosition(position);
        final  RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_item_quote);
        remoteView.setTextViewText(R.id.symbol, cur.getString(Contract.Quote.POSITION_SYMBOL));
        remoteView.setTextViewText(R.id.price,dollarFormat.format(cur.getFloat(Contract.Quote.POSITION_PRICE)));
        float rawAbsoluteChange = cur.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE);
        float percentageChange = cur.getFloat(Contract.Quote.POSITION_PERCENTAGE_CHANGE);
        if (rawAbsoluteChange > 0)
            remoteView.setTextColor(R.id.change, Color.GREEN);
        else
        remoteView.setTextColor(R.id.change,Color.RED);
//remoteView.sette(R.id.change,R.drawable.percent_change_pill_red);

        String change = dollarFormatWithPlus.format(rawAbsoluteChange);
        String percentage = percentageFormat.format(percentageChange / 100);

        if (PrefUtils.getDisplayMode(context)
                .equals(context.getString(R.string.pref_display_mode_absolute_key))) {
           remoteView.setTextViewText(R.id.change,change);
        } else {
            remoteView.setTextViewText(R.id.change,percentage);
        }


        // remoteView.setTextViewText();
        Log.d("my trace", "appwidg id " + appWidgetIds.length);
        Log.d("my trace", "inside return view loop " + position);

        try {
          //  remoteView.setImageViewBitmap(R.id.widget_pic, images.get(position));
            Intent i=new Intent();
            Bundle extras=new Bundle();

            extras.putString("listrow", "hh");
            i.putExtras(extras);
            remoteView.setOnClickFillInIntent(R.id.symbol, i);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
