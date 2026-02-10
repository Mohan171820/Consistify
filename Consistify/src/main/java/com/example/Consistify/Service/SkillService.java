package com.example.Consistify.Service;

import com.example.Consistify.DTO.SkillCreateRequest;
import com.example.Consistify.DTO.SkillResponseDTO;
import com.example.Consistify.Entity.Skill;
import com.example.Consistify.Entity.User;
import com.example.Consistify.Repo.SkillRepository;
import com.example.Consistify.Repo.UserRepository;
import com.example.Consistify.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

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

    public List<SkillResponseDTO> getSkillsForCurrentUser() {

        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return skillRepository.findByUser(user)
                .stream()
                .map(skill -> new SkillResponseDTO(
                        skill.getId(),
                        skill.getName()
                ))
                .toList();
    }
}
