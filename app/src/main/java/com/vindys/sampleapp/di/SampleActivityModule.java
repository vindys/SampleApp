package com.vindys.sampleapp.di;

import com.vindys.sampleapp.data.repository.SampleAppRepository;
import com.vindys.sampleapp.view.activity.sampleActivity.SampleActivity;
import com.vindys.sampleapp.view.activity.sampleActivity.SampleActivityView;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class SampleActivityModule {

    /*@Binds
    abstract SampleActivityView provideSampleActivityView(SampleActivity sampleActivity);*/

    @Provides
    static SampleActivityPresenter providesSampleActivityPresenter(SampleActivityView sampleView, SampleAppRepository sampleAppRepository){
        return new SampleActivityPresenterImpl(sampleView,sampleAppRepository);
    }
}
