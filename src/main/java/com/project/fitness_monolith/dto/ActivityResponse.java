package com.project.fitness_monolith.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.fitness_monolith.model.ActivityType;
import com.project.fitness_monolith.model.Recommendations;
import com.project.fitness_monolith.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {


    private String id;
    private String userId;
    private ActivityType type;
    private Map<String,Object> additionalMetrics;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
