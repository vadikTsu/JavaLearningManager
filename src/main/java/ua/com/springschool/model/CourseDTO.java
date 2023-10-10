package ua.com.springschool.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CourseDTO {

    private Long id;
    private String name;
    private Set<Long> studentIds;
}
