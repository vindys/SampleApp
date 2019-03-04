package com.vindys.sampleapp.di;

import android.arch.lifecycle.ViewModelProvider;


import com.vindys.sampleapp.data.repository.SampleAppService;
import com.vindys.sampleapp.viewmodel.SampleAppViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(subcomponents = ViewModelSubComponent.class)
class AppModule {
    @Singleton @Provides
    SampleAppService provideFEProblemService() {
        return new Retrofit.Builder()
                .baseUrl(SampleAppService.HTTPS_API_FALCONE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SampleAppService.class);
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            ViewModelSubComponent.Builder viewModelSubComponent) {

        return new SampleAppViewModelFactory(viewModelSubComponent.build());
    }


}
