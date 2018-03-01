package com.example.grantrowberry.fmclient.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.Polyline;
import com.amazon.geo.mapsv2.model.PolylineOptions;
import com.example.grantrowberry.fmclient.Models.Model.Event;
import com.example.grantrowberry.fmclient.Models.Model.FTNode;
import com.example.grantrowberry.fmclient.Models.Model.FamilyTree;
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

/**
 * Created by GrantRowberry on 3/30/17.
 */

public class MapEventFragment extends android.support.v4.app.Fragment {
    Person person;
    Event event;
    TextView name;
    TextView eventInfo;
    ImageView imageView;
    Button exit;
    AmazonMap map;
    Marker marker;
    List<Polyline> polylines = new ArrayList<>();
    FTNode root;
    RelativeLayout layout;
    double rootGeneration;
    final double baseLineWidth = 10.0;
    Util util = new Util();
    Boolean fromMapActivity = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.map_event_fragment, container, false);
        name = (TextView) v.findViewById(R.id.person_name_map_event);
        name.setText(person.getFirstName() + " " + person.getLastName());
        eventInfo = (TextView) v.findViewById(R.id.event_info_map_event);
        eventInfo.setText(event.getEventType() + " " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
        imageView = (ImageView) v.findViewById(R.id.gender_icon_map_event);
        layout = (RelativeLayout) v. findViewById(R.id.map_event_info_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                PersonEvents personEvents = util.findPerson(person.getPersonID());
                intent.putExtra("personEvent", personEvents);
                startActivity(intent);
            }
        });

        if(person.getGender().equals("m")){
            imageView.setImageResource(R.drawable.maleicon);
        } else {
            imageView.setImageResource(R.drawable.femaleicon);
        }

        exit = (Button) v.findViewById(R.id.close_map_event_);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment f = fm.findFragmentById(R.id.event_info_fragment);
                if(f != null && f instanceof MapEventFragment) {

                    for (Polyline line: polylines) {
                        line.remove();
                    }

                    MapEventFragment mef = (MapEventFragment) f;
                    fm.beginTransaction().remove(mef).commit();
                }
            }
        });


        if(fromMapActivity) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),(float) 5.0));
        }
        if(ModelContainer.getInstance().getLifeStoryIsOn()) {
            setLifeStoryLines();
        }
        if(ModelContainer.getInstance().getSpouseIsOn()) {
            setSpouseLine();

        }
        if(ModelContainer.getInstance().getFamilyTreeIsOn()){
            setFamilyLines();
        }








        return v;
    }

    public void setLifeStoryLines(){
        PersonEvents personEvents = util.findPerson(person.getPersonID());
        List<LatLng> points = new ArrayList<>();

        for (Event event: personEvents.getEvents()) {
            if(util.checkEvent(event)) {
                LatLng ll = new LatLng(event.getLatitude(), event.getLongitude());
                points.add(ll);
            }
        }

        PolylineOptions opt = new PolylineOptions()
                .addAll(points)
                .color(ModelContainer.getInstance().getLifeStoryColor());

        Polyline polyline = map.addPolyline(opt);
        polylines.add(polyline);

    }

    public void setSpouseLine(){
        List<LatLng> points = new ArrayList<>();
        LatLng ll = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        points.add(ll);

        PersonEvents spousePersonEvents = util.findPerson(person.getSpouse());
        Event event = spousePersonEvents.getEvents().get(0);
        if (util.checkEvent(event)) {
            LatLng latlng = new LatLng(event.getLatitude(), event.getLongitude());
            points.add(latlng);
        }

        PolylineOptions opt = new PolylineOptions()
                .addAll(points)
                .color(ModelContainer.getInstance().getSpouseColor());


        Polyline polyline = map.addPolyline(opt);
        polylines.add(polyline);
    }

    public void setFamilyLines() {
        List<LatLng> points = new ArrayList<>();
        LatLng eventPoint = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        points.add(eventPoint);
        if(person.getFather() != null) {
            Event fatherEvent = util.findPerson(person.getFather()).getEvents().get(0);
            if(util.checkEvent(fatherEvent)) {
                LatLng fatherPoint = new LatLng(fatherEvent.getLatitude(), fatherEvent.getLongitude());
                points.add(fatherPoint);
                PolylineOptions optFather = new PolylineOptions()
                        .addAll(points)
                        .color(ModelContainer.getInstance().getFamilyTreeColor());
                Polyline fatherLine = map.addPolyline(optFather);
                polylines.add(fatherLine);
            }

        }
        points.clear();
        points.add(eventPoint);
        if(person.getMother() != null) {
            Event motherEvent = util.findPerson(person.getMother()).getEvents().get(0);
            if(util.checkEvent(motherEvent)) {
                LatLng motherPoint = new LatLng(motherEvent.getLatitude(), motherEvent.getLongitude());
                points.add(motherPoint);
                PolylineOptions optMother = new PolylineOptions()
                        .addAll(points)
                        .color(ModelContainer.getInstance().getFamilyTreeColor());
                Polyline motherLine = map.addPolyline(optMother);
                polylines.add(motherLine);
            }

        }

        root = ModelContainer.getInstance().getFamilyTree().getNode(person.getPersonID());
        rootGeneration = root.getGeneration();

        if(root.getFatherNode() != null){
            setFamilyLinesHelper(root.getFatherNode());
        }
        if(root.getMotherNode() != null){
            setFamilyLinesHelper(root.getMotherNode());
        }

    }

    public void setFamilyLinesHelper(FTNode node) {
        double totalGenerations = ModelContainer.getInstance().getFamilyTree().getTotalGenerations();
        float width = (float) (((totalGenerations-node.getGeneration())/(totalGenerations-rootGeneration))*baseLineWidth);

        Person nodePerson = node.getPersonEvents().getPerson();
        List<LatLng> points = new ArrayList<>();
        Event nodeEvent = node.getPersonEvents().getEvents().get(0);
        if(util.checkEvent(nodeEvent)) {
            LatLng nodePoint = new LatLng(nodeEvent.getLatitude(), nodeEvent.getLongitude());
            points.add(nodePoint);

            if (nodePerson.getFather() != null) {
                Event fatherEvent = util.findPerson(nodePerson.getFather()).getEvents().get(0);
                if (util.checkEvent(fatherEvent)) {
                    LatLng fatherPoint = new LatLng(fatherEvent.getLatitude(), fatherEvent.getLongitude());
                    points.add(fatherPoint);
                    PolylineOptions optFather = new PolylineOptions()
                            .addAll(points)
                            .color(ModelContainer.getInstance().getFamilyTreeColor())
                            .width((float) width);
                    Polyline fatherLine = map.addPolyline(optFather);
                    polylines.add(fatherLine);
                }

            }

            points.clear();
            points.add(nodePoint);

            if (nodePerson.getMother() != null) {
                Event motherEvent = util.findPerson(nodePerson.getMother()).getEvents().get(0);
                if (util.checkEvent(motherEvent)) {
                    LatLng motherPoint = new LatLng(motherEvent.getLatitude(), motherEvent.getLongitude());
                    points.add(motherPoint);
                    PolylineOptions optMother = new PolylineOptions()
                            .addAll(points)
                            .color(ModelContainer.getInstance().getFamilyTreeColor())
                            .width((float) width);
                    Polyline motherLine = map.addPolyline(optMother);
                    polylines.add(motherLine);
                }

            }
            if (node.getFatherNode() != null) {
                setFamilyLinesHelper(node.getFatherNode());
            }
            if (node.getMotherNode() != null) {
                setFamilyLinesHelper(node.getMotherNode());
            }
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    public AmazonMap getMap() {
        return map;
    }

    public void setMap(AmazonMap map) {
        this.map = map;
    }

    public List<Polyline> getPolylines() {
        return polylines;
    }

    public void setPolylines(List<Polyline> polylines) {
        this.polylines = polylines;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Boolean getFromMapActivity() {
        return fromMapActivity;
    }

    public void setFromMapActivity(Boolean fromMapActivity) {
        this.fromMapActivity = fromMapActivity;
    }
}
