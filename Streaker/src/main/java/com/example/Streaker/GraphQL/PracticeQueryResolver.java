package com.example.Streaker.GraphQL;

import com.example.Streaker.DTO.PracticeResponseDTO;
import com.example.Streaker.Service.PracticeLoggingService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PracticeQueryResolver {

    private final PracticeLoggingService practiceLoggingService;

    public PracticeQueryResolver(PracticeLoggingService practiceLoggingService) {
        this.practiceLoggingService = practiceLoggingService;
    }

    @QueryMapping
    public List<PracticeResponseDTO> myPracticeSessions() {
        return practiceLoggingService.getAllSessions();
    }
}
