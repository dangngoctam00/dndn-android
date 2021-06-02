package com.example.dadn.data.dto;

public class AlertRequest {
    private String id;
    private String accept;

    public AlertRequest(String id, String accept) {
        this.id = id;
        this.accept = accept;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }
}
