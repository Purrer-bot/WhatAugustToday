package com.govodrill.augusttoday.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.govodrill.augusttoday.MainActivity;
import com.govodrill.augusttoday.R;
import com.govodrill.augusttoday.date.DateCalculator;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        setNotification(context, DateCalculator.getDaysFromAugust());
    }

    /* Set notification for how many days from august (TODO maybe to another class?) */
    private void setNotification(Context context, int days){
        String notifyMessage = "Сегодня " + days + " августа";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, ConstantsNotification.NOTIFY_CHANNEL)
                        .setContentTitle("Какой сегодня день")
                        .setContentText(notifyMessage)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setLights(0xff0000, 1000, 1000)
                        .setSmallIcon(R.drawable.ic_small_icon)
                        .setContentIntent(launchMainActivity(context));
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(ConstantsNotification.NOTIFY_ID, notification);

    }
    //Intent for opening activity on click on notification
    private PendingIntent launchMainActivity(Context context){
//        Intent intent = new Intent(context, MainActivity.class);
        Intent intent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(context,
                100 ,intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
