package com.vindys.sampleapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.vindys.sampleapp.data.repository.SampleAppRepository;

import javax.inject.Inject;

public class SampleAppViewModel extends AndroidViewModel {
    @Inject
    public SampleAppViewModel(@NonNull SampleAppRepository sampleAppRepository, @NonNull Application application) {
        super(application);
    }
}
