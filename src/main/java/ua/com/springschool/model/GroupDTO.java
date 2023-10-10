package ua.com.springschool.model;

import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import ua.com.springschool.entity.Student;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class GroupDTO {

    private Long id;
    private String name;
    private Set<Long> studentIds;
}
