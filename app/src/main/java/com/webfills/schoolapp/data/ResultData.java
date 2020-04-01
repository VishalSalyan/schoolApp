package com.webfills.schoolapp.data;

/**
 * Created by PA01 on 10/29/2017.
 */

public class ResultData {

    private String title;
    private String updatedDate;
    private String description;
    private String fileUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
