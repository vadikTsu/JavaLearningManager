package ua.com.springschool.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.springschool.entity.Group;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

class GroupRepositoryTest extends AbstractTestContainer {

    GroupRepository groupRepository;

    @Autowired
    public GroupRepositoryTest(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    @Test
    void findAllGroupsWithLessStudents_shouldFetchStudentsWithLessThanStudents_whenMaximumInput() {
        Collection<Group> allGroups = groupRepository.findAll();
        Collection<Group> groupsWithLessThanStudents = groupRepository.findAllGroupsWithLessStudents(1000);

        Set<Long> allGroupIds = allGroups.stream().map(Group::getId).collect(Collectors.toSet());
        Set<Long> groupsWithLessIds = groupsWithLessThanStudents.stream().map(Group::getId).collect(Collectors.toSet());

        Assertions.assertEquals(allGroupIds, groupsWithLessIds);
    }

    @Test
    void findAllGroupsWithLessStudents_shouldFetchEmptyCollection_whenMinimumInput() {
        Collection<Group> groupsWithLessThanStudents = groupRepository.findAllGroupsWithLessStudents(1);

        Assertions.assertTrue(groupsWithLessThanStudents.isEmpty());
    }
}
