package com.ptms.ptms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TaskDto {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotNull
    private LocalDateTime dueDate;
    private String createdAt;
    @NotEmpty
    private String priority;
    @NotEmpty
    private String status;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String categoryName;
}
