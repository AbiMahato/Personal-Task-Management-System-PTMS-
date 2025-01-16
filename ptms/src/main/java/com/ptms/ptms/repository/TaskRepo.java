package com.ptms.ptms.repository;

import com.ptms.ptms.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findByUserId(Long userId);
}
