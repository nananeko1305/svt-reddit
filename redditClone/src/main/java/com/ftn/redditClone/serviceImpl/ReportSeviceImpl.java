package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.entity.Report;
import com.ftn.redditClone.repository.ReportRepository;
import com.ftn.redditClone.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportSeviceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }
}
