package in.co.tripin.chahiyecustomer.notifications;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;


public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public static final String TAG = "InstanceIdService";
    private PreferenceManager preferenceManager;
    @Override
    public void onTokenRefresh() {
        preferenceManager = PreferenceManager.getInstance(getApplicationContext());
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        preferenceManager.setFCMId(refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
        super.onTokenRefresh();
    }
}
