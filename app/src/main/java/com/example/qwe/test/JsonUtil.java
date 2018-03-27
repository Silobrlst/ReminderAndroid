package com.example.qwe.test;

import org.json.JSONObject;

public class JsonUtil {
    public static final String reminds = "reminds";

    public static final String text = "text";
    public static final String sound = "sound";
    public static final String minute = "minute";
    public static final String hour = "hour";
    public static final String period = "period";

    public static final String daysCountToCancelJsonName = "daysCountToCancel";
    public static final String daysLeftJsonName = "daysLeft";
    public static final String beginDateJsonName = "beginTime";
    public static final String selfDestructJsonName = "selfDestruct";

    public static void validateJsonKey(JSONObject jsonObjectIn, String nameIn, Object defaultIn){
        if(!jsonObjectIn.has(nameIn)){
            try{
                jsonObjectIn.put(nameIn, defaultIn);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
