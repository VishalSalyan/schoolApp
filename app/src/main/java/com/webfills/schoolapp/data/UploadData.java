package com.webfills.schoolapp.data;

public class UploadData {
    private String description;
    private String name;
    private String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public UploadData() {
    }

    public UploadData(String name, String description, String url) {
        this.name = name;
        this.url = url;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
