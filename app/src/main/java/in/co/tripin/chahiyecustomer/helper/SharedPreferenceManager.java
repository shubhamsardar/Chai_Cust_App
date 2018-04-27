package in.co.tripin.chahiyecustomer.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SharedPreferenceManager {
    public static final String MyPREFERENCES = "Chaihiye";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Context ctx;

    public boolean isLogin(){
        if(TextUtils.isEmpty(getStringPrefrence("sign_user_id"))){
            return false;
        }else {
            return true;
        }
    }

    public SharedPreferenceManager(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        this.ctx = ctx;
    }

    public void setStringPrefrence(String strKey, String strValue) {
        editor.putString(strKey, strValue);
        editor.commit();
    }

    public String getStringPrefrence(String strKey) {
        String strValue = sharedpreferences.getString(strKey, "");
        return strValue;
    }

    public void setBoolPrefrence(String strKey, boolean boolValue) {
        editor.putBoolean(strKey, boolValue);
        editor.commit();
    }

    public boolean getBoolPrefrence(String strKey) {
        boolean boolValue = sharedpreferences.getBoolean(strKey, false);
        return boolValue;
    }

    public boolean getBoolNotiPrefrence(String strKey) {
        boolean boolValue = sharedpreferences.getBoolean(strKey, true);
        return boolValue;
    }

    public void clearAllPreferences() {
        editor.clear();
        editor.commit();
    }
}
