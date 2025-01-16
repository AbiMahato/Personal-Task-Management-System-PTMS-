package com.ptms.ptms.controller;

import com.ptms.ptms.dto.TaskDto;
import com.ptms.ptms.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    @GetMapping("/get-task/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    @PostMapping("/create-task")
    public String createTask(@Valid @RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }
    @PutMapping("/update-task/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto){
        return taskService.updateTask(id,taskDto);
    }

    @DeleteMapping("delete-task/{id}")
    public String deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id) ;
    }

    @GetMapping("/user-tasks/{id}")
    public List<TaskDto> getUserTasks(@PathVariable Long id) {
        return taskService.getUserTasks(id);
    }
    @GetMapping("/sorted-tasks/{field}")
    public List<TaskDto> getTasksWithSorting(@PathVariable String field) {
        return taskService.getTasksWithSorting(field);
    }
    @GetMapping("/tasks-pagination/{offset}/{pageSize}")
    public Page<TaskDto> getTasksWithPagination(@PathVariable int offset,@PathVariable int pageSize) {
        return taskService.getTasksWithPagination(offset,pageSize);
    }

    @GetMapping("/filter")
    public List<TaskDto> getFilteredTasks(
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String categoryName) {
        return taskService.getFilteredTasks(priority, status, userId, categoryName);
    }
}
