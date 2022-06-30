package com.ftn.redditClone.service;

import com.ftn.redditClone.model.entity.Report;

import java.util.List;

public interface ReportService {

    Report saveReport(Report report);

    List<Report> findAll();
}
