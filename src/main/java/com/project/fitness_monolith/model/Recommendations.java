package com.project.fitness_monolith.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Data
public class Recommendations {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,foreignKey = @ForeignKey(name = "fk_recommendation_user"))
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id",nullable = false,foreignKey = @ForeignKey(name = "fk_recommendation_activity"))
    @JsonIgnore
    private Activity activity;


    private String type;
    @Column(length = 2000)
    private String recommendation;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> improvements;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> suggestions;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> safety;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
