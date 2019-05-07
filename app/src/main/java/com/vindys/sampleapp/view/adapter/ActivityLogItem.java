package com.vindys.sampleapp.view.adapter;

import com.vindys.sampleapp.data.model.ActivityLog;

public class ActivityLogItem extends ListItem {
    private ActivityLog activityLog;

    public ActivityLog getActivityLog() {
        return activityLog;
    }

    public void setActivityLog(ActivityLog activityLog) {
        this.activityLog = activityLog;
    }

    @Override
    public int getType() {
        return TYPE_GENERAL;
    }
}
