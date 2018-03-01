package com.example.grantrowberry.fmclient.UI;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.grantrowberry.fmclient.Models.Model.FilterItem;
import com.example.grantrowberry.fmclient.Models.ModelContainer;
import com.example.grantrowberry.fmclient.R;

import java.util.List;

public class FilterActivity extends Activity {
    private RecyclerView mRecyclerView;
    private FilterAdapter mFilterAdapter;
    private Button backButton;
    private Button upButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mRecyclerView = (RecyclerView) findViewById(R.id.filter_recycler_view);
        backButton = (Button) findViewById(R.id.back_button_filter);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        upButton = (Button) findViewById(R.id.up_button_filter);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(FilterActivity.this, MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });

        updateUI();

    }

    private void updateUI() {
        List<FilterItem> filterItems = ModelContainer.getInstance().getFilterItemsList();
        mFilterAdapter = new FilterAdapter(filterItems);
        mRecyclerView.setAdapter(mFilterAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class FilterHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private CheckBox mOnCheckBox;
        private FilterItem mFilterItem;

        public FilterHolder(final View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.filter_title);
            mOnCheckBox = (CheckBox) itemView.findViewById(R.id.filter_switch);
            mOnCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    int itemPosition = mRecyclerView.getChildLayoutPosition(itemView);
                    if(itemPosition != -1) {
                        ModelContainer.getInstance().getFilterItemsList().get(itemPosition).setON(isChecked);
                    }
                }

            });

        }

        public void bindFilter(FilterItem filterItem) {
            mFilterItem = filterItem;
            mTitleTextView.setText(mFilterItem.getFilterTitle());
            mOnCheckBox.setChecked(mFilterItem.getON());


        }
    }
    private class FilterAdapter extends RecyclerView.Adapter<FilterHolder> {
        private List<FilterItem> mFilter;

        public FilterAdapter(List<FilterItem> filters){
            mFilter = filters;
        }

        @Override
        public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.filter_item, parent, false);
            return new FilterHolder(view);
        }

        @Override
        public void onBindViewHolder(FilterHolder holder, int position) {
            FilterItem filter = mFilter.get(position);
            holder.bindFilter(filter);
        }

        @Override
        public int getItemCount() {
            return mFilter.size();
        }
    }

}


