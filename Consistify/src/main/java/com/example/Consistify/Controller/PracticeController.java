package com.example.Consistify.Controller;

import com.example.Consistify.DTO.PracticeLogRequest;
import com.example.Consistify.DTO.PracticeResponseDTO;
import com.example.Consistify.Service.PracticeLoggingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/practice")
@RequiredArgsConstructor
public class PracticeController {

    private final PracticeLoggingService practiceLoggingService;

     // This mapping to log the progress and endpoint should be "https://localhost:8080/api/v1/practice/log"
    @PostMapping("/log")
    public ResponseEntity<String> logPractice(@Valid @RequestBody PracticeLogRequest request) {
        practiceLoggingService.logPractice(request);
        return ResponseEntity.ok("Practice logged successfully");
    }
    // This mapping to get the logged practice sessions "https://localhost:8080/api/v1/practice/my"
    @GetMapping("/my")
    public List<PracticeResponseDTO> getMyPractices() {
        return practiceLoggingService.getAllSessions();
    }
}



