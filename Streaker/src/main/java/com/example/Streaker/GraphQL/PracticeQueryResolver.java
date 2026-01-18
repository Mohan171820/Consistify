package com.example.Streaker.GraphQL;

import com.example.Streaker.DTO.PracticeResponseDTO;
import com.example.Streaker.Service.PracticeLoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PracticeQueryResolver { // This Resolver is to just get data from database using repo

    private final PracticeLoggingService practiceLoggingService;

    @QueryMapping
    public List<PracticeResponseDTO> myPracticeSessions() {
        return practiceLoggingService.getAllSessions();
    }
}