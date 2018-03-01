package com.example.grantrowberry.fmclient.UI;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.grantrowberry.fmclient.Models.Model.Util;
import com.example.grantrowberry.fmclient.Models.ModelContainer;
import com.example.grantrowberry.fmclient.Net.Context;
import com.example.grantrowberry.fmclient.Net.SyncTask;
import com.example.grantrowberry.fmclient.R;

import java.util.List;

public class SettingsActivity extends Activity implements Context {
    Button backButton;
    Button upButton;
    Switch mLifeStorySwitch;
    Switch mFamilyTreeSwitch;
    Switch mSpouseSwitch;
    Spinner mLifeStorySpinner;
    Spinner mFamilyTreeSpinner;
    Spinner mSpouseSpinner;
    Spinner mMapTypeSpinner;
    RelativeLayout mReSyncLayout;
    RelativeLayout mLogoutLayout;
    Util util = new Util();

    public void onSuccess(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onError(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
    public void goToMap(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backButton = (Button) findViewById(R.id.back_button_settings);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        upButton = (Button) findViewById(R.id.up_button_settings);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(SettingsActivity.this, MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });

        mLifeStorySwitch = (Switch) findViewById(R.id.life_story_line_switch);
        mLifeStorySwitch.setChecked(ModelContainer.getInstance().getLifeStoryIsOn());
        mLifeStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    ModelContainer.getInstance().setLifeStoryIsOn(isChecked);
            }
        });

        mFamilyTreeSwitch = (Switch) findViewById(R.id.family_tree_line_switch);
        mFamilyTreeSwitch.setChecked(ModelContainer.getInstance().getFamilyTreeIsOn());
        mFamilyTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                ModelContainer.getInstance().setFamilyTreeIsOn(isChecked);
            }
        });

        mSpouseSwitch = (Switch) findViewById(R.id.spouse_line_switch);
        mSpouseSwitch.setChecked(ModelContainer.getInstance().getSpouseIsOn());
        mSpouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                ModelContainer.getInstance().setSpouseIsOn(isChecked);
            }
        });

        mLifeStorySpinner = (Spinner) findViewById(R.id.life_story_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.color_keys, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLifeStorySpinner.setAdapter(adapter);
        mLifeStorySpinner.setSelection(util.getColorIndex("lifeStory"));
        mLifeStorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.updateColor("lifeStory", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mFamilyTreeSpinner = (Spinner) findViewById(R.id.family_tree_spinner);
        mFamilyTreeSpinner.setAdapter(adapter);
        mFamilyTreeSpinner.setSelection(util.getColorIndex("familyTree"));
        mFamilyTreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.updateColor("familyTree", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mSpouseSpinner = (Spinner) findViewById(R.id.spouse_spinner);
        mSpouseSpinner.setAdapter(adapter);
        mSpouseSpinner.setSelection(util.getColorIndex("spouse"));

        mSpouseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.updateColor("spouse", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mMapTypeSpinner = (Spinner) findViewById(R.id.map_spinner);
        String[] mapTypes = util.getMapTypes();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypes);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMapTypeSpinner.setAdapter(adapter1);
        mMapTypeSpinner.setSelection(util.getMapTypeIndex());
        mMapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                util.setMapType((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mReSyncLayout = (RelativeLayout) findViewById(R.id.resynclayout);
        mReSyncLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ModelContainer.getInstance().clear();
                SyncTask s = new SyncTask();
                s.setC(SettingsActivity.this);
                s.execute();
            }
        });

        mLogoutLayout = (RelativeLayout) findViewById(R.id.logoutlayout);
        mLogoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ModelContainer.getInstance().clear();
                Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });



    }


}
