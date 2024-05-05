package com.dsinnovators.todo.controllers;

import com.dsinnovators.todo.services.TaskService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReportController {

    private final TaskService taskService;

    public ReportController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> report() throws FileNotFoundException, JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(taskService.getTasks());
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reports.jrxml"));
        Map<String, Object> map = new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
//        JasperExportManager.exportReportToPdfFile(report, "report.pdf");

        byte[] reportData = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();

        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(reportData);
    }

}
