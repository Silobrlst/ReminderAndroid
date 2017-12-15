package com.example.qwe.test;

import android.content.Context;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class JsonLoader {
    private static String loadJsonFromAsset(Context contextIn, String fileNameIn) {
        String json;
        try {

            FileInputStream fileInputStream = contextIn.openFileInput(fileNameIn);

            int size = fileInputStream.available();

            byte[] buffer = new byte[size];

            fileInputStream.read(buffer);

            fileInputStream.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }

        return json;
    }

    private static void saveDataToFile(Context contextIn, String fileNameIn, String dataIn) {
        FileOutputStream outputStream;

        try {
            outputStream = contextIn.openFileOutput(fileNameIn, Context.MODE_WORLD_READABLE);
            outputStream.write(dataIn.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject loadJSON(Context contextIn, String fileNameIn) {
        String data = loadJsonFromAsset(contextIn, fileNameIn);

        if (data.isEmpty()) {
            data = "{}";
        }

        JSONObject json = null;

        try{
            json = new JSONObject(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return json;
    }

    public static void saveJSON(Context contextIn, String fileNameIn, JSONObject jsonObjectIn) {
        try{
            saveDataToFile(contextIn, fileNameIn, jsonObjectIn.toString(5));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}