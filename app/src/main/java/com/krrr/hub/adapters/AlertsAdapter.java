package com.krrr.hub.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

import com.krrr.hub.MainActivity;
import com.krrr.hub.R;

public class AlertsAdapter extends ArrayAdapter<ParseObject> {

    Context context;
    int layoutResourceId;
    List<ParseObject> data = null;

    public AlertsAdapter(Context context, int layoutResourceId, List<ParseObject> data) {
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



            holder.desc = (TextView) row.findViewById(R.id.txtRule);
            holder.enabled = (Switch) row.findViewById(R.id.switchOn);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final ParseObject event = data.get(position);
        holder.enabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(event!=null){
                    event.put("enabled", b);
                    event.saveInBackground();
                }
                Log.d("AlertsAdapter", "" + b);
            }
        });
        if (event != null) {
            try{
                holder.desc.setText(event.getString("Description"));
                holder.enabled.setChecked(event.getBoolean("enabled"));
            }
            catch(NullPointerException e){
            }
        }

        return row;
    }

    static class ViewHolder
    {
        TextView desc;
        Switch enabled;
    }
}