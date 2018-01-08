package com.example.qwe.test;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PeriodWeek implements Period{
    private ArrayList<Integer> daysOfWeek = new ArrayList<>();

    private static final PeriodType periodType = PeriodType.Week;

    PeriodWeek(){

    }

    PeriodWeek(JSONObject jsonIn){
        try{
            JSONArray json = jsonIn.getJSONArray("daysOfWeek");

            for(int i=0; i<json.length(); i++){
                daysOfWeek.add(json.getInt(i));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public PeriodType getPeriodType() {
        return periodType;
    }

    public ArrayList<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(ArrayList<Integer> daysIn) {
        daysOfWeek = daysIn;
    }

    @Override
    public boolean checkIsToday() {
        DateTime time = new DateTime();

        for(int day: daysOfWeek){
            if(day == time.getDayOfWeek()){
                return true;
            }
        }

        return false;
    }

    @Override
    public void toJson(JSONObject jsonIn){
        try{
            JSONArray json = new JSONArray();

            for(int day: daysOfWeek){
                json.put(day);
            }

            jsonIn.put("type", periodType.toString());

            jsonIn.put("daysOfWeek", json);

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
