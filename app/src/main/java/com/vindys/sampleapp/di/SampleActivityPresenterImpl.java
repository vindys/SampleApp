package com.vindys.sampleapp.di;

import com.vindys.sampleapp.data.repository.SampleAppRepository;
import com.vindys.sampleapp.view.activity.sampleActivity.SampleActivityView;

import javax.inject.Inject;

public class SampleActivityPresenterImpl implements SampleActivityPresenter {
    SampleActivityView sampleActivityView;
    SampleAppRepository sampleAppRepository;

    @Inject
    public SampleActivityPresenterImpl(SampleActivityView sampleActivityView, SampleAppRepository sampleAppRepository) {
        this.sampleActivityView = sampleActivityView;
        this.sampleAppRepository = sampleAppRepository;
    }

    @Override
    public void loadActivity() {

    }
}
