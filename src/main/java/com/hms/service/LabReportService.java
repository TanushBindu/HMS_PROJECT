package com.hms.service;

import com.hms.model.LabReport;
import com.hms.repository.LabReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabReportService {

    @Autowired
    private LabReportRepository labReportRepository;

    public List<LabReport> getAllReports() {
        return labReportRepository.findAll();
    }

    public LabReport getReportById(Long id) {
        return labReportRepository.findById(id).orElse(null);
    }

    public LabReport saveReport(LabReport report) {
        return labReportRepository.save(report);
    }

    public void deleteReport(Long id) {
        labReportRepository.deleteById(id);
    }
}
