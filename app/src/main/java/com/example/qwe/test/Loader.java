package com.example.qwe.test;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.UUID;

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

    private void loadRemind(JSONObject remindJsonIn, Remind remindIn){
        validateRemindJson(remindJsonIn);

        try{
            remindIn.setTime(remindJsonIn.getInt(hourJsonName), remindJsonIn.getInt(minuteJsonName));
            remindIn.setText(remindJsonIn.getString(textJsonName));
            remindIn.setPeriodType(PeriodType.valueOf(remindJsonIn.getString(periodJsonName)));

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private Remind loadRemind(JSONObject remindJsonIn){
        Remind remind = new Remind();
        loadRemind(remindJsonIn, remind);
        return remind;
    }
    public void loadReminds(Context contextIn, RemindsContainer remindsIn){
        JSONObject configJson = JsonLoader.loadJSON(contextIn, configFileName);
        validateConfigJson(configJson);

        try{
            JSONObject remindsJson = configJson.getJSONObject(remindsJsonName);

            JSONArray ids = remindsJson.names();
            if(ids != null){
                for(int i=0; i<ids.length(); i++){
                    Remind remind = loadRemind(remindsJson.getJSONObject(ids.getString(i)));
                    remind.setId(ids.getString(i));

                    remindsIn.add(remind);
                }
            }

            remindsIn.notifyDataSetChanged();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void loadRemindById(Context contextIn, String idIn, Remind remindIn){
        JSONObject configJson = JsonLoader.loadJSON(contextIn, configFileName);
        validateConfigJson(configJson);

        try{
            JSONObject remindsJson = configJson.getJSONObject(remindsJsonName);

            JSONArray ids = remindsJson.names();
            if(ids != null){
                for(int i=0; i<ids.length(); i++){
                    if(idIn.equals(ids.getString(i))){
                        loadRemind(remindsJson.getJSONObject(ids.getString(i)), remindIn);
                        remindIn.setId(idIn);
                    }
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public Remind loadRemindById(Context contextIn, String idIn){
        Remind remind = new Remind();
        loadRemindById(contextIn, idIn, remind);
        return remind;
    }

    private JSONObject saveRemind(Remind remindIn){
        JSONObject remindJson = new JSONObject();

        try{
            remindJson.put(textJsonName, remindIn.getText());
            remindJson.put(periodJsonName, remindIn.getPeriodType());
            remindJson.put(hourJsonName, remindIn.getHour());
            remindJson.put(minuteJsonName, remindIn.getMinute());

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return remindJson;
    }
    public void saveReminds(Context contextIn, RemindsContainer remindsIn){
        JSONObject configJson = new JSONObject();

        try{
            JSONObject remindsJson = configJson.getJSONObject(remindsJsonName);

            for(int i=0; i<remindsIn.getCount(); i++){
                remindsJson.put(remindsIn.getItem(i).getId(), saveRemind(remindsIn.getItem(i)));
            }

            configJson.put(remindsJsonName, remindsJson);

            JsonLoader.saveJSON(contextIn, configFileName, configJson);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //сохраняет одну напомниалку в файл
    public void saveRemindOnly(Context contextIn, Remind remindIn){
        JSONObject configJson = JsonLoader.loadJSON(contextIn, configFileName);
        validateConfigJson(configJson);

        try{
            JSONObject remindsJson = configJson.getJSONObject(remindsJsonName);

            remindsJson.put(remindIn.getId(), saveRemind(remindIn));

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
