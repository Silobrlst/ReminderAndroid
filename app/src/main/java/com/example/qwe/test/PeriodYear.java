package com.example.qwe.test;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PeriodYear implements Period{
    private ArrayList<Integer> daysOfYear = new ArrayList<>();

    private PeriodMonth jan;
    private PeriodMonth feb;
    private PeriodMonth mar;
    private PeriodMonth apr;
    private PeriodMonth may;
    private PeriodMonth jun;
    private PeriodMonth jul;
    private PeriodMonth aug;
    private PeriodMonth sep;
    private PeriodMonth oct;
    private PeriodMonth nov;
    private PeriodMonth dec;

    private static final PeriodType periodType = PeriodType.Year;

    PeriodYear(){
        jan = new PeriodMonth();
        feb = new PeriodMonth();
        mar = new PeriodMonth();
        apr = new PeriodMonth();
        may = new PeriodMonth();
        jun = new PeriodMonth();
        jul = new PeriodMonth();
        aug = new PeriodMonth();
        sep = new PeriodMonth();
        oct = new PeriodMonth();
        nov = new PeriodMonth();
        dec = new PeriodMonth();
    }
    PeriodYear(JSONObject jsonIn){
        try{
            JSONArray daysOfYearJson = jsonIn.getJSONArray("daysOfYear");

            for(int i=0; i<daysOfYearJson.length(); i++){
                daysOfYear.add(daysOfYearJson.getInt(i));
            }

            if (jsonIn.has("jan")) jan = new PeriodMonth(jsonIn.getJSONObject("jan"));
            if (jsonIn.has("feb")) feb = new PeriodMonth(jsonIn.getJSONObject("feb"));
            if (jsonIn.has("mar")) mar = new PeriodMonth(jsonIn.getJSONObject("mar"));
            if (jsonIn.has("apr")) apr = new PeriodMonth(jsonIn.getJSONObject("apr"));
            if (jsonIn.has("may")) may = new PeriodMonth(jsonIn.getJSONObject("may"));
            if (jsonIn.has("jun")) jun = new PeriodMonth(jsonIn.getJSONObject("jun"));
            if (jsonIn.has("jul")) jul = new PeriodMonth(jsonIn.getJSONObject("jul"));
            if (jsonIn.has("aug")) aug = new PeriodMonth(jsonIn.getJSONObject("aug"));
            if (jsonIn.has("sep")) sep = new PeriodMonth(jsonIn.getJSONObject("sep"));
            if (jsonIn.has("oct")) oct = new PeriodMonth(jsonIn.getJSONObject("oct"));
            if (jsonIn.has("nov")) nov = new PeriodMonth(jsonIn.getJSONObject("nov"));
            if (jsonIn.has("dec")) dec = new PeriodMonth(jsonIn.getJSONObject("dec"));

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public PeriodType getPeriodType() {
        return periodType;
    }

    public ArrayList<Integer> getDaysOfYear() {
        return daysOfYear;
    }

    public PeriodMonth getJan() {
        return jan;
    }
    public PeriodMonth getFeb() {
        return feb;
    }
    public PeriodMonth getMar() {
        return mar;
    }
    public PeriodMonth getApr() {
        return apr;
    }
    public PeriodMonth getMay() {
        return may;
    }
    public PeriodMonth getJun() {
        return jun;
    }
    public PeriodMonth getJul() {
        return jul;
    }
    public PeriodMonth getAug() {
        return aug;
    }
    public PeriodMonth getSep() {
        return sep;
    }
    public PeriodMonth getOct() {
        return oct;
    }
    public PeriodMonth getNov() {
        return nov;
    }
    public PeriodMonth getDec() {
        return dec;
    }

    public void setDaysOfYear(ArrayList<Integer> daysOfYearIn) {
        daysOfYear = daysOfYearIn;
    }

    @Override
    public boolean checkIsToday() {
        if(jan != null){if (jan.checkIsToday()) return true;}
        if(feb != null){if (feb.checkIsToday()) return true;}
        if(mar != null){if (mar.checkIsToday()) return true;}
        if(apr != null){if (apr.checkIsToday()) return true;}
        if(may != null){if (may.checkIsToday()) return true;}
        if(jun != null){if (jun.checkIsToday()) return true;}
        if(jul != null){if (jul.checkIsToday()) return true;}
        if(aug != null){if (aug.checkIsToday()) return true;}
        if(sep != null){if (sep.checkIsToday()) return true;}
        if(oct != null){if (oct.checkIsToday()) return true;}
        if(nov != null){if (nov.checkIsToday()) return true;}
        if(dec != null){if (dec.checkIsToday()) return true;}

        DateTime time = new DateTime();

        for(int day: daysOfYear){
            if(day == time.getDayOfWeek()){
                return true;
            }
        }

        return false;
    }

    @Override
    public void toJson(JSONObject jsonIn){
        try{
            JSONArray daysOfYearJson = new JSONArray();

            for(int day: daysOfYear){
                daysOfYearJson.put(day);
            }

            jsonIn.put("type", periodType.toString());

            jsonIn.put("daysOfYear", daysOfYearJson);

            if (jan.getDaysOfMonth().size() > 0) jsonIn.put("jan", jan.toJson());
            if (feb.getDaysOfMonth().size() > 0) jsonIn.put("feb", feb.toJson());
            if (mar.getDaysOfMonth().size() > 0) jsonIn.put("mar", mar.toJson());
            if (apr.getDaysOfMonth().size() > 0) jsonIn.put("apr", apr.toJson());
            if (may.getDaysOfMonth().size() > 0) jsonIn.put("may", may.toJson());
            if (jun.getDaysOfMonth().size() > 0) jsonIn.put("jun", jun.toJson());
            if (jul.getDaysOfMonth().size() > 0) jsonIn.put("jul", jul.toJson());
            if (aug.getDaysOfMonth().size() > 0) jsonIn.put("aug", aug.toJson());
            if (sep.getDaysOfMonth().size() > 0) jsonIn.put("sep", sep.toJson());
            if (oct.getDaysOfMonth().size() > 0) jsonIn.put("oct", oct.toJson());
            if (nov.getDaysOfMonth().size() > 0) jsonIn.put("nov", nov.toJson());
            if (dec.getDaysOfMonth().size() > 0) jsonIn.put("dec", dec.toJson());

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
