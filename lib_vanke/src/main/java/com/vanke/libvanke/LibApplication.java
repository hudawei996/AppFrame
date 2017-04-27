package com.vanke.libvanke;

import android.app.Application;

import com.vanke.libvanke.data.HttpManager;
import com.vanke.libvanke.util.AppUtils;
import com.vanke.libvanke.util.Logger;
import com.vanke.libvanke.util.ToastUtils;

/**
 * User: PAPA
 * Date: 2017-03-31
 */

public class LibApplication {

    private static final String LOG_TAG = "zhuzheer";

    private LibApplication() {

    }
    public static void init(Application context, boolean isDebug) {
        Logger.init(LOG_TAG, isDebug);
        HttpManager.init(context, isDebug);
        AppUtils.init(context);
        ToastUtils.init(context);
    }
}
