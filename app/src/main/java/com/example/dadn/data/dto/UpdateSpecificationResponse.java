package com.example.dadn.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateSpecificationResponse {
    @Expose
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
