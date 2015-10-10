package com.krrr.hub.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import com.krrr.hub.R;
import com.krrr.hub.adapters.SensorsAdapter;

public class SensorsFragment extends Fragment implements
        DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sensors_fragment, container, false);
        final ListView scheduleList = (ListView) v.findViewById(R.id.lstSchedule);
        final Context context = getActivity();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Sensors");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> events, ParseException e) {
                if (e == null) {
                    SensorsAdapter adapter = new SensorsAdapter(context, R.layout.sensors_item_list, events);
                    scheduleList.setAdapter(adapter);

                    ArrayList<String> sensors = new ArrayList<String>();
                    ArrayList<String> statuses = new ArrayList<String>();
                    for(ParseObject event : events){
                        if(event!=null) {
                            sensors.add(event.getString("naturalLanguageName"));
                            String name = event.getString("name");
                            boolean open = event.getBoolean("open");
                            String openString = "";

                            if (open) {
                                if (name.equals("Window") || name.equals("Garage"))
                                    openString = "Open";
                                else openString = "On";
                            } else {
                                if (name.equals("Window") || name.equals("Garage"))
                                    openString = "Closed";
                                else openString = "Off";
                            }
                            statuses.add(openString);
                        }
                    }
                    PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/sensors");
                    putDataMapReq.getDataMap().putStringArrayList("sensors", sensors);
                    putDataMapReq.getDataMap().putStringArrayList("statuses", statuses);
                    putDataMapReq.getDataMap().putDouble("rand", Math.random());
                    Log.e("SensorsFragment", "putting DataItems");
                    PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                    PendingResult<DataApi.DataItemResult> pendingResult =
                            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);

                } else {
                    Log.e("Error", e.getMessage());
                }
            }
        });

        return v;
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
