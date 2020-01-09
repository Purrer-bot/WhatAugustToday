package com.govodrill.augusttoday;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.govodrill.augusttoday.date.DateCalculator;
import com.govodrill.augusttoday.notifications.NotificationReceiver;
import com.govodrill.augusttoday.preferences.ConstantsPreferences;
import com.govodrill.augusttoday.preferences.PreferencesManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvAugust;
    private ImageButton ibtNotify;

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    private PreferencesManager preferenceManager = new PreferencesManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAugust = findViewById(R.id.am_tv_august);
        ibtNotify = findViewById(R.id.am_bt_notify);


        setBackground();
        setNotificationIcon();

        int daysFromAugust = DateCalculator.getDaysFromAugust();

        String message = Integer.toString(daysFromAugust) + " августа";
        tvAugust.setText(message);

        ibtNotify.setOnClickListener(this);

        //Set notification on first time launch
//        setNotification();
        if(getFirstTimeLaunch()){
            setNotification();
            setFirstTimeLaunch(false);
        }
//        if(!checkNotification()){
//            setNotificationIcon();
//        }

    }

    //Start repeat notification. Notify every day 9 AM (must do this at least)
    private void setNotification(){
        //Set time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE,0);

        //Create intent
        Intent alarmIntent = new Intent(MainActivity.this, NotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 100, alarmIntent, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);


        //Overriding the manifest programmatically (TODO How this works?)
        ComponentName receiver = new ComponentName(MainActivity.this, NotificationReceiver.class);
        PackageManager pm = MainActivity.this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }
    private Boolean checkNotification(){
        Intent alarmIntent =  new Intent(MainActivity.this, NotificationManager.class);
        return PendingIntent.getBroadcast(this,
                100, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null;

    }

    private void cancelNotification(){
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        ComponentName receiver = new ComponentName(MainActivity.this, NotificationReceiver.class);
        PackageManager pm = MainActivity.this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    //Preferences methods
    private boolean getFirstTimeLaunch(){
        return preferenceManager.getBoolean(ConstantsPreferences.APP_FIRST_TIME_LAUNCH);
    }

    private void setFirstTimeLaunch(Boolean isFirstTime){
        preferenceManager.set(ConstantsPreferences.APP_FIRST_TIME_LAUNCH, isFirstTime);
    }
    private boolean getNotify(){
        return preferenceManager.getBoolean(ConstantsPreferences.APP_ENABLE_NOTIFICATION);
    }
    private void setNotify(boolean isNotify){
        preferenceManager.set(ConstantsPreferences.APP_ENABLE_NOTIFICATION, isNotify);
    }


    //Background sets depends on month
    private void setBackground(){
        int currentMonth = DateCalculator.getCurrentMonth();
        RelativeLayout layout = findViewById(R.id.RelativeLayout01);
        switch (currentMonth){
            case(Calendar.JANUARY):
            case(Calendar.FEBRUARY):
            case(Calendar.MARCH):
            case(Calendar.OCTOBER):
            case(Calendar.NOVEMBER):
            case(Calendar.DECEMBER):
                layout.setBackgroundResource(R.drawable.bg_am_autumn);
                break;
            case (Calendar.APRIL):
            case(Calendar.MAY):
            case(Calendar.JUNE):
            case(Calendar.JULY):
            case(Calendar.AUGUST):
            case(Calendar.SEPTEMBER):
                layout.setBackgroundResource(R.drawable.bg_am_summer);
                break;
        }

    }
    private void setNotificationIcon(){
        if(getNotify()){
            ibtNotify.setImageResource(R.drawable.icon_bell);
        }
        else{
            ibtNotify.setImageResource(R.drawable.icon_bell_dis);
        }
    }

    //Click listener. Enables and disables notifications
    @Override
    public void onClick(View v) {
        boolean isEnable = getNotify();
        if(isEnable){
            ibtNotify.setImageResource(R.drawable.icon_bell_dis);
            setNotify(false);
            cancelNotification();
            Toast.makeText(this, "Отключено", Toast.LENGTH_SHORT).show();
        }
        else{
            ibtNotify.setImageResource(R.drawable.icon_bell);
            setNotify(true);
            setNotification();
            Toast.makeText(this, "Фключено", Toast.LENGTH_SHORT).show();
        }
    }
}
