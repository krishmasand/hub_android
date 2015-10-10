package com.krrr.hub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.krrr.hub.adapters.HistoryAdapter;
import com.krrr.hub.R;

public class HistoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_fragment,container,false);
        getActivity().getWindow().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final ListView faqList = (ListView) v.findViewById(R.id.lstFAQ);
        ((TextView) v.findViewById(R.id.txtHeader)).setText("Alert History");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> events, ParseException e) {
                if (e == null) {
                    Collections.sort(events, new CustomComparator());
                    HistoryAdapter adapter = new HistoryAdapter(getActivity(), R.layout.history_item_list, events);
                    faqList.setAdapter(adapter);
                } else {
                    Log.e("Error", e.getMessage());
                }
            }
        });
        return v;
    }

    private class CustomComparator implements Comparator<ParseObject> {
        @Override
        public int compare(ParseObject o1, ParseObject o2) {
            try{
                return -(o1.getCreatedAt().compareTo(o2.getCreatedAt()));
            }
            catch(Exception e){
                return 0;
            }
        }
    }
}
