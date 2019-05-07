package com.vindys.sampleapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ActivityMap {
    public int msgtype;
    public String authentication;
    @SerializedName("activityDocuments")
    public Map<Long,List<ActivityLog>> activityDocuments;
}
