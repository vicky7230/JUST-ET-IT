package com.awesome.vicky.justetit.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.awesome.vicky.justetit.pojo.NetworkStateChanged;

import de.greenrobot.event.EventBus;

public class NetworkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkAvailable(context)) {
            //Toast.makeText(context, "Network Available", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Network Unavailable", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new NetworkStateChanged(false));
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
