package com.ftn.swtreddit.model;

import java.time.LocalDate;

public class Report {

    private ReportReason reportReason;
    private LocalDate timestamp;
    private User byUser;
    private boolean accepted;

    public Report(ReportReason reportReason, LocalDate timestamp, User byUser, boolean accepted) {
        this.reportReason = reportReason;
        this.timestamp = timestamp;
        this.byUser = byUser;
        this.accepted = accepted;
    }

    public Report(){}

    public ReportReason getReportReason() {
        return reportReason;
    }

    public void setReportReason(ReportReason reportReason) {
        this.reportReason = reportReason;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public User getByUser() {
        return byUser;
    }

    public void setByUser(User byUser) {
        this.byUser = byUser;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
