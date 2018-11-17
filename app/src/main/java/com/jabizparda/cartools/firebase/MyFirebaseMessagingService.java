package com.jabizparda.cartools.firebase;

/**
 * Created by Karo on 6/21/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Log.e("Karo","KArooooooooooooo");
//        Logger.init();
        Logger.e(remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pref.edit().putBoolean("RATING", true).apply();
        pref.edit().putString("karooo","karo").apply();
        Logger.d("karow");
//        Logger.e(remoteMessage.toString());
//        Eve.getInstance(getApplicationContext()).setRating(true, getApplicationContext());
//
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().get("payload").toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
//        Log.e(TAG, "push json: " + json.toString());
//
//        try {
//            JSONObject data = json.getJSONObject("data");
//
//            String title = data.getString("title");
//            String message = data.getString("message");
//            boolean isBackground = data.getBoolean("is_background");
//            String imageUrl = data.getString("image");
//            String timestamp = data.getString("timestamp");
//            JSONObject payload = data.getJSONObject("payload");
//
//            Log.e(TAG, "title: " + title);
//            Log.e(TAG, "message: " + message);
//            Log.e(TAG, "isBackground: " + isBackground);
//            Log.e(TAG, "payload: " + payload.toString());
//            Log.e(TAG, "imageUrl: " + imageUrl);
//            Log.e(TAG, "timestamp: " + timestamp);
//
//
//            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//                // app is in foreground, broadcast the push message
//                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//                pushNotification.putExtra("message", message);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                // play notification sound
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSound();
//            } else {
//                // app is in background, show the notification in notification tray
//                Intent resultIntent = new Intent(getApplicationContext(), ScannerActivity.class);
//                resultIntent.putExtra("message", message);
//
//                // check for image attachment
//                if (TextUtils.isEmpty(imageUrl)) {
//                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//                } else {
//                    // image is present, show notification with image
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
//        }
//        Logger.json(json.toString());
//        JsonObject result = new Gson().fromJson(json.toString(), JsonObject.class);
//        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//
//        if(result.has("call")){
//            Log.i("call","is");
//            JsonObject payload = result.get("call").getAsJsonObject();
//            if(payload.has("sound")){
//                String sound = payload.get("sound").getAsString();
//                if(sound.equals("1") || sound.equals("2") || sound.equals("3") || sound.equals("4")){
////                    notificationUtils.playNotificationSound(sound);
//                }
//            }
//            Intent dialogIntent = new Intent(this, DialogActivity.class);
//            if(NotificationUtils.isAppIsInBackground(getApplicationContext())){
//                String message = result.get("message").getAsString();
//
//                String title = result.get("title").getAsString();
//
//                String timestamp = result.get("timestamp").getAsString();
//                notificationUtils.showNotificationMessage(title,message,timestamp, new Intent());
//            }
////            dialogIntent.putExtra("message", result.get("message").getAsString());
////            dialogIntent.putExtra("title", result.get("title").getAsString());
//
//            dialogIntent.putExtra(
//                    "showText", payload.get("plaque").getAsString());
//            if(NotificationUtils.isAppIsInBackground(getApplicationContext())){
//                dialogIntent.putExtra("Action", "back");
//            } else {
//                dialogIntent.putExtra("Action", "open");
//            }
//            dialogIntent.putExtra("type", "call");
//            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(dialogIntent);
//            Logger.d(payload);
//        } else if(result.has("price")){
//            JsonObject payload = result.get("price").getAsJsonObject();
////            AppDatabase.getAppDatabase(this).personDao().insert(person);
////            JsonObject destination = result.get("destination").getAsJsonObject();
////            person=AppDatabase.getAppDatabase(this).personDao().getPerson();
////            person.setDestinStatus(destination.get("status").getAsString());
//            Logger.d(payload);
//        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        Logger.d("showNotificationMessage");
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}