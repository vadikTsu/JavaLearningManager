package ua.com.springschool.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;


@Data
@Builder
public class StudentDTO {

    private Long id;
    private String name;
    private Long groupId;
    private Set<Long> courseIds;
}
