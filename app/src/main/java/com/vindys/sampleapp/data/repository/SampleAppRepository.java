package com.vindys.sampleapp.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.vindys.sampleapp.data.model.ActivityMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class SampleAppRepository {
    private SampleAppService sampleAppService;

    @Inject
    public SampleAppRepository(SampleAppService sampleAppService) {
        this.sampleAppService = sampleAppService;
    }

    public LiveData<ActivityMap> getActivityLogs(String yearSelected, String monthSelected) {
        final MutableLiveData<ActivityMap> data = new MutableLiveData<>();

        String url = "2180746/" + yearSelected + "/" + monthSelected + "/" + "0.json";
        sampleAppService.getActivityLogs(url).enqueue(new Callback<ActivityMap>() {
            @Override
            public void onResponse(Call<ActivityMap> call, Response<ActivityMap> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ActivityMap> call, Throwable t) {
                // TODO better error handling in part #2 ...
                data.setValue(null);
            }
        });

        return data;
    }
}
