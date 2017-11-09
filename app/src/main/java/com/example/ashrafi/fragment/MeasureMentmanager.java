package com.example.ashrafi.fragment;

import android.view.View;
import android.widget.ListView;

/**
 * Created by Acer PC on 1/26/2017.
 */

public class MeasureMentmanager {
    public static int getListViewHeight(ListView listView) {
        listView.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return listView.getMeasuredHeight();
    }

}
