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
@RequestMapping("/practice")
@RequiredArgsConstructor
public class PracticeController {

    private final PracticeLoggingService practiceLoggingService;
    /**
     * Receives practice data from the user, validates it, and saves it.
     */
    @PostMapping("/log")
    public ResponseEntity<String> logPractice(@Valid @RequestBody PracticeLogRequest request) {
        // Delegate the business logic to the service layer
        practiceLoggingService.logPractice(request);
        return ResponseEntity.ok("Practice logged successfully");
    }
    @GetMapping
    public List<PracticeResponseDTO> getAllPractices() {
        return practiceLoggingService.getAllSessions();
    }
}

