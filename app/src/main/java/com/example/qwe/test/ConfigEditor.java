package com.example.qwe.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ConfigEditor extends AppCompatActivity {

    Loader loader = new Loader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_editor);

        EditText configText = (EditText) findViewById(R.id.configText);

        try{
            configText.setText(JsonLoader.loadJSON(this, loader.getConfigFileName()).toString(5));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
