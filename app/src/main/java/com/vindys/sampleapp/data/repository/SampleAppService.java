package com.vindys.sampleapp.data.repository;

import com.vindys.sampleapp.data.model.ActivityMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface SampleAppService {
    String HTTPS_API_ABP_URL = "https://sof.abpweddings.com/mats/activity/read/";

    @GET
    Call<ActivityMap> getActivityLogs(@Url String url);
}
