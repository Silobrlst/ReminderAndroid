package com.example.qwe.test;

import org.json.JSONObject;

public class ValidateJson {
    private static void validateJsonKey(JSONObject jsonObjectIn, String nameIn, Object defaultIn){
        if(!jsonObjectIn.has(nameIn)){
            try{
                jsonObjectIn.put(nameIn, defaultIn);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static void remind(JSONObject remindJsonIn){
        validateJsonKey(remindJsonIn, JsonNames.text, "");
        validateJsonKey(remindJsonIn, JsonNames.minute, 0);
        validateJsonKey(remindJsonIn, JsonNames.hour, 0);
        validateJsonKey(remindJsonIn, JsonNames.period, PeriodType.OneTime);
    }
}
