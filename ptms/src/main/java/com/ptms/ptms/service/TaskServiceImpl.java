package com.ptms.ptms.service;

import com.ptms.ptms.dto.TaskDto;
import com.ptms.ptms.enums.Priority;
import com.ptms.ptms.enums.TaskStatus;
import com.ptms.ptms.exception.TaskNotFoundException;
import com.ptms.ptms.mapper.TaskMapper;
import com.ptms.ptms.model.Category;
import com.ptms.ptms.model.Task;
import com.ptms.ptms.model.User;
import com.ptms.ptms.repository.CategoryRepo;
import com.ptms.ptms.repository.TaskRepo;
import com.ptms.ptms.repository.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    public TaskServiceImpl(TaskRepo taskRepo, UserRepo userRepo, CategoryRepo categoryRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public String createTask(TaskDto taskDto) {
        try {
            Task task = TaskMapper.INSTANCE.convertDtoToTaskEntity(taskDto);

            // Fetch User by userName
            User user = userRepo.findByUserName(taskDto.getUserName());
            if (user == null) {
                throw new RuntimeException("User not found with username: " + taskDto.getUserName());
            }
            task.setUser(user);

            // Fetch Category by categoryName
            Category category = categoryRepo.findByName(taskDto.getCategoryName());
            if (category == null) {
                throw new RuntimeException("Category not found with name: " + taskDto.getCategoryName());
            }
            task.setCategory(category);

            taskRepo.save(task);
            return "Task created with id: " + task.getId();
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
        Task task = taskRepo.findById(id).get();
        taskRepo.delete(task);
        return "task deleted, id=" + id;
    }

    @Override
    public TaskDto updateTask(Long id,TaskDto taskDto) {
        if(taskRepo.findById(id).isEmpty())
            throw new TaskNotFoundException("Task with id " + id + " not found");
        Task task = taskRepo.findById(id).get();

//        TaskMapper.INSTANCE.updateTaskFromDto(taskDto, task);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setPriority(Priority.valueOf(taskDto.getPriority()));
        task.setStatus(TaskStatus.valueOf(taskDto.getStatus()));

        if (taskDto.getUserName() != null) {
            User user = userRepo.findByUserName(taskDto.getUserName());
            if (user == null) {
                throw new RuntimeException("User not found with username: " + taskDto.getUserName());
            }
            task.setUser(user);
        }

        if (taskDto.getCategoryName() != null) {
            Category category = categoryRepo.findByName(taskDto.getCategoryName());
            if (category == null) {
                throw new RuntimeException("Category not found with name: " + taskDto.getCategoryName());
            }
            task.setCategory(category);
        }

        taskRepo.save(task);
        return TaskMapper.INSTANCE.convertTaskEntityToDto(task);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        if(taskRepo.findById(id).isEmpty())
            throw new TaskNotFoundException("Task with id " + id + " not found");
        Task task = taskRepo.findById(id).get();
        return TaskMapper.INSTANCE.convertTaskEntityToDto(task);
    }

    @Override
    public List<TaskDto> getTasksWithSorting(String field) {
        List<Task> tasks = taskRepo.findAll(Sort.by(Sort.Direction.ASC,field));
        return tasks.stream()
                .map(TaskMapper.INSTANCE::convertTaskEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TaskDto> getTasksWithPagination(int offset, int pageSize) {
        Page<Task> tasks = taskRepo.findAll(PageRequest.of(offset, pageSize));
        return tasks.map(TaskMapper.INSTANCE::convertTaskEntityToDto);
    }

    @Override
    public List<TaskDto> getUserTasks(Long id) {
        List<Task> tasks = taskRepo.findByUserId(id);
        return tasks.stream()
                .map(TaskMapper.INSTANCE::convertTaskEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getFilteredTasks(String priority, String status, Long userId, String categoryName) {
        Specification<Task> specification = Specification.where(null);

        if (priority != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("priority"), Priority.valueOf(priority)));
        }

        if (status != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), TaskStatus.valueOf(status)));
        }

        if (userId != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("user").get("id"), userId));
        }

        if (categoryName != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category").get("name"), categoryName));
        }

        List<Task> tasks = taskRepo.findAll(specification);
        return tasks.stream()
                .map(TaskMapper.INSTANCE::convertTaskEntityToDto)
                .collect(Collectors.toList());
    }


}
