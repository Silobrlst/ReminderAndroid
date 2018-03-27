package com.example.qwe.test;

import android.util.Log;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.util.UUID;

public class Remind {
    private static final String textJsonName = "text";
    private static final String minuteJsonName = "minute";
    private static final String hourJsonName = "hour";
    private static final String periodJsonName = "period";

    //TODO: sounds
    private static final String soundJsonName = "sound";

    private String id;
    private String text;
    private boolean selfDestroy = false;

    private Period period = null;
    private DateTime time = new DateTime();

    public Remind(){
        id = UUID.randomUUID().toString();
    }
    public Remind(String idIn, JSONObject remindJsonIn){
        id = idIn;
        fromJson(remindJsonIn);
    }

    //<set>=========================================================================================
    public void setId(String idIn) {
        id = idIn;
    }

    public void setText(String textIn) {
        text = textIn;
    }

    public void setTime(int hourIn, int minuteIn){
        DateTime t = new DateTime();
        time = new DateTime(t.getYear(), t.getMonthOfYear(), t.getDayOfMonth(), hourIn, minuteIn);
    }

    public void setPeriod(Period periodIn) {
        period = periodIn;
    }
    public void setPeriod(JSONObject periodJsonIn){
        try{
            if(periodJsonIn.has("type")){
                switch (PeriodType.valueOf(periodJsonIn.getString("type"))){
                    case OneTime:
                        period = new PeriodOneTime(periodJsonIn);
                        break;
                    case Week:
                        period = new PeriodWeek(periodJsonIn);
                        break;
                    case Month:
                        period = new PeriodMonth(periodJsonIn);
                        break;
                    case Year:
                        period = new PeriodYear(periodJsonIn);
                        break;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void setSelfDestroy(boolean selfDestroyIn) {
        selfDestroy = selfDestroyIn;
    }
    //</set>========================================================================================

    //<get>=========================================================================================
    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public DateTime getTime() {
        return time;
    }

    public Period getPeriod() {
        return period;
    }

    int getMinute(){
        return time.getMinuteOfHour();
    }
    int getHour(){
        return time.getHourOfDay();
    }

    public boolean getSelfDestroy(){
        return selfDestroy;
    }
    //</get>========================================================================================

    public boolean checkRemindToday(){
        DateTime now = new DateTime();

        if(now.getMinuteOfHour() > time.getMinuteOfHour() && now.getHourOfDay() > now.getHourOfDay()){
            return false;
        }

        if(period == null){
            return false;
        }

        return period.checkIsToday();
    }
    public boolean checkTimeIsNow(){
        DateTime now = new DateTime();
        return now.getMinuteOfHour() == time.getMinuteOfHour() && now.getHourOfDay() == now.getHourOfDay();
    }

    public JSONObject toJson(){
        JSONObject remindJson = new JSONObject();

        try{
            remindJson.put(textJsonName, text);
            remindJson.put(hourJsonName, getHour());
            remindJson.put(minuteJsonName, getMinute());

            if(period != null){
                Log.i("myTag", "fjgdlkfjg");
                remindJson.put(periodJsonName, period.toJson());
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return remindJson;
    }
    public void fromJson(JSONObject remindJsonIn){
        //<validation>==============================================================================
        JsonUtil.validateJsonKey(remindJsonIn, JsonUtil.text, "");
        JsonUtil.validateJsonKey(remindJsonIn, JsonUtil.minute, 0);
        JsonUtil.validateJsonKey(remindJsonIn, JsonUtil.hour, 0);
        JsonUtil.validateJsonKey(remindJsonIn, JsonUtil.period, PeriodType.OneTime);
        //</validation>=============================================================================

        try{
            setTime(remindJsonIn.getInt(hourJsonName), remindJsonIn.getInt(minuteJsonName));
            setText(remindJsonIn.getString(textJsonName));
            setPeriod(remindJsonIn.getJSONObject(periodJsonName));

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
