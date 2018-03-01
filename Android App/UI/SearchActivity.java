package com.example.grantrowberry.fmclient.UI;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grantrowberry.fmclient.Models.Model.Event;
import com.example.grantrowberry.fmclient.Models.Model.PersonEvents;
import com.example.grantrowberry.fmclient.Models.Model.Search;
import com.example.grantrowberry.fmclient.Models.Model.SearchItem;
import com.example.grantrowberry.fmclient.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {
    RecyclerView mSearchRecyclerView;
    SearchAdapter mSearchAdapter;
    EditText mSearchBar;
    Search search = new Search();
    Button mSearchButton;
    Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchRecyclerView = (RecyclerView) findViewById(R.id.search_recycle_view);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                updateUI();
            }
        });
        mSearchBar = (EditText) findViewById(R.id.search_edit_text);
        mBackButton = (Button) findViewById(R.id.back_button_search);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });



    }

    private void updateUI(){
        List<SearchItem> searchItems = search.search(mSearchBar.getText().toString());

        mSearchAdapter = new SearchActivity.SearchAdapter(searchItems);
        mSearchRecyclerView.setAdapter(mSearchAdapter);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mPrimaryTextView;
        private TextView mSecondaryTextView;
        private ImageView mSearchImageView;
        private SearchItem mSearchItem;

        public SearchHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mPrimaryTextView = (TextView) itemView.findViewById(R.id.search_primary_text);
            mSecondaryTextView = (TextView) itemView.findViewById(R.id.search_secondary_text);
            mSearchImageView = (ImageView) itemView.findViewById(R.id.search_image);

        }

        public void bindEvent(SearchItem searchItem) {
            mSearchItem = searchItem;
            mPrimaryTextView.setText(mSearchItem.getPrimaryText());
            mSecondaryTextView.setText(mSearchItem.getSecondaryText());

            if(searchItem.getItemType().equals("person")) {
                if (mSearchItem.getPersonEvents().getPerson().getGender().equals("m")) {
                    mSearchImageView.setBackgroundResource(R.drawable.maleicon);
                } else {
                    mSearchImageView.setBackgroundResource(R.drawable.femaleicon);
                }
            } else if(searchItem.getItemType().equals("event")) {
                mSearchImageView.setBackgroundResource(R.mipmap.locationpingrey);
            }
        }

        @Override
        public void onClick(View v) {
            if(mSearchItem.getItemType().equals("event")) {
                Intent intent = new Intent(SearchActivity.this, MapActivity.class);
                Event event = mSearchItem.getEvent();
                intent.putExtra("event", event);
                startActivity(intent);

            } else if(mSearchItem.getItemType().equals("person")) {
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                PersonEvents personEvents = mSearchItem.getPersonEvents();
                intent.putExtra("personEvent", personEvents);
                startActivity(intent);
            }
        }
    }
    private class SearchAdapter extends RecyclerView.Adapter<SearchActivity.SearchHolder> {
        private List<SearchItem> mSearchItems;

        public SearchAdapter(List<SearchItem> searchItems){
            mSearchItems = searchItems;
        }

        @Override
        public SearchActivity.SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.search_recycler, parent, false);
            return new SearchActivity.SearchHolder(view);
        }

        @Override
        public void onBindViewHolder(SearchActivity.SearchHolder holder, int position) {
            SearchItem searchItem = mSearchItems.get(position);
            holder.bindEvent(searchItem);
        }

        @Override
        public int getItemCount() {
            return mSearchItems.size();
        }

        public void clear() {
            int size = this.mSearchItems.size();
            this.mSearchItems.clear();
            notifyItemRangeRemoved(0,size);
        }
    }

}
