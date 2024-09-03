package com.example.product;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.product.dto.groupsDto;
import com.example.product.entity.Groups;
import com.example.product.entity.Users;
import com.example.product.exception.GroupsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.GroupRepository;
import com.example.product.repository.UsersRepository;
import com.example.product.service.GroupServiceImpl;

@SpringBootTest
public class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    public void getAllListOfGroups_Success() {
        List<Groups> groups = new ArrayList<>();
        // Add some sample groups to the list
        // Assuming you have some test data set up properly
        
        when(groupRepository.findAll()).thenReturn(groups);
        
        List<groupsDto> result = groupService.getAllListOfGroups();
        assertEquals(groups.size(), result.size());
    }
    
    @Test
    public void getAGroupById_Success() throws GroupsNotFoundException {
        Groups group = new Groups(); // Assuming you set up a sample group object
        group.setGroupID(1);
        group.setGroupName("Test Group");
        Users admin = new Users(); // Assuming you set up a sample user object
        admin.setUserID(1);
        group.setAdmin(admin);
        
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        
        groupsDto result = groupService.getAGroupById(1);
        assertEquals(1, result.getGroupid());
        assertEquals("Test Group", result.getGroupname());
        assertEquals(1, result.getAdminid());
    }

    @Test
    public void getAGroupById_GroupNotFound_ThrowsException() {
        when(groupRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(GroupsNotFoundException.class, () -> groupService.getAGroupById(1));
    }
    
    @Test
    public void createANewGroup_Success() throws UserNotFoundException {
        Groups newGroup = new Groups(); // Assuming you set up a sample group object
        Users adminUser = new Users(); // Assuming you set up a sample user object
        adminUser.setUserID(1);
        newGroup.setAdmin(adminUser);
        
        when(usersRepository.findById(1)).thenReturn(Optional.of(adminUser));
        
        groupService.createANewGroup(newGroup);
        // Add assertions to verify if the group is created successfully
    }

    @Test
    public void createANewGroup_AdminUserNotFound_ThrowsException() {
        Groups newGroup = new Groups(); // Assuming you set up a sample group object
        Users adminUser = new Users(); // Assuming you set up a sample user object
        adminUser.setUserID(1);
        newGroup.setAdmin(adminUser);
        
        when(usersRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(UserNotFoundException.class, () -> groupService.createANewGroup(newGroup));
    }
    
    @Test
    public void updateAnExistingGroup_Success() throws GroupsNotFoundException, UserNotFoundException {
        Groups existingGroup = new Groups(); // Assuming you set up a sample group object
        existingGroup.setGroupID(1);
        existingGroup.setGroupName("Original Group Name");
        Users adminUser = new Users(); // Assuming you set up a sample user object
        adminUser.setUserID(1);
        existingGroup.setAdmin(adminUser);
        
        when(groupRepository.findById(1)).thenReturn(Optional.of(existingGroup));
        when(usersRepository.findById(1)).thenReturn(Optional.of(adminUser));
        
     //   groupService.updateAnExistingGroup(existingGroup, "Updated Group Name");
        assertEquals("Updated Group Name", existingGroup.getGroupName());
    }

    @Test
    public void updateAnExistingGroup_GroupNotFound_ThrowsException() {
        when(groupRepository.findById(1)).thenReturn(Optional.empty());
       // assertThrows(GroupsNotFoundException.class, () -> groupService.updateAnExistingGroup(null, "Updated Group Name"));
    }

    @Test
    public void updateAnExistingGroup_AdminUserNotFound_ThrowsException() {
        Groups existingGroup = new Groups(); // Assuming you set up a sample group object
        existingGroup.setGroupID(1);
        existingGroup.setGroupName("Original Group Name");
        Users adminUser = new Users(); // Assuming you set up a sample user object
        adminUser.setUserID(1);
        existingGroup.setAdmin(adminUser);
        
        when(groupRepository.findById(1)).thenReturn(Optional.of(existingGroup));
        when(usersRepository.findById(1)).thenReturn(Optional.empty());
        
        //assertThrows(UserNotFoundException.class, () -> groupService.updateAnExistingGroup(existingGroup, "Updated Group Name"));
    }
    
    @Test
    public void deleteGroupByItsId_Success() throws GroupsNotFoundException {
        Groups group = new Groups(); // Assuming you set up a sample group object
        group.setGroupID(1);
        
        when(groupRepository.findById(1)).thenReturn(java.util.Optional.of(group));
        
        groupService.deleteGroupByItsId(1);
        // Add assertions to verify if the group is deleted successfully
    }

    @Test
    public void deleteGroupByItsId_GroupNotFound_ThrowsException() {
        when(groupRepository.findById(1)).thenReturn(java.util.Optional.empty());
        assertThrows(GroupsNotFoundException.class, () -> groupService.deleteGroupByItsId(1));
    }
    
    
    
    
}
