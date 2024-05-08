package com.todo.app.model.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Data
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_no_blocked")
    private boolean accountNoBlocked;

    @Column(name = "account_no_expired")
    private boolean accountNoExpired;

    @Column(name = "credentials_no_expired")
    private boolean credentialsNoExpired;

    @JsonIgnoreProperties({"userTask"})
    @OneToMany(targetEntity = TaskEntity.class, fetch = FetchType.LAZY, mappedBy = "userTask", cascade = CascadeType.ALL)
    List<TaskEntity> taskList;

}
