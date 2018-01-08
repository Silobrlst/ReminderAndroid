package com.example.qwe.test;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PeriodMonth implements Period{
    private ArrayList<Integer> daysOfMonth = new ArrayList<>();

    private PeriodWeek week1;
    private PeriodWeek week2;
    private PeriodWeek week3;
    private PeriodWeek week4;
    private PeriodWeek week5;

    private static final PeriodType periodType = PeriodType.Month;

    PeriodMonth(){
        week1 = new PeriodWeek();
        week2 = new PeriodWeek();
        week3 = new PeriodWeek();
        week4 = new PeriodWeek();
        week5 = new PeriodWeek();
    }

    PeriodMonth(JSONObject jsonIn){
        try{
            JSONArray daysOfMonthJson = jsonIn.getJSONArray("daysOfMonth");

            for(int i=0; i<daysOfMonthJson.length(); i++){
                daysOfMonth.add(daysOfMonthJson.getInt(i));
            }

            if (jsonIn.has("week1")) week1 = new PeriodWeek(jsonIn.getJSONObject("week1"));
            if (jsonIn.has("week2")) week2 = new PeriodWeek(jsonIn.getJSONObject("week2"));
            if (jsonIn.has("week3")) week3 = new PeriodWeek(jsonIn.getJSONObject("week3"));
            if (jsonIn.has("week4")) week4 = new PeriodWeek(jsonIn.getJSONObject("week4"));
            if (jsonIn.has("week5")) week5 = new PeriodWeek(jsonIn.getJSONObject("week5"));

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public PeriodType getPeriodType() {
        return periodType;
    }

    //<get>=========================================================================================
    public ArrayList<Integer> getDaysOfMonth() {
        return daysOfMonth;
    }

    public PeriodWeek getWeek1() {
        return week1;
    }
    public PeriodWeek getWeek2() {
        return week2;
    }
    public PeriodWeek getWeek3() {
        return week3;
    }
    public PeriodWeek getWeek4() {
        return week4;
    }
    public PeriodWeek getWeek5() {
        return week5;
    }
    //</get>========================================================================================

    public void setDaysOfMonth(ArrayList<Integer> daysOfMonthIn) {
        daysOfMonth = daysOfMonthIn;
    }

    @Override
    public boolean checkIsToday() {
        DateTime time = new DateTime();

        for(int day: daysOfMonth){
            if(day == time.getDayOfMonth()){
                return true;
            }
        }

        if(week1 != null){if (week1.checkIsToday()) return true;}
        if(week2 != null){if (week1.checkIsToday()) return true;}
        if(week3 != null){if (week1.checkIsToday()) return true;}
        if(week4 != null){if (week1.checkIsToday()) return true;}
        if(week5 != null){if (week1.checkIsToday()) return true;}

        return false;
    }

    @Override
    public void toJson(JSONObject jsonIn){
        try{
            JSONArray daysOfMonthJson = new JSONArray();

            for(int day: daysOfMonth){
                daysOfMonthJson.put(day);
            }

            jsonIn.put("type", periodType.toString());

            jsonIn.put("daysOfMonth", daysOfMonthJson);

            if (week1.getDaysOfWeek().size() > 0) jsonIn.put("week1", week1.toJson());
            if (week2.getDaysOfWeek().size() > 0) jsonIn.put("week2", week2.toJson());
            if (week3.getDaysOfWeek().size() > 0) jsonIn.put("week3", week3.toJson());
            if (week4.getDaysOfWeek().size() > 0) jsonIn.put("week4", week4.toJson());
            if (week5.getDaysOfWeek().size() > 0) jsonIn.put("week5", week5.toJson());

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
