package com.example.qwe.test;

import org.json.JSONObject;

public interface Period {
    boolean checkIsToday();

    PeriodType getPeriodType();

    void toJson(JSONObject jsonIn);
    JSONObject toJson();

    void fromJson(JSONObject jsonIn);
}
