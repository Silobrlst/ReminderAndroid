package com.example.qwe.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public final class RemindsContainer extends ArrayAdapter<Remind> implements Serializable {
    private final Context context;

    public RemindsContainer(Context contextIn){
        super(contextIn, android.R.layout.simple_list_item_1);
        context = contextIn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.remind, parent, false);

        Remind remind = getItem(position);

        String remindStr = "";
        remindStr += Integer.toString(remind.getHour());
        remindStr += ":";
        remindStr += Integer.toString(remind.getMinute());
        remindStr += " " + getItem(position).getText();

        ((TextView) rowView.findViewById(R.id.textView)).setText(remindStr);

        return rowView;
    }

    public Remind getRemindById(String idIn){
        for(int i=0; i<getCount(); i++){
            if(getItem(i).getId().equals(idIn)){
                return getItem(i);
            }
        }

        return null;
    }

    void fromJson(JSONObject jsonIn){
        try{
            JSONArray ids = jsonIn.names();
            if(ids != null){
                for(int i=0; i<ids.length(); i++){
                    String id = ids.getString(i);

                    Remind remind = new Remind(id, jsonIn.getJSONObject(id));

                    add(remind);
                }
            }

            notifyDataSetChanged();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    JSONObject toJson(){
        JSONObject json = new JSONObject();

        try{
            for(int i=0; i<getCount(); i++){
                json.put(getItem(i).getId(), getItem(i).toJson());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return json;
    }
}
