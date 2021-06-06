package com.example.dadn.ui.statistic;

import com.google.gson.annotations.SerializedName;


public class ResultFeedChart {
    private String value;
    @SerializedName("created_at")
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
