package com.krrr.hub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

import com.krrr.hub.R;

public class SensorsAdapter extends ArrayAdapter<ParseObject> {

    Context context;
    int layoutResourceId;
    List<ParseObject> data = null;

    public SensorsAdapter(Context context, int layoutResourceId, List<ParseObject> data) {
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
            holder.sensorImage = (ImageView) row.findViewById(R.id.sensor_image);
            holder.sensorName = (TextView) row.findViewById(R.id.txtSensor);
            holder.status = (TextView) row.findViewById(R.id.txtOn);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ParseObject event = data.get(position);
        if (event != null) {
            try{
                String name = event.getString("name");
                String natNam = event.getString("naturalLanguageName");
                boolean open = event.getBoolean("open");
                String openString;
                if (open) {
                    holder.sensorName.setTextColor(Color.parseColor("#00467f"));
                    holder.status.setTextColor(Color.parseColor("#00467f"));
                    if(name.equals("Window") || name.equals("Garage")) openString = "Open";
                    else openString = "On";
                }
                else{
                    holder.sensorName.setTextColor(Color.parseColor("#a5a5a5"));
                    holder.status.setTextColor(Color.parseColor("#a5a5a5"));
                    if(name.equals("Window") || name.equals("Garage")) openString = "Closed";
                    else openString = "Off";
                }
                if(name.equals("Window")){
                    holder.sensorImage.setImageResource(R.drawable.ic_stat_windowsensor);
                }
                if(name.equals("Garage")){
                    holder.sensorImage.setImageResource(R.drawable.ic_action_garagesensor);
                }
                if(name.equals("Smoke")){
                    holder.sensorImage.setImageResource(R.drawable.ic_action_smokesensor);
                }
                if(name.equals("Fire")){
                    holder.sensorImage.setImageResource(R.drawable.ic_action_firesensor);
                }
                if(name.equals("Water")){
                    holder.sensorImage.setImageResource(R.drawable.ic_action_watersensor);
                }
                holder.sensorName.setText(natNam);
                holder.status.setText(openString);
            }
            catch(NullPointerException e){
                Log.e("DeviceAdapter", e.toString());
            }



        }

        return row;
    }

    static class ViewHolder
    {
        ImageView sensorImage;
        TextView sensorName;
        TextView status;
    }
}