package com.example.Streaker.Controller;

import com.example.Streaker.DTO.PracticeLogRequest;
import com.example.Streaker.DTO.PracticeResponseDTO;
import com.example.Streaker.Service.PracticeLoggingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/practice")
@RequiredArgsConstructor
public class PracticeController {

    private final PracticeLoggingService practiceLoggingService;

    @PostMapping("/log")
    public ResponseEntity<String> logPractice(@Valid @RequestBody PracticeLogRequest request) {
        practiceLoggingService.logPractice(request);
        return ResponseEntity.ok("Practice logged successfully");
    }

    @GetMapping("/my")
    public List<PracticeResponseDTO> getMyPractices() {
        return practiceLoggingService.getAllSessions();
    }
}



