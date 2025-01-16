package com.ptms.ptms.service;

import com.ptms.ptms.dto.TaskDto;
import com.ptms.ptms.model.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {
    String createTask(TaskDto taskDto);

    List<TaskDto> getAllTasks();

    String deleteTask(Long id);

    TaskDto updateTask(Long id,TaskDto taskDto);

    TaskDto getTaskById(Long id);

    List<TaskDto> getTasksWithSorting(String field);

    Page<TaskDto> getTasksWithPagination(int offset, int pageSize);

    List<TaskDto> getUserTasks(Long id);

    List<TaskDto> getFilteredTasks(String priority, String status, Long userId, String categoryName);
}
