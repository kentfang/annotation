package com.galanz.processors;

import android.app.Activity;
import com.galanz.processors.binder.ViewBinder;
import com.galanz.processors.proxy.OnClickProxy;

public class GalanzBinder {
    public static void bind(Activity activity){
        String className = activity.getClass().getName()+"$ViewBinder";
        try {
            Class<?>  viewBindClass=Class.forName(className);
            ViewBinder viewBinder = (ViewBinder) viewBindClass.newInstance();
            viewBinder.bind(activity);
            OnClickProxy.newInstance().injectOnClickEvents(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
