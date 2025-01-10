package com.ptms.ptms.controller;

import com.ptms.ptms.dto.TaskDto;
import com.ptms.ptms.model.Task;
import com.ptms.ptms.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TasksController {
    private final TaskService taskService;
    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping("/all-tasks")
    public List<TaskDto> getAllTasks(){
        return taskService.getAllTasks();
    }
    @PostMapping("/create-task")
    public String createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
    @DeleteMapping("delete-task/{id}")
    public String deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id) ;
    }

}
