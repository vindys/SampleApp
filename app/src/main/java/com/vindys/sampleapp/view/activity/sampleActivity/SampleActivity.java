package com.vindys.sampleapp.view.activity.sampleActivity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

import com.vindys.sampleapp.R;
import com.vindys.sampleapp.databinding.ActivitySampleBinding;
import com.vindys.sampleapp.di.SampleActivityPresenter;
import com.vindys.sampleapp.viewmodel.SampleAppViewModel;

import javax.inject.Inject;

public class SampleActivity extends DaggerAppCompatActivity implements SampleActivityView {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ActivitySampleBinding sampleActivityBinding;
    private SampleAppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        initDataBinding();
    }

    private void initDataBinding() {
        sampleActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_sample);
        /*fEPlanViewModel = new FEPlanViewModel(this);*/
        viewModel = ViewModelProviders.of(this,
                viewModelFactory).get(SampleAppViewModel.class);

        observeViewModel();
        sampleActivityBinding.setSampleActivityModel(viewModel);

        createClickHandler();

    }

    private void observeViewModel(){

    }

    private void createClickHandler(){

    }
    @Override
    public void onSampleActivityLoaded() {

    }
}
