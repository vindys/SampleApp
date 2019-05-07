package com.vindys.sampleapp.data.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ActivityLog {
    public String id;
    public String actionTypeLabel;
    public String description;
    public long createdOn;

    public String getCreatedOn() {
        DateFormat dateTimeFormatter = new SimpleDateFormat("hh:mm a");
        Date date = new Date(createdOn);
        return dateTimeFormatter.format(date);
        //return createdOn;
    }
}
