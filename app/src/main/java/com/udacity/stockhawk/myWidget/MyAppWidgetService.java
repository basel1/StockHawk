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

        return new WidgetListAdapter(getApplicationContext(),intent);
    }
}
