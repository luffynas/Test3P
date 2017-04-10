package com.luffycode.test3p.helper;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by MacMini on 4/10/17.
 */

public class Utils {

    public static boolean isValidateEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidateName(String name){
        return !TextUtils.isEmpty(name) && name.length() > 4;
    }

    public static void requestFocus(Context mContext, View view){
        if (view.requestFocus()){
            Activity activity = (Activity) mContext;
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
