package com.ftn.swtreddit.model;

import java.time.LocalDate;

public class Comment {

    private String text;
    private LocalDate timestamp;
    private boolean isDeleted;

    public Comment(String text, LocalDate timestamp, boolean isDeleted) {
        this.text = text;
        this.timestamp = timestamp;
        this.isDeleted = isDeleted;
    }

    public Comment(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
