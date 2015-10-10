package com.krrr.hub;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SensorsAdapter extends ArrayAdapter<Pair<String, String>> {

    Context context;
    int layoutResourceId;
    List<Pair<String,String>> data = null;

    public SensorsAdapter(Context context, int layoutResourceId, List<Pair<String, String>> data) {
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
            holder.sensorName = (TextView) row.findViewById(R.id.txtSensor);
            holder.status = (TextView) row.findViewById(R.id.txtOn);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Pair event = data.get(position);

        holder.sensorName.setText((String) event.first);
        holder.status.setText((String) event.second);

        return row;
    }

    static class ViewHolder
    {
        TextView sensorName;
        TextView status;
    }
}