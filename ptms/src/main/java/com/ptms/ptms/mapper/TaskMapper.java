package com.ptms.ptms.mapper;

import com.ptms.ptms.dto.TaskDto;
import com.ptms.ptms.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
    Task convertDtoToTaskEntity(TaskDto dto);

    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "category.name", target = "categoryName")
    TaskDto convertTaskEntityToDto(Task task);
}
