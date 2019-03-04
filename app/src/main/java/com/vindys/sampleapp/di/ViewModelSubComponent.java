package com.vindys.sampleapp.di;

import com.vindys.sampleapp.viewmodel.SampleAppViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link com.vindys.sampleapp.viewmodel.SampleAppViewModelFactory}.
 */
@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    SampleAppViewModel sampleAppViewModel();
    //FEResultViewModel fEResultViewModel();
}
