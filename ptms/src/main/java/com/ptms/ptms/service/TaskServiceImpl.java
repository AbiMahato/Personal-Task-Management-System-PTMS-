package com.ptms.ptms.service;

import com.ptms.ptms.dto.TaskDto;
import com.ptms.ptms.mapper.TaskMapper;
import com.ptms.ptms.model.Task;
import com.ptms.ptms.repository.TaskRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;

    public TaskServiceImpl(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public String createTask(Task task) {
        try {
            taskRepo.save(task);
            return "Task created";
        } catch (Exception e) {
            return "Task not created: " + e.getMessage();
        }
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepo.findAll()
                .stream()
                .map(TaskMapper.INSTANCE::convertTaskEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteTask(Long id) {
        if (taskRepo.findById(id).isEmpty())
            throw new RuntimeException("Task with id " + id + " does not exist");
//            throw new StudentNotFoundException("Student with id " + id + " not found");
        Task task = taskRepo.findById(id).get();
        taskRepo.delete(task);
        return "task deleted, id=" + id;
    }

}
