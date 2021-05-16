package com.example.dadn.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificationDetailResponse {
    @Expose
    @SerializedName("spec")
    private String spec;

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("lower_bound")
    private Float lower_bound;

    @Expose
    @SerializedName("upper_bound")
    private Float upper_bound;

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLower_bound() {
        return lower_bound;
    }

    public void setLower_bound(Float lower_bound) {
        this.lower_bound = lower_bound;
    }

    public Float getUpper_bound() {
        return upper_bound;
    }

    public void setUpper_bound(Float upper_bound) {
        this.upper_bound = upper_bound;
    }
}
