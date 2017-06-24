package com.threedlink.ivotalents.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.threedlink.ivotalents.common.activities.MainActivity;
import com.threedlink.ivotalents.common.activities.baseactivities.BaseActivity;

/**
 * Created by diiaz94 on 12-06-2017.
 */
public class Util {


    protected static BaseActivity activity;
    protected static SharedPreferences sharedPreferences;

    public static void setActivity(BaseActivity _activity) {
        activity = _activity;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static Context getContext() {
        return getActivity().getApplicationContext();
    }

    public static void replaceFragment(FragmentManager frManager, Fragment fr, int container) {
        frManager
                .beginTransaction()
                .addToBackStack(fr.getClass().getSimpleName())
                .replace(container, fr).commit();
    }

    public static String getToken() {
        return "";
    }

    public static void goToActivity(Activity fromActivity, Class toActivity, int intentsFlags) {
        Intent intent = new Intent(getContext(), toActivity);
        getActivity().startActivity(intent.addFlags(intentsFlags));
        fromActivity.finish();
    }

    public static void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showNotifications() {
        ((MainActivity)getActivity()).notificationsRecyclerView.setVisibility(View.VISIBLE);

    }
}
