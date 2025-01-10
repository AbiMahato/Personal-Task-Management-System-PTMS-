package com.ptms.ptms.service;

import com.ptms.ptms.dto.TaskDto;
import com.ptms.ptms.model.Task;

import java.util.List;

public interface TaskService {
    String createTask(Task task);

    List<TaskDto> getAllTasks();

    String deleteTask(Long id);
}
