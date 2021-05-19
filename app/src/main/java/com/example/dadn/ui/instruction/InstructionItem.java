package com.example.dadn.ui.instruction;

public class InstructionItem {
    private int mImgResource;
    private String title;
    private String details;

    public InstructionItem(int mImgResource, String tile, String details) {
        this.mImgResource = mImgResource;
        this.title = tile;
        this.details = details;
    }

    public int getmImgResource() {
        return mImgResource;
    }

    public void setmImgResource(int mImgResource) {
        this.mImgResource = mImgResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
