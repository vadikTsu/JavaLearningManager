package ua.com.springschool.mapper;

import org.mapstruct.Mapper;
import ua.com.springschool.entity.Group;
import ua.com.springschool.model.GroupDTO;

@Mapper
public interface GroupMapper {

    Group groupDtoToGroup(GroupDTO groupDTO);
    GroupDTO groupToGroupDto(Group group);
}
