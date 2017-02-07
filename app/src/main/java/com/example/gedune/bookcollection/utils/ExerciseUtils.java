package com.example.gedune.bookcollection.utils;

import com.example.gedune.bookcollection.AppProfile;

/**
 * Created by netease on 16/11/8.
 */

public class ExerciseUtils {


    public static int dp2px(float dipValue){
        return (int)(dipValue * AppProfile.getContext().getResources().getDisplayMetrics().density + 0.5f);
    }



}
