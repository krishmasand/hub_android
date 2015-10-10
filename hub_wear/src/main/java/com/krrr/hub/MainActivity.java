package com.krrr.hub;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.util.Pair;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements
        DataApi.DataListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private TextView mTextView;
    private GoogleApiClient mGoogleApiClient;
    private String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                Pair<String, String> testPair = new Pair<String, String>("N/A", "N/A");
                List<Pair<String, String>> events = new ArrayList<Pair<String, String>>();
                events.add(testPair);
                mTextView = (TextView) stub.findViewById(R.id.txtHeader);
                final ListView scheduleList = (ListView) stub.findViewById(R.id.lstSensors);
                SensorsAdapter adapter = new SensorsAdapter(getApplicationContext(), R.layout.sensors_item_list, events);
                scheduleList.setAdapter(adapter);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Connected to Google Api Service");
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    protected void onStop() {
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            Wearable.DataApi.removeListener(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.e(TAG, "data changed");
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                Log.e(TAG, "got changed data items");
                if (item.getUri().getPath().compareTo("/sensors") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    ArrayList<String> sensors = dataMap.getStringArrayList("sensors");
                    ArrayList<String> statuses = dataMap.getStringArrayList("statuses");
                    List<Pair<String, String>> events = new ArrayList<Pair<String, String>>();
                    for(int i = 0; i < sensors.size(); i++){
                        events.add(new Pair<String, String>(sensors.get(i), statuses.get(i)));
                    }
                    final ListView scheduleList = (ListView) findViewById(R.id.lstSensors);
                    SensorsAdapter adapter = new SensorsAdapter(getApplicationContext(), R.layout.sensors_item_list, events);
                    scheduleList.setAdapter(adapter);
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
