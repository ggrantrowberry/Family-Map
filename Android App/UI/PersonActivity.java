package com.example.grantrowberry.fmclient.UI;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.grantrowberry.fmclient.Models.Model.Event;
import com.example.grantrowberry.fmclient.Models.Model.PersonEvents;
import com.example.grantrowberry.fmclient.Models.Model.PersonRelationship;
import com.example.grantrowberry.fmclient.Models.Model.Util;
import com.example.grantrowberry.fmclient.R;

import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends Activity {
    Button mBackButton;
    Button mUpButton;
    ImageView mImageView;
    ImageView mCollapseEventIcon;
    ImageView mCollapseFamilyIcon;
    RecyclerView mEventRecyclerView;
    RecyclerView mPersonRecyclerView;
    EventAdapter mEventAdapter;
    PersonAdapter mPersonAdapter;
    TextView mFirstNameTextView;
    TextView mLastNameTextView;
    PersonEvents mPersonEvents;
    RelativeLayout mEventCollapse;
    RelativeLayout mFamilyCollapse;
    Util util = new Util();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        mPersonEvents = (PersonEvents) getIntent().getSerializableExtra("personEvent");

        mEventRecyclerView = (RecyclerView) findViewById(R.id.event_recycle_view_person);

        mPersonRecyclerView = (RecyclerView) findViewById(R.id.family_recycle_view_person);
        mFirstNameTextView = (TextView) findViewById(R.id.first_name_person);
        mFirstNameTextView.setText(mPersonEvents.getPerson().getFirstName());
        mLastNameTextView = (TextView) findViewById(R.id.last_name_person);
        mLastNameTextView.setText(mPersonEvents.getPerson().getLastName());

        mBackButton = (Button) findViewById(R.id.back_button_person);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        mUpButton = (Button) findViewById(R.id.up_button_person);
        mUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(PersonActivity.this, MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });

        mImageView = (ImageView) findViewById(R.id.person_background_image_icon);
        if(mPersonEvents.getPerson().getGender().equals("m")){
            mImageView.setBackgroundResource(R.drawable.maleicontransparent);
        } else {
            mImageView.setBackgroundResource(R.drawable.femaleicontransparent);
        }

        mCollapseEventIcon = (ImageView) findViewById(R.id.collapsable_event_icon);
        mCollapseFamilyIcon = (ImageView) findViewById(R.id.collapsable_family_icon);

        mEventCollapse = (RelativeLayout) findViewById(R.id.collapsable_event);
        mEventCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(mEventRecyclerView.getVisibility() != View.GONE){
                    mEventRecyclerView.setVisibility(View.GONE);
                    mCollapseEventIcon.setBackgroundResource(R.mipmap.expandarrowup);
                } else {
                    mEventRecyclerView.setVisibility(View.VISIBLE);
                    mCollapseEventIcon.setBackgroundResource(R.mipmap.expandarrowdownblack);
                }
            }
        });

        mFamilyCollapse = (RelativeLayout) findViewById(R.id.collapsable_family);
        mFamilyCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(mPersonRecyclerView.getVisibility() != View.GONE){
                    mPersonRecyclerView.setVisibility(View.GONE);
                    mCollapseFamilyIcon.setBackgroundResource(R.mipmap.expandarrowup);
                } else {
                    mPersonRecyclerView.setVisibility(View.VISIBLE);
                    mCollapseFamilyIcon.setBackgroundResource(R.mipmap.expandarrowdownblack);
                }
            }
        });

        updateUI();

    }

    private void updateUI() {
        if(mPersonEvents.getEvents() != null) {
            List<Event> events = new ArrayList<>();
            for (Event event: mPersonEvents.getEvents()) {
                if(util.checkEvent(event)){
                    events.add(event);
                }
            }

                mEventAdapter = new PersonActivity.EventAdapter(events);
                mEventRecyclerView.setAdapter(mEventAdapter);
                mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
            mEventCollapse.setVisibility(View.GONE);
            mEventRecyclerView.setVisibility(View.GONE);
        }

        List<PersonRelationship> personRelationships = util.getRelationships(mPersonEvents.getPerson().getPersonID());
        mPersonAdapter = new PersonActivity.PersonAdapter(personRelationships);
        mPersonRecyclerView.setAdapter(mPersonAdapter);
        mPersonRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mEventTypeTextView;
        private TextView mEventPlaceTextView;
        private Event mEvent;

        public EventHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mEventTypeTextView = (TextView) itemView.findViewById(R.id.event_type_event_info_person);
            mEventPlaceTextView = (TextView) itemView.findViewById(R.id.event_place_event_info_person);

        }

        public void bindEvent(Event event) {
            mEvent = event;
            mEventTypeTextView.setText(mEvent.getEventType());
            mEventPlaceTextView.setText(mEvent.getCity() + ", " + mEvent.getCountry() + " " + Integer.toString(mEvent.getYear()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PersonActivity.this, MapActivity.class);
            Event event = mEvent;
            intent.putExtra("event", event);
            startActivity(intent);
        }
    }
    private class EventAdapter extends RecyclerView.Adapter<PersonActivity.EventHolder> {
        private List<Event> mEvents;

        public EventAdapter(List<Event> events){
            mEvents = events;
        }

        @Override
        public PersonActivity.EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.event_recycler, parent, false);
            return new PersonActivity.EventHolder(view);
        }

        @Override
        public void onBindViewHolder(PersonActivity.EventHolder holder, int position) {
            Event event = mEvents.get(position);
            holder.bindEvent(event);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }
    }

    private class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mRelationshipTextView;
        private TextView mNameTextView;
        private ImageView mImageView;
        private PersonRelationship mPersonRelationship;

        public PersonHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mRelationshipTextView = (TextView) itemView.findViewById(R.id.person_relationship_info_person);
            mNameTextView = (TextView) itemView.findViewById(R.id.person_name_info_person);
            mImageView = (ImageView) itemView.findViewById(R.id.person_image);

        }

        public void bindEvent(PersonRelationship personRelationship) {
            mPersonRelationship = personRelationship;
            mRelationshipTextView.setText(mPersonRelationship.getRelationship());
            mNameTextView.setText(mPersonRelationship.getPerson().getFirstName() + " " + mPersonRelationship.getPerson().getLastName());
            if(personRelationship.getPerson().getGender().equals("m")){
                mImageView.setBackgroundResource(R.drawable.maleicon);
            } else {
                mImageView.setBackgroundResource(R.drawable.femaleicon);
            }
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PersonActivity.this ,PersonActivity.class);
            PersonEvents personEvents = util.findPerson(mPersonRelationship.getPerson().getPersonID());
            intent.putExtra("personEvent", personEvents);
            startActivity(intent);
        }
    }
    private class PersonAdapter extends RecyclerView.Adapter<PersonActivity.PersonHolder> {
        private List<PersonRelationship> mPersonRelationships;

        public PersonAdapter(List<PersonRelationship> personRelationships){
            mPersonRelationships = personRelationships;
        }

        @Override
        public PersonActivity.PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.person_recycler, parent, false);
            return new PersonActivity.PersonHolder(view);
        }

        @Override
        public void onBindViewHolder(PersonActivity.PersonHolder holder, int position) {
            PersonRelationship personRelationship = mPersonRelationships.get(position);
            holder.bindEvent(personRelationship);
        }

        @Override
        public int getItemCount() {
            return mPersonRelationships.size();
        }
    }
}
