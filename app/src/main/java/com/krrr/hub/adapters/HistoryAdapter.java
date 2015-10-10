package com.krrr.hub.adapters;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

import com.krrr.hub.R;

public class HistoryAdapter extends ArrayAdapter<ParseObject> {

    Context context;
    int layoutResourceId;
    List<ParseObject> data = null;

    public HistoryAdapter(Context context, int layoutResourceId, List<ParseObject> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.dateTV = (TextView) row.findViewById(R.id.txtQuestion);
            holder.text = (TextView) row.findViewById(R.id.txtAnswer);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ParseObject event = data.get(position);
        if (event != null) {
            Date date = event.getCreatedAt();
            String AM_PM;

            int hours = date.getHours();
            if (hours == 0) {
                hours = 12;
                AM_PM = "am";
            } else if (hours < 12) {
                AM_PM = "am";
            } else if (hours == 12) {
                AM_PM = "pm";
            } else {
                hours = hours - 12;
                AM_PM = "pm";
            }

            String minutes = String.format("%02d", date.getMinutes());
            String seconds = String.format("%02d", date.getSeconds());
            String time = date.getMonth() + 1 + "/" + date.getDate() + "/" + (date.getYear() + 1900) + " " + hours + ":" + minutes + ":" + seconds + AM_PM;



            holder.dateTV.setText(time);
            holder.text.setText(event.getString("Text"));
        }

        return row;
    }

    static class ViewHolder
    {
        TextView dateTV;
        TextView text;
    }
}