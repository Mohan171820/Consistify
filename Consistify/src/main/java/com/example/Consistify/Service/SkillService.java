package com.example.Consistify.Service;

import com.example.Consistify.DTO.SkillCreateRequest;
import com.example.Consistify.DTO.SkillResponseDTO;
import com.example.Consistify.Entity.Skill;
import com.example.Consistify.Entity.User;
import com.example.Consistify.Repo.SkillRepository;
import com.example.Consistify.Repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createSkill(SkillCreateRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean exists = skillRepository
                .existsByUserIdAndNameIgnoreCaseAndActiveTrue(
                        request.getUserId(),
                        request.getName()
                );

        if (exists) {
            throw new IllegalStateException("Skill already exists for this user");
        }


        if (exists) {
            throw new IllegalStateException("Skill already exists for this user");
        }

        Skill skill = new Skill();
        skill.setId(null); // force INSERT
        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        skill.setDecayDays(request.getDecayDays());
        skill.setActive(true);
        skill.setUser(user);

        skillRepository.save(skill);

    }
    public List<SkillResponseDTO> getSkillsForUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return skillRepository.findByUserAndActiveTrue(user)
                .stream()
                .map(skill -> new SkillResponseDTO(
                        skill.getId(),
                        skill.getName()
                ))
                .toList();
    }

}
