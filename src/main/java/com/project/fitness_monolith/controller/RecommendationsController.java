package com.project.fitness_monolith.controller;

import com.project.fitness_monolith.Service.RecommendationService;
import com.project.fitness_monolith.dto.RecommendationRequest;
import com.project.fitness_monolith.model.Recommendations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationsController {

    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<Recommendations> generateRecommendation(@RequestBody RecommendationRequest request){
        Recommendations recommendation=recommendationService.generateRecommendation(request);
        return ResponseEntity.ok(recommendation);
    }

    @GetMapping("/user/{userId}")
    public  ResponseEntity<List<Recommendations>> getUserRecommendation(@PathVariable String userId){
        List<Recommendations> recommendationsList=recommendationService.getUserRecommnedation(userId);
        return ResponseEntity.ok(recommendationsList);
    }

    @GetMapping("/activity/{activtyId}")
    public  ResponseEntity<List<Recommendations>> getActivityRecommendation(@PathVariable String activityId){
        List<Recommendations> recommendationsList=recommendationService.getActivityRecommnedation(activityId);
        return ResponseEntity.ok(recommendationsList);
    }
}
