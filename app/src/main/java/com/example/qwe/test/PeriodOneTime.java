package com.example.qwe.test;

import org.joda.time.DateTime;
import org.json.JSONObject;

public class PeriodOneTime implements Period{
    private int day = 1;
    private int month = 1;
    private int year = 1970;

    private boolean selfDestruction = false;

    private static final PeriodType periodType = PeriodType.OneTime;

    PeriodOneTime(){

    }
    PeriodOneTime(JSONObject jsonIn){
        try{
            day = jsonIn.getInt("day");
            month = jsonIn.getInt("month");
            year = jsonIn.getInt("year");

            selfDestruction = jsonIn.getBoolean("selfDestruct");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public PeriodType getPeriodType() {
        return periodType;
    }

    public int getDay() {
        return day;
    }
    public int getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }

    boolean getSelfDestruction(){
        return selfDestruction;
    }

    public void setDay(int day) {
        this.day = day;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public void setYear(int year) {
        this.year = year;
    }

    void setSelfDestruction(boolean selfDestructionIn){
        selfDestruction = selfDestructionIn;
    }

    @Override
    public boolean checkIsToday() {
        DateTime time = new DateTime();

        if(day == time.getDayOfMonth() && month == time.getMonthOfYear() && year == time.getYear()){
            return true;
        }

        return false;
    }

    @Override
    public void toJson(JSONObject jsonIn){
        try{
            jsonIn.put("type", periodType.toString());

            jsonIn.put("day", day);
            jsonIn.put("month", month);
            jsonIn.put("year", year);

            jsonIn.put("selfDestruct", selfDestruction);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        toJson(json);
        return json;
    }
}
