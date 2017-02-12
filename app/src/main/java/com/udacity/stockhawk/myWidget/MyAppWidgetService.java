package com.udacity.stockhawk.myWidget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by Basel on 03/02/2017.
 */

public class MyAppWidgetService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int a=5;
        a++;
        Log.d("my trace","in remote view service");
        return new WidgetListAdapter(getApplicationContext(),intent);
    }
}
