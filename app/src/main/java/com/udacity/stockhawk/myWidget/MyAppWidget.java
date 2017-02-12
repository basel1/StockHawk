package com.udacity.stockhawk.myWidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.ui.MainActivity;

import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Intent intent = new Intent(context,
                MyAppWidgetService.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
       // intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews remoteViews = new RemoteViews(context
                .getApplicationContext().getPackageName(),
                R.layout.my_app_widget);
        remoteViews.setRemoteAdapter(R.id.listView, intent);
     //   PendingIntent pintent = PendingIntent.getService(context.getApplicationContext(), 0, new Intent(context,MyAppWidget.class), 0);
        // remoteViews.setPendingIntentTemplate(R.id.appwidget_text, pintent);
//        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
  //      alarm.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 60 * 1000, pintent);
        // clicking action

        Intent clickIntent = new Intent(context,
                MainActivity.class);

         clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
             appWidgetIds);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.listView, pendingIntent);

        // Update the widgets via the service
        context.startService(intent);





        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);
           // updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

