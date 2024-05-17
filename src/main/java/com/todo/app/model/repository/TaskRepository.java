package com.todo.app.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.todo.app.model.entities.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long>{

    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM tasks
            WHERE user_id = ?1
            """, nativeQuery = true)
    void customDeleteTask(Long id);
}
