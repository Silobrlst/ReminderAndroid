package com.example.qwe.test;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class Reminder extends IntentService {
    Loader loader = new Loader();

    public Reminder() {
        super("Reminder");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        RemindsContainer remindsContainer = new RemindsContainer(Reminder.this);
        loader.loadReminds(Reminder.this, remindsContainer);

        final RemindsContainer remindsToday = new RemindsContainer(Reminder.this);
        final RemindsContainer remindsTodayTemp = new RemindsContainer(Reminder.this);
        for (int i = 0; i < remindsContainer.getCount(); i++) {
            if (remindsContainer.getItem(i).checkRemindToday()) {
                remindsToday.add(remindsContainer.getItem(i));
            }
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                remindsTodayTemp.clear();
                for(int i=0; i<remindsToday.getCount(); i++){
                    remindsTodayTemp.add(remindsToday.getItem(i));
                }

                for(int i=0; i<remindsTodayTemp.getCount(); i++){
                    Remind remind = remindsTodayTemp.getItem(i);

                    if(remind.checkTimeIsNow()){
                        Intent remindActivity = new Intent(Reminder.this, RemindActivity.class);
                        remindActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        remindActivity.putExtra("text", remind.getText());
                        startActivity(remindActivity);

                        remindsToday.remove(remind);
                    }
                }
            }
        }, 0, 1000);
    }
}
