package com.ptms.ptms.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String createdAt;
    private String priority;
    private String status;
    private String userName;
    private String categoryName;
}
