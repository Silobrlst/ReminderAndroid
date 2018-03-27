package com.example.qwe.test;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

public class CancelNextDays {
    int daysCountToCancel = 0;
    int daysLeft = 0;
    DateTime beginTime = new DateTime();
    boolean selfDestruct = true;

    DateTimeFormatter beginTimeFormat = DateTimeFormat.fullDateTime();

    CancelNextDays(){}

    void fromJson(JSONObject jsonIn){
        JsonUtil.validateJsonKey(jsonIn, JsonUtil.daysCountToCancelJsonName, 0);
        JsonUtil.validateJsonKey(jsonIn, JsonUtil.daysLeftJsonName, 0);
        JsonUtil.validateJsonKey(jsonIn, JsonUtil.beginDateJsonName, "");
        JsonUtil.validateJsonKey(jsonIn, JsonUtil.selfDestructJsonName, false);

        try{
            daysCountToCancel = jsonIn.getInt(JsonUtil.daysCountToCancelJsonName);
            daysLeft = jsonIn.getInt(JsonUtil.daysLeftJsonName);
            beginTime = beginTimeFormat.parseDateTime(jsonIn.getString(JsonUtil.beginDateJsonName));
            selfDestruct = jsonIn.getBoolean(JsonUtil.selfDestructJsonName);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    JSONObject toJson(){
        JSONObject json = new JSONObject();

        return json;
    }
}
