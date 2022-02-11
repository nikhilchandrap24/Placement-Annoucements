package com.example.placementannouncements;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminView implements Serializable {
    String title,eventDate,branch,description,link;
    @Exclude private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdminView(String title, String eventDate, String branch, String description, String link) {
        this.title = title;
        this.eventDate = eventDate;
        this.branch = branch;
        this.description = description;
        this.link=link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
