package com.example.qwe.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    RemindsContainer remindsContainer;

    Loader loader = new Loader();

    AlertDialog.Builder builder;

    int selectedPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView remindsList = (ListView) findViewById(R.id.remindsList);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button openConfigButton = (Button) findViewById(R.id.openConfigButton);

        remindsContainer = new RemindsContainer(this);
        remindsList.setAdapter(remindsContainer);

        loader.loadReminds(this, remindsContainer);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEditRemindActivity = new Intent(MainActivity.this, AddEditRemindActivity.class);
                addEditRemindActivity.putExtra("remindId", "0"); //0 - создаем новую напоминалку

                startActivityForResult(addEditRemindActivity, 0);
            }
        });

        openConfigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configEditor = new Intent(MainActivity.this, ConfigEditor.class);
                startActivity(configEditor);
            }
        });

        builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent addEditRemindActivity = new Intent(MainActivity.this, AddEditRemindActivity.class);
                addEditRemindActivity.putExtra("remindId", remindsContainer.getItem(selectedPos).getId()); //0 - значит создаем новую напоминалку

                startActivityForResult(addEditRemindActivity, 1);
            }
        });
        builder.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Remind remind = remindsContainer.getItem(selectedPos);
                loader.removeRemindById(MainActivity.this, remind.getId());
                remindsContainer.remove(remind);
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        remindsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;
                builder.create().show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            String id = data.getExtras().getString("remindId");

            if (requestCode == 0) {
                Remind remind = loader.loadRemindById(this, id);
                remindsContainer.add(remind);

            } else if (requestCode == 1) {
                loader.loadRemindById(this, id, remindsContainer.getRemindById(id));
            }
        }
    }
}
