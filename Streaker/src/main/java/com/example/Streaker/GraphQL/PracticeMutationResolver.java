package com.example.Streaker.GraphQL;

import com.example.Streaker.DTO.PracticeLogRequest;
import com.example.Streaker.Service.PracticeLoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PracticeMutationResolver {

    private final PracticeLoggingService practiceLoggingService;

    @MutationMapping       // This mutation mapping to log and update details
    public String logPractice(@Argument PracticeLogRequest input) {
        practiceLoggingService.logPractice(input);
        return "Practice logged successfully";
    }
}