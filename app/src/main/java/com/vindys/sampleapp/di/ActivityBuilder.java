package com.vindys.sampleapp.di;

import com.vindys.sampleapp.view.activity.sampleActivity.SampleActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SampleActivityModule.class)
    abstract SampleActivity bindSampleActivity();

    /*@ContributesAndroidInjector(modules = ResultActivityModule.class)
    abstract ResultActivity bindResultActivity();*/

    /*@ContributesAndroidInjector(modules = {DetailActivityModule.class, DetailFragmentProvider.class})
    abstract DetailActivity bindDetailActivity();*/

}