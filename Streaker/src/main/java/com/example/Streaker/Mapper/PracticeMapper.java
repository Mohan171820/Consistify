package com.example.Streaker.Mapper;

import com.example.Streaker.DTO.PracticeLogRequest;
import com.example.Streaker.DTO.PracticeResponseDTO;
import com.example.Streaker.Entity.PracticeSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PracticeMapper {
     // Entity -> DTO
    @Mapping(source = "skill.id", target = "skillId")
    @Mapping(source = "skill.name", target = "skillName")
    PracticeResponseDTO toResponseDTO(PracticeSession session);

    // DTO -> Entity
    @Mapping(target = "id", ignore = true) // Database generates the ID
    @Mapping(target = "skill", ignore = true) // We fetch and set this manually in Service
    PracticeSession toEntity(PracticeLogRequest request);
}