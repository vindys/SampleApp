package com.vindys.sampleapp.view.activity.sampleActivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

import com.vindys.sampleapp.R;
import com.vindys.sampleapp.data.model.ActivityMap;
import com.vindys.sampleapp.data.model.ActivityLog;
import com.vindys.sampleapp.databinding.ActivitySampleBinding;
import com.vindys.sampleapp.view.adapter.ActivityLogItem;
import com.vindys.sampleapp.view.adapter.ActivityRecyclerViewAdapter;
import com.vindys.sampleapp.view.adapter.DateItem;
import com.vindys.sampleapp.view.adapter.ListItem;
import com.vindys.sampleapp.view.util.SpacesItemDecoration;
import com.vindys.sampleapp.viewmodel.SampleAppViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

public class SampleActivity extends DaggerAppCompatActivity implements SampleActivityView {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ActivitySampleBinding sampleActivityBinding;
    private SampleAppViewModel viewModel;
    private ActivityRecyclerViewAdapter activityRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        initDataBinding();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(i -> executeDelayed());
    }

    private void initDataBinding() {
        sampleActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_sample);
        viewModel = ViewModelProviders.of(this,
                viewModelFactory).get(SampleAppViewModel.class);

        observeViewModel();
        sampleActivityBinding.setSampleActivityModel(viewModel);

        initializeRecyclerView(sampleActivityBinding);

    }

    private void observeViewModel(){
        LiveData<ActivityMap> activiLiveData = viewModel.getActivityLogObservable();
        activiLiveData.observe(this, new android.arch.lifecycle.Observer<ActivityMap>() {
            @Override
            public void onChanged(@Nullable ActivityMap activityLogs) {
                if (activityLogs != null && (!TextUtils.isEmpty(activityLogs.toString()))) {
                    activityRecyclerViewAdapter.setActivityList(getConsolidatedList(activityLogs));
                }
            }
        });

        viewModel.getDataStateMutableLiveData().observe(this, new Observer<SampleAppViewModel.DataState>() {
            @Override
            public void onChanged(@Nullable SampleAppViewModel.DataState dataState) {
                switch (dataState){
                    case LIVE:
                        sampleActivityBinding.activityLogRecyclerView.setVisibility(View.VISIBLE);
                        sampleActivityBinding.emptyView.setVisibility(View.GONE);
                        sampleActivityBinding.progressBar.setVisibility(View.GONE);
                        break;
                    case EMPTY:
                    case ERROR:
                        sampleActivityBinding.activityLogRecyclerView.setVisibility(View.GONE);
                        sampleActivityBinding.emptyView.setVisibility(View.VISIBLE);
                        sampleActivityBinding.progressBar.setVisibility(View.GONE);
                        break;
                    case LOADING:
                        sampleActivityBinding.activityLogRecyclerView.setVisibility(View.GONE);
                        sampleActivityBinding.emptyView.setVisibility(View.GONE);
                        sampleActivityBinding.progressBar.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private List<ListItem> getConsolidatedList(ActivityMap activityMap){
        // We linearly add every item into the consolidatedList.
        List<ListItem> consolidatedList = new ArrayList<>();

        DateFormat dateTimeFormatter = new SimpleDateFormat("MMMM d, yyyy");


        //Data seems to be coming sorted desc order. But just ensuring by sorting keys(dates) desc order
        SortedSet<Long> keys = new TreeSet<Long>(Collections.reverseOrder());
        keys.addAll(activityMap.activityDocuments.keySet());

        for (long date : keys) {
            DateItem dateItem = new DateItem();
            Date dateValue = new Date(date);
            dateItem.setDate(dateTimeFormatter.format(dateValue));
            consolidatedList.add(dateItem);

            for (ActivityLog activityLog : activityMap.activityDocuments.get(date)) {
                ActivityLogItem generalItem = new ActivityLogItem();
                generalItem.setActivityLog(activityLog);
                consolidatedList.add(generalItem);
            }
        }
        return consolidatedList;
    }

    private void initializeRecyclerView(ActivitySampleBinding sampleActivityBinding){
        activityRecyclerViewAdapter = new ActivityRecyclerViewAdapter(viewModel);

        sampleActivityBinding.activityLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                getResources().getDisplayMetrics()); // calculated
        sampleActivityBinding.activityLogRecyclerView.addItemDecoration(new SpacesItemDecoration(space));
        sampleActivityBinding.activityLogRecyclerView.setAdapter(activityRecyclerViewAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavBar();
        executeDelayed();
    }

    private void executeDelayed() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // execute after 500ms
                hideNavBar();
            }
        }, 5000);
    }

    private void hideNavBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

            );   //  To draw behind the action bar
        }else{
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION


            );//  To draw behind the action bar below 19
        }

    }
    @Override
    public void onSampleActivityLoaded() {

    }
}
