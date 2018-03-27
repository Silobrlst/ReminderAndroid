package com.example.qwe.test;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class Loader {
    private static final String remindsJsonName = "reminds";

    private static final String textJsonName = "text";
    private static final String soundJsonName = "sound";
    private static final String minuteJsonName = "minute";
    private static final String hourJsonName = "hour";
    private static final String periodJsonName = "period";

    private static final String configFileName = "config.json";

    private void validateJsonKey(JSONObject jsonObjectIn, String nameIn, Object defaultIn){
        if(!jsonObjectIn.has(nameIn)){
            try{
                jsonObjectIn.put(nameIn, defaultIn);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    private void validateRemindJson(JSONObject remindJsonIn){
        validateJsonKey(remindJsonIn, textJsonName, "");
        validateJsonKey(remindJsonIn, minuteJsonName, 0);
        validateJsonKey(remindJsonIn, hourJsonName, 0);
        validateJsonKey(remindJsonIn, periodJsonName, PeriodType.OneTime);
    }
    private void validateConfigJson(JSONObject configJsonIn){
        validateJsonKey(configJsonIn, remindsJsonName, new JSONObject());


        try{
            JSONArray remindsJson = configJsonIn.getJSONArray(remindsJsonName);

            for(int i=0; i<remindsJson.length(); i++){
                validateRemindJson(remindsJson.getJSONObject(i));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void loadReminds(Context contextIn, RemindsContainer remindsIn){
        JSONObject configJson = JsonLoader.loadJSON(contextIn, configFileName);
        validateConfigJson(configJson);

        try{
            remindsIn.fromJson(configJson.getJSONObject(remindsJsonName));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void saveReminds(Context contextIn, RemindsContainer remindsIn){
        JSONObject configJson = new JSONObject();
        validateConfigJson(configJson);

        try{
            configJson.put(remindsJsonName, remindsIn.toJson());

            JsonLoader.saveJSON(contextIn, configFileName, configJson);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void removeRemindById(Context contextIn, String idIn){
        JSONObject configJson = JsonLoader.loadJSON(contextIn, configFileName);
        validateConfigJson(configJson);

        try{
            JSONObject remindsJson = configJson.getJSONObject(remindsJsonName);

            if(remindsJson.has(idIn)){
                remindsJson.remove(idIn);
            }

            JsonLoader.saveJSON(contextIn, configFileName, configJson);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static String getConfigFileName() {
        return configFileName;
    }
}
