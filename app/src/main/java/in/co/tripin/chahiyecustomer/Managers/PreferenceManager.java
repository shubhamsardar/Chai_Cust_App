package in.co.tripin.chahiyecustomer.Managers;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Ravishankar Ahirwar on 12/2/2016.
 */

public class PreferenceManager {
    public static final String PREF_MOBILE_NUMBER = "partner_mobile_no";
    public static final String PREF_USER_NAME = "partner_user_name";
    public static final String PREF_TRIPIN_ID = "partner_tripin_id";
    public static final String PREF_LOGIN_STATUS = "partner_login_status";
    private static final String PREF_FILE_NAME = "partner_preference";
    private static final String PREF_ACCESS_TOKEN = "partner_access_token";
    private static final String PREF_USER_ID = "partner_user_id";
    private static final String PREF_TEMP_USER_ID = "partner_temp_user_id";
    private static final String PREF_FCM_ID = "partner_fcm_id";
    private static final String PREF_ENQUIRY_ID = "enquiry_id";
    private static final String PREF_MAP_STATUS = "map_status";
    private static final String PREF_EMAIL_ID = "email_id";
    private static final String PREF_COMPANY_NAME = "compamy_name";
    private static final String PREF_PROFILE_IMG = "profile_img";
    private static final String PREF_DEFAULT_ADDRESS = "default_address";


    private static SharedPreferences sInstance;
    private static SharedPreferences.Editor editor;
    private static PreferenceManager mSPreferenceManager;

    private PreferenceManager() {
    }

    /**
     * @param context
     * @return
     */
    public static synchronized PreferenceManager getInstance(Context context) {
        if (mSPreferenceManager == null) {
//            sInstance = PreferenceManager.getDefaultSharedPreferences(context);
            sInstance = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
            editor = sInstance.edit();
            mSPreferenceManager = new PreferenceManager();
        }
        return mSPreferenceManager;
    }

    public String getValueFromSharedPreference(String key, String value) {
        String retrivedValue = sInstance.getString(key, value);
        return retrivedValue;
    }

    public String getValueFromSharedPreference(String key) {
        return getValueFromSharedPreference(key, "");
    }

    public void setValueToSharedPreference(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void clearAllDataFromSharedPrefernces() {
        editor.clear();
        editor.commit();
    }

    /**
     * @return
     */
    public String getAccessToken() {
        String accessToken = sInstance.getString(PREF_ACCESS_TOKEN, null);
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwZXJzb25JZCI6IjViMjM3MjllYjc3ZDBkMDAxNTU0NWU1OSIsImV4cGlyZXMiOjE1Mjk2NjE2OTk5Mzl9.hD3OhVl_rQOJVH6r6rS2UEx6-PLQDQ4cIljyzwvzfPw";
    }

    /**
     * @param accessToken
     */
    public void setAccessToken(String accessToken) {
        editor.putString(PREF_ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    /**
     * @return
     */
    public String getUserId() {
        String userId = sInstance.getString(PREF_USER_ID, null);
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        editor.putString(PREF_USER_ID, userId);
        editor.commit();
    }

    /**
     * @return
     */
    public String getTempUserId() {
        String userId = sInstance.getString(PREF_TEMP_USER_ID, null);
        return userId;
    }

    /**
     * @param userId
     */
    public void setTempUserId(String userId) {
        editor.putString(PREF_TEMP_USER_ID, userId);
        editor.commit();
    }

    /**
     * @return
     */
    public boolean isLogin() {
        String userId = sInstance.getString(PREF_USER_ID, null);

        if (userId == null || userId.isEmpty() == true) {
            return false;
        } else {
            return true;
        }
    }

    public String getMobileNo() {
        String mobileNo = sInstance.getString(PREF_MOBILE_NUMBER, null);
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        editor.putString(PREF_MOBILE_NUMBER, mobileNo);
        editor.commit();
    }

    public String getUserName() {
        String userName = sInstance.getString(PREF_USER_NAME, null);
        return userName;
    }

    public void setUserName(String userName) {
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public String getTripinId() {
        String tripinId = sInstance.getString(PREF_TRIPIN_ID, null);
        return tripinId;
    }

    public void setTripinId(String tripinId) {
        editor.putString(PREF_TRIPIN_ID, tripinId);
        editor.commit();
    }

    public String getGCMId() {
        String fcmId = sInstance.getString(PREF_FCM_ID, null);
        return fcmId;
    }

    public void setGCMId(String fcmId) {
        editor.putString(PREF_FCM_ID, fcmId);
        editor.commit();
    }

    public String getEnquiryId() {
        String enquiryId = sInstance.getString(PREF_ENQUIRY_ID, null);
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        editor.putString(PREF_ENQUIRY_ID, enquiryId);
        editor.commit();
    }

    public void clearLoginPreferences() {
        //setting GCM id before clearing since it would be required when user sign's in again.
        String gcmId = getGCMId();
        String accessToken = getAccessToken();
        editor.clear();
        setGCMId(gcmId);
        setAccessToken(accessToken);
        editor.commit();
    }

    public String getMapStatus() {
        String mapStatus = sInstance.getString(PREF_MAP_STATUS, null);
        return mapStatus;
    }

    public void setMapStatus(String mapStatus) {
        editor.putString(PREF_MAP_STATUS, mapStatus);
        editor.commit();
    }

    public String getEmailId() {
        String tripinId = sInstance.getString(PREF_EMAIL_ID, null);
        return tripinId;
    }

    public void setEmailId(String tripinId) {
        editor.putString(PREF_EMAIL_ID, tripinId);
        editor.commit();
    }

    public String getCompanyName() {
        String tripinId = sInstance.getString(PREF_COMPANY_NAME, null);
        return tripinId;
    }

    public void setCompanyName(String tripinId) {
        editor.putString(PREF_COMPANY_NAME, tripinId);
        editor.commit();
    }

    public String getProfileImgPath() {
        String tripinId = sInstance.getString(PREF_PROFILE_IMG, null);
        return tripinId;
    }

    public void setProfileImgPath(String profileImg) {
        editor.putString(PREF_PROFILE_IMG, profileImg);
        editor.commit();
    }

    public String getDefaultAddress() {
        String address = sInstance.getString(PREF_DEFAULT_ADDRESS, null);
        return address;
    }

    public void setDefaultAddress(String address) {
        editor.putString(PREF_DEFAULT_ADDRESS, address);
        editor.commit();
    }

}
