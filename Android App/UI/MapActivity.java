package com.example.grantrowberry.fmclient.UI;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.example.grantrowberry.fmclient.Models.Model.Event;
import com.example.grantrowberry.fmclient.Models.Model.PersonEvents;
import com.example.grantrowberry.fmclient.R;

public class MapActivity extends FragmentActivity {

    private MapFragment mapFragment;
    private Event mEvent;
    private Button mBackButton;
    private Button mUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mEvent = (Event) getIntent().getSerializableExtra("event");

        mBackButton = (Button) findViewById(R.id.back_button_map);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        mUpButton = (Button) findViewById(R.id.up_button_map);
        mUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(MapActivity.this, MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });


        FragmentManager fm = this.getSupportFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(R.id.map_activity_map_fragment);

        if(mapFragment == null) {
            mapFragment = new MapFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("fromMapActivity", "From Map Activity");
        bundle.putSerializable("event", mEvent);
        mapFragment.setArguments(bundle);

        fm.beginTransaction()
                .add(R.id.map_activity_map_fragment,mapFragment)
                .commit();

    }

}
