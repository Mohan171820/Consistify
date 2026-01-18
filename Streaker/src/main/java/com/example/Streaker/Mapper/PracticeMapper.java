package com.example.Streaker.Mapper;

import com.example.Streaker.DTO.PracticeLogRequest;
import com.example.Streaker.DTO.PracticeResponseDTO;
import com.example.Streaker.Entity.PracticeSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Registers this mapper as a Spring bean
public interface PracticeMapper {

    // Converts PracticeSession entity to PracticeResponseDTO
    @Mapping(source = "skill.id", target = "skillId")     // Maps skill ID from Skill entity
    @Mapping(source = "skill.name", target = "skillName") // Maps skill name from Skill entity
    PracticeResponseDTO toResponseDTO(PracticeSession session);

    // Converts PracticeLogRequest DTO to PracticeSession entity
    @Mapping(target = "id", ignore = true)    // ID is generated automatically by the database
    @Mapping(target = "skill", ignore = true) // Skill is fetched and set manually in the service layer
    PracticeSession toEntity(PracticeLogRequest request);
}
