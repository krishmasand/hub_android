package com.krrr.hub;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.krrr.hub.fragments.HistoryFragment;
import com.krrr.hub.fragments.SensorsFragment;
import com.krrr.hub.fragments.AlertsFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public static double width;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            navigationView = (NavigationView) findViewById(R.id.navigation_view);
            if(!navigationView.getMenu().getItem(0).isChecked() && !drawerLayout.isDrawerOpen(Gravity.LEFT)){
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;

        SensorsFragment fragment = new SensorsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){
                    case R.id.sensors:
                        SensorsFragment sensorsFragment = new SensorsFragment();
                        FragmentTransaction scheduleFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        scheduleFragmentTransaction.replace(R.id.frame, sensorsFragment);
                        scheduleFragmentTransaction.commit();
                        return true;
                    case R.id.history:
                        HistoryFragment historyFragment = new HistoryFragment();
                        FragmentTransaction faqFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        faqFragmentTransaction.replace(R.id.frame, historyFragment);
                        faqFragmentTransaction.commit();
                        return true;
                    case R.id.alerts:
                        AlertsFragment alertsFragment = new AlertsFragment();
                        FragmentTransaction speakerFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        speakerFragmentTransaction.replace(R.id.frame, alertsFragment);
                        speakerFragmentTransaction.commit();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar.setLogo(R.drawable.ic_stat_icon_android_notification);
        toolbar.setTitle("Hub");
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.onResume();
    }
}
