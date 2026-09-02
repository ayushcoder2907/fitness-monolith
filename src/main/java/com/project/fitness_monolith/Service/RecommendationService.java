package com.project.fitness_monolith.Service;

import com.project.fitness_monolith.dto.RecommendationRequest;
import com.project.fitness_monolith.model.Activity;
import com.project.fitness_monolith.model.Recommendations;
import com.project.fitness_monolith.model.User;
import com.project.fitness_monolith.repository.ActivityRepository;
import com.project.fitness_monolith.repository.RecommendationRepository;
import com.project.fitness_monolith.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class RecommendationService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final RecommendationRepository recommendationRepository;

    public Recommendations generateRecommendation(RecommendationRequest request) {

        User user=userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not found: "+request.getUserId()));

        Activity activity=activityRepository.findById(request.getActivityId())
                .orElseThrow(() -> new RuntimeException("Activity Not found: "+request.getActivityId()));

        Recommendations recommendation=Recommendations.builder()
                .user(user)
                .activity(activity)
                .improvements(request.getImprovements())
                .suggestions(request.getSuggestions())
                .safety(request.getSafety())
                .build();

        Recommendations savedRecommendation =recommendationRepository.save(recommendation);
        return savedRecommendation;
    }

    public List<Recommendations> getUserRecommnedation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public List<Recommendations> getActivityRecommnedation(String activityId) {
        return recommendationRepository.findByActivityId(activityId);

    }
}
