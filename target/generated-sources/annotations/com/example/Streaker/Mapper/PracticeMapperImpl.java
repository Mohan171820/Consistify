package com.example.Streaker.Mapper;

import com.example.Streaker.DTO.PracticeLogRequest;
import com.example.Streaker.DTO.PracticeResponseDTO;
import com.example.Streaker.Entity.PracticeSession;
import com.example.Streaker.Entity.Skill;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-07T21:08:46+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class PracticeMapperImpl implements PracticeMapper {

    @Override
    public PracticeResponseDTO toResponseDTO(PracticeSession session) {
        if ( session == null ) {
            return null;
        }

        PracticeResponseDTO.PracticeResponseDTOBuilder practiceResponseDTO = PracticeResponseDTO.builder();

        practiceResponseDTO.skillId( sessionSkillId( session ) );
        practiceResponseDTO.skillName( sessionSkillName( session ) );
        practiceResponseDTO.practiceDate( session.getPracticeDate() );
        practiceResponseDTO.durationMinutes( session.getDurationMinutes() );
        practiceResponseDTO.effortLevel( session.getEffortLevel() );

        return practiceResponseDTO.build();
    }

    @Override
    public PracticeSession toEntity(PracticeLogRequest request) {
        if ( request == null ) {
            return null;
        }

        PracticeSession practiceSession = new PracticeSession();

        practiceSession.setPracticeDate( request.getPracticeDate() );
        practiceSession.setDurationMinutes( request.getDurationMinutes() );
        practiceSession.setEffortLevel( request.getEffortLevel() );
        practiceSession.setNotes( request.getNotes() );

        return practiceSession;
    }

    private Long sessionSkillId(PracticeSession practiceSession) {
        if ( practiceSession == null ) {
            return null;
        }
        Skill skill = practiceSession.getSkill();
        if ( skill == null ) {
            return null;
        }
        Long id = skill.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String sessionSkillName(PracticeSession practiceSession) {
        if ( practiceSession == null ) {
            return null;
        }
        Skill skill = practiceSession.getSkill();
        if ( skill == null ) {
            return null;
        }
        String name = skill.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
