package com.example.gedune.bookcollection;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.List;

/**
 * Created by netease on 16/8/7.
 */
public class ExerciseApplication extends Application {
    private static ExerciseApplication instance;
    private static final int POLL_INTERVAL = 1 * 60 * 1000;


    public ExerciseApplication() {
        instance = this;
    }

    public void onCreate() {
        super.onCreate();
        if (!shouldInit()) {
            return;
        }
        AppProfile.init(this);
        ZXingLibrary.initDisplayOpinion(this);
    }


    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
