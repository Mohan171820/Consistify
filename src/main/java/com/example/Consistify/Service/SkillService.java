package com.example.Consistify.Service;

import com.example.Consistify.DTO.SkillCreateRequest;
import com.example.Consistify.DTO.SkillHealthDTO;
import com.example.Consistify.DTO.SkillResponseDTO;
import com.example.Consistify.Entity.Skill;
import com.example.Consistify.Entity.User;
import com.example.Consistify.Repo.SkillRepository;
import com.example.Consistify.Repo.UserRepository;
import com.example.Consistify.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final SkillHealthCalculator healthCalculator;

    @Transactional
    public void createSkill(
            SkillCreateRequest request
    ) {
        // Resolve logged-in user from security context
        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Prevent duplicate skill names per user
        boolean exists = skillRepository
                .existsByUserAndNameIgnoreCase(user, request.getName());

        if (exists) {
            throw new IllegalStateException("Skill already exists for this user");
        }

        Skill skill = new Skill();
        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        skill.setUser(user);

        skillRepository.save(skill);
    }


    public Page<SkillResponseDTO> getSkillsForCurrentUser(Pageable pageable) {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return skillRepository
                .findByUser(user, pageable)
                .map(skill -> new SkillResponseDTO(
                        skill.getId(),
                        skill.getName(),
                        skill.getCategory(),
                        skill.isActive()
                ));


        }
    public List<SkillHealthDTO> getSkillHealthForCurrentUser() {

        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return skillRepository.findByUser(user)
                .stream()
                .map(skill -> healthCalculator.calculate(skill))
                .toList();
    }

}
