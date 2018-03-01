package com.example.grantrowberry.fmclient.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptor;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.GroundOverlay;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.amazon.geo.mapsv2.model.Polyline;
import com.example.grantrowberry.fmclient.Models.Model.Event;
import com.example.grantrowberry.fmclient.Models.Model.Person;
import com.example.grantrowberry.fmclient.Models.Model.PersonEvents;
import com.example.grantrowberry.fmclient.Models.Model.Util;
import com.example.grantrowberry.fmclient.Models.ModelContainer;
import com.example.grantrowberry.fmclient.Models.RequestResult.LoginRequest;
import com.example.grantrowberry.fmclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by GrantRowberry on 3/28/17.
 */

public class MapFragment extends Fragment {

    AmazonMap map;
    Map<Marker, Map<Event,Person>> markerEvents = new HashMap<>();
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<String> menuItems;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private Button mOpenDrawer;
    private TextView mFirstNameText;
    private TextView mLastNameText;
    private Util util = new Util();
    private Boolean fromMapActivity = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("myapp", Log.getStackTraceString(new Exception()));
        map.clear();
        map.setMapType(ModelContainer.getInstance().getCurrentMapType());
        createMarkers();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.event_info_fragment);

        if(f != null && f instanceof MapEventFragment) {
            MapEventFragment mef = (MapEventFragment) f;

            for (Polyline line: mef.getPolylines()) {
                line.remove();
            }

            fm.beginTransaction().remove(mef).commit();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.map_fragment, container, false);
        map = ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id. mapFragment)).getMap();
        map.setMapType(ModelContainer.getInstance().getCurrentMapType());


        createMarkers();


//        mFirstNameText = (TextView)v.findViewById(R.id.first_name_drawer);
//        mFirstNameText.setText(ModelContainer.getInstance().getUserPerson().getFirstName());
//        mLastNameText = (TextView)v.findViewById(R.id.last_name_drawer);
//        mLastNameText.setText(ModelContainer.getInstance().getUserPerson().getLastName());

        mOpenDrawer = (Button)v.findViewById(R.id.drawer_icon);
        mDrawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) v.findViewById(R.id.left_drawer);



        Bundle arguments = getArguments();
        if(arguments != null ){
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mOpenDrawer.setVisibility(View.GONE);
            simulateOnClick((Event) getArguments().getSerializable("event"));
            fromMapActivity = true;
        }
        // Set the adapter for the list view
        menuItems = new ArrayList<>();
        menuItems.add("Search");
        menuItems.add("Filter");
        menuItems.add("Settings");



        mDrawerList.setAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.drawer_list_item, menuItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        //mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        return v;
    }


    public void createMarkers(){
        List<PersonEvents> personEventsList = ModelContainer.getInstance().getPersonEvents();
        for (PersonEvents personEvents: personEventsList) {
            for (Event event: personEvents.getEvents()) {
                if(util.checkEvent(event)) {
                    LatLng latlng = new LatLng(event.getLatitude(), event.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(latlng)
                            .icon(BitmapDescriptorFactory.defaultMarker(util.getMarkerColor(event.getEventType())));
                    Marker m = map.addMarker(markerOptions);
                    Map<Event, Person> tempPersonEventMap = new TreeMap<>();
                    tempPersonEventMap.put(event, personEvents.getPerson());
                    markerEvents.put(m, tempPersonEventMap);
                }
            }

        }

        map.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                MapEventFragment mapEventFragment;

                Fragment f = fm.findFragmentById(R.id.event_info_fragment);

                if(f != null && f instanceof MapEventFragment) {
                    MapEventFragment mef = (MapEventFragment) f;

                    for (Polyline line: mef.getPolylines()) {
                        line.remove();
                    }

                    fm.beginTransaction().remove(mef).commit();
                }

                mapEventFragment = new MapEventFragment();
                TreeMap<Event, Person> personEvent = (TreeMap<Event, Person>) markerEvents.get(marker);
                mapEventFragment.setPerson(personEvent.firstEntry().getValue());
                mapEventFragment.setEvent(personEvent.firstEntry().getKey());
                mapEventFragment.setMap(map);
                mapEventFragment.setMarker(marker);
                fm.beginTransaction()
                        .add(R.id.event_info_fragment, mapEventFragment)
                        .commit();

                return false;
            }
        });
    }

    private void simulateOnClick(Event event){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        MapEventFragment mapEventFragment;

        Fragment f = fm.findFragmentById(R.id.event_info_fragment);

        if(f != null && f instanceof MapEventFragment) {
            MapEventFragment mef = (MapEventFragment) f;

            for (Polyline line: mef.getPolylines()) {
                line.remove();
            }

            fm.beginTransaction().remove(mef).commit();
        }

        Marker m = findMarker(event);
        mapEventFragment = new MapEventFragment();
        TreeMap<Event, Person> personEvent = (TreeMap<Event, Person>) markerEvents.get(m);
        mapEventFragment.setPerson(personEvent.firstEntry().getValue());
        mapEventFragment.setEvent(personEvent.firstEntry().getKey());
        mapEventFragment.setMap(map);
        mapEventFragment.setMarker(m);
        mapEventFragment.setFromMapActivity(true);
        fm.beginTransaction()
                .add(R.id.event_info_fragment, mapEventFragment)
                .commit();

    }

    private Marker  findMarker(Event event){
        for (Map.Entry<Marker, Map<Event,Person>> entry: markerEvents.entrySet()) {
            Marker m = entry.getKey();
            if(m.getPosition().latitude == event.getLatitude() && m.getPosition().longitude == event.getLongitude()){
                return m;
            }
        }
        return null;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);

        }
    }
    private void selectItem(int position) {
        if(position == 2) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            mDrawerList.setItemChecked(position,false);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        if(position == 1) {
            Intent intent = new Intent(getActivity(), FilterActivity.class);
            startActivity(intent);
            mDrawerList.setItemChecked(position,false);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        if(position == 0) {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
            mDrawerList.setItemChecked(position,false);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

}
