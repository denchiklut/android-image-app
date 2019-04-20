package com.example.andoid_image_app;

public class ImageData {
    private String id;
    private String src;
    private String full;
    private String desc;

    public ImageData(String id, String src, String full, String desc) {
        this.id = id;
        this.src = src;
        this.full = full;
        this.desc = desc;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
