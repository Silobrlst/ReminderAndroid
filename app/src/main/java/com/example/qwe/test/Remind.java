package com.example.qwe.test;

import android.util.Log;

import org.joda.time.DateTime;

import java.util.UUID;

public class Remind {
    private String id;
    private String text;
    private PeriodType periodType = PeriodType.OneTime;
    private boolean selfDestroy = false;

    private DateTime time = new DateTime();

    public Remind(){
        id = UUID.randomUUID().toString();
    }

    public void setText(String textIn) {
        text = textIn;
    }

    public String getText() {
        return text;
    }

    public void setTime(int hourIn, int minuteIn){
        DateTime t = new DateTime();
        time = new DateTime(t.getYear(), t.getMonthOfYear(), t.getDayOfMonth(), hourIn, minuteIn);
    }

    public boolean checkTimeIsCurrent(){
        return time.isEqualNow();
    }

    public void setSelfDestroy(boolean selfDestroyIn) {
        selfDestroy = selfDestroyIn;
    }

    public boolean getSelfDestroy(){
        return selfDestroy;
    }

    public String getId() {
        return id;
    }

    public void setId(String idIn) {
        id = idIn;
    }

    public void setPeriodType(PeriodType periodTypeIn) {
        periodType = periodTypeIn;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    int getMinute(){
        return time.getMinuteOfHour();
    }

    int getHour(){
        return time.getHourOfDay();
    }
}
