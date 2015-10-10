package com.krrr.hub.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.krrr.hub.adapters.AlertsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import com.krrr.hub.R;

public class RulesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sensors_fragment, container, false);
        final ListView scheduleList = (ListView) v.findViewById(R.id.lstSchedule);
        ((TextView) v.findViewById(R.id.txtHeader)).setText("Rules");
        final Context context = getActivity();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Rules");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> events, ParseException e) {
                if (e == null) {
                    //Collections.sort(events, new CustomComparator());
                    AlertsAdapter adapter = new AlertsAdapter(context, R.layout.rules_item_list, events);
                    scheduleList.setAdapter(adapter);
                } else {
                    Log.e("Error", e.getMessage());
                }
            }
        });

        return v;
    }

//    private class CustomComparator implements Comparator<ParseObject> {
//        @Override
//        public int compare(ParseObject o1, ParseObject o2) {
//            try{
//                return o1.getDate("StartTime").compareTo(o2.getDate("StartTime"));
//            }
//            catch(Exception e){
//                return 0;
//            }
//        }
//    }
}
