package com.example.dadn.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateSpecificationRequest {
    public UpdateSpecificationRequest() {}

    public UpdateSpecificationRequest(String type ,Integer lower_bound, Integer upper_bound) {
        this.type = type;
        this.lower_bound = lower_bound;
        this.upper_bound = upper_bound;
    }

    @Expose
    @SerializedName("type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Expose
    @SerializedName("lower_bound")
    private Integer lower_bound;

    @Expose
    @SerializedName("upper_bound")
    private Integer upper_bound;

    public Integer getLower_bound() {
        return lower_bound;
    }

    public void setLower_bound(Integer lower_bound) {
        this.lower_bound = lower_bound;
    }

    public Integer getUpper_bound() {
        return upper_bound;
    }

    public void setUpper_bound(Integer upper_bound) {
        this.upper_bound = upper_bound;
    }
}
