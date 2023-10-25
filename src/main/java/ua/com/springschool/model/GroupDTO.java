package ua.com.springschool.model;


import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class GroupDTO {

    private Long id;
    private String name;
    private Set<Long> studentIds;
}
