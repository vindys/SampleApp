package com.vindys.sampleapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.vindys.sampleapp.data.model.ActivityMap;
import com.vindys.sampleapp.data.repository.SampleAppRepository;

import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;

public class SampleAppViewModel extends AndroidViewModel {
    SampleAppRepository sampleAppRepository;
    private String yearSelected;
    private String monthSelected;
    private final LiveData<ActivityMap> activityLogObservable;
    private MutableLiveData<YearMonthFilter> yearmonthValue=new MutableLiveData<>();

    private MutableLiveData<DataState> dataStateMutableLiveData=new MutableLiveData<>();

    @Inject
    public SampleAppViewModel(@NonNull SampleAppRepository sampleAppRepository, @NonNull Application application) {
        super(application);

        this.sampleAppRepository = sampleAppRepository;

        //we use switchMap so that whenever year or month is changed retrofit call would be initiated.
        activityLogObservable = Transformations.switchMap(yearmonthValue,value-> {
            if(value!=null&& value.month!=null && value.year!=null)
                return getActivityLogData(value.year,value.month);
            else return new MutableLiveData<>();
        });
    }

    private LiveData<ActivityMap> getActivityLogData(String year, String month) {
        // the view should show a loading indicator
        dataStateMutableLiveData.setValue(DataState.LOADING);
        // we don't actually map anything, we just use the map function to get
        // a callback of when the repository's LiveData has finished loading
        return Transformations.map(sampleAppRepository.getActivityLogs(year, month), activityMap -> {
            // the retrofit call has just finished fetching the data from the network
            // and after this method returns it will be available to the observer
            // in the activity.
            // we also need to dismiss the loading indicator
            if(activityMap == null){
                dataStateMutableLiveData.setValue(DataState.ERROR);
            }
            else if(activityMap.activityDocuments.isEmpty())
                dataStateMutableLiveData.setValue(DataState.EMPTY);
            else
                dataStateMutableLiveData.setValue(DataState.LIVE);
            return activityMap;
        });
    }

    public void onYearSelected(AdapterView<?> parent, View view, int pos, long id){
        if((TextView)view!=null) {
            yearSelected = ((TextView) view).getText().toString();
            setYearMonthFilter(yearSelected, monthSelected);
        }
    }

    public void onMonthSelected(AdapterView<?> parent, View view, int pos, long id){
        monthSelected = Integer.toString(pos + 1);
        setYearMonthFilter(yearSelected,monthSelected);
    }


    public void setYearMonthFilter(String year, String month) {
        if(year!=null && month !=null) {
            YearMonthFilter update = new YearMonthFilter(year, month);
            if (Objects.equals(yearmonthValue.getValue(), update)) {
                return;
            }
            yearmonthValue.setValue(update);
        }
    }

    public MutableLiveData<DataState> getDataStateMutableLiveData() {
        return dataStateMutableLiveData;
    }

    public LiveData<ActivityMap> getActivityLogObservable() {
        return activityLogObservable;
    }

    public int currentMonthIndex(){
        if( monthSelected == null) {
            Calendar calendar = Calendar.getInstance();
            return calendar.get(Calendar.MONTH);
        }else
            return Integer.valueOf(monthSelected) - 1;
    }


    static class YearMonthFilter {
        final String year;
        final String month;

        YearMonthFilter(String year, String month) {
            this.year = year == null ? null : year.trim();
            this.month = month == null ? null : month.trim();
        }
    }

    public enum DataState{
        LOADING,
        ERROR,
        EMPTY,
        LIVE
    }
}
