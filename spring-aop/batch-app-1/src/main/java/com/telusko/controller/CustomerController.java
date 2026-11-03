package com.telusko.controller;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    private Job job;

    @Autowired
    private JobOperator jobOperator;

    @GetMapping("/import")
    public  void loadData()
    {
        JobParameters jobParameter = new JobParametersBuilder()
                .addLong("Starts AT", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobOperator.start(job, jobParameter);
            System.out.println("Data Loaded");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
