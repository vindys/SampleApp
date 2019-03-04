package com.vindys.sampleapp.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SampleAppRepository {
    private SampleAppService sampleAppService;

    @Inject
    public SampleAppRepository(SampleAppService sampleAppService) {
        this.sampleAppService = sampleAppService;
    }
}
