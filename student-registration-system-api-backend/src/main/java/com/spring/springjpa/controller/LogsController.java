package com.spring.springjpa.controller;
import com.spring.springjpa.Model.Logs;
import com.spring.springjpa.repository.LogsRepository;
import com.spring.springjpa.utilities.ApiResponseLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private LogsRepository logsRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllLogs() {
        List<Logs> logsList = logsRepository.findAll();
        return ResponseEntity.ok().body(new ApiResponseLogs(true, "Logs found", logsList));
    }
}
