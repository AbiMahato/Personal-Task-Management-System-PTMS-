package com.ptms.ptms.repository;

import com.ptms.ptms.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
