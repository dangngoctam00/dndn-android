package com.example.dadn.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificationResponse {

    @Expose
    @SerializedName("spec")
    private String spec;

    @Expose
    @SerializedName("id")
    private Integer id;

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
}
