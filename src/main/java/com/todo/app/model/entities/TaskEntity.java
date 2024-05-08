package com.todo.app.model.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
@Data
@Builder
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "created_at", columnDefinition = "DATE")
    private LocalDate createdAt;

    @JsonIgnoreProperties({"taskList"})
    @ManyToOne(targetEntity = UserEntity.class, cascade = CascadeType.MERGE)
    private UserEntity userTask;

}
