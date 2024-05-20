package com.todo.app.model.repository;

import java.util.List;

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

    @Query(value = """
            SELECT * FROM tasks
            WHERE is_completed = true
            ORDER BY tasks.id DESC
            """, nativeQuery = true)
    List<TaskEntity> completeTasks();

    @Query(value = """
            SELECT * FROM tasks
            WHERE is_completed = false
            ORDER BY tasks.id DESC
            """, nativeQuery = true)
    List<TaskEntity> uncompleteTasks();
}
