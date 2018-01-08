package com.example.qwe.test;

import java.util.ArrayList;

public class Common {
    private static final Common ourInstance = new Common();

    public static Common getInstance() {
        return ourInstance;
    }

    private Common() {
    }

    public static String daysToString(ArrayList<Integer> daysIn){
        if(daysIn.size() == 0){
            return "";
        }

        String days = Integer.toString(daysIn.get(0));

        for(int i=1; i<daysIn.size(); i++){
            days += ", " + Integer.toString(daysIn.get(i));
        }

        return days;
    }

    public static ArrayList<Integer> parseDays(String daysIn){
        daysIn = daysIn.trim();

        ArrayList<Integer> daysInt = new ArrayList<>();

        if(daysIn.matches("\\d+(\\s*,\\s*\\d+)+")){
            String daysStr[] = daysIn.split("\\s*,\\s*");

            for(int i=0; i<daysStr.length; i++){
                daysInt.add(Integer.parseInt(daysStr[i]));
            }

        }else if(daysIn.matches("\\d+")){
            daysInt.add(Integer.parseInt(daysIn));
        }

        return daysInt;
    }
}
