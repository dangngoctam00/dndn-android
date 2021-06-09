package com.example.dadn.data.dto;

public class AlertRequest {
    private String id;
    private String action;

    public AlertRequest(String id, String action) {
        this.id = id;
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
