package com.example.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product.dto.groupsDto;
import com.example.product.entity.Groups;
import com.example.product.entity.Users;
import com.example.product.exception.GroupsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.GroupRepository;
import com.example.product.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupRepository groupsRepository;
	@Autowired
	UsersRepository usersRepository;

	@Override
	public List<groupsDto> getAllListOfGroups() {
		List<Groups> groups = groupsRepository.findAll();
		List<groupsDto> groupsdto = new ArrayList<>();

		for (Groups g : groups) {
			groupsDto gd = new groupsDto();
			gd.setGroupid(g.getGroupID());
			gd.setGroupname(g.getGroupName());
			gd.setAdminid(g.getAdmin().getUserID());
			groupsdto.add(gd);
		}

		return groupsdto;
	}

	@Override
	public groupsDto getAGroupById(int groupId) throws GroupsNotFoundException {
		if (groupsRepository.findById(groupId).isEmpty())
			throw new GroupsNotFoundException("The group with group id " + groupId + " doesn't exists");
		Groups g = groupsRepository.findById(groupId).get();
		groupsDto gd = new groupsDto();
		gd.setGroupid(g.getGroupID());
		gd.setGroupname(g.getGroupName());
		gd.setAdminid(g.getAdmin().getUserID());

		return gd;
	}

	@Override
	public void createANewGroup(Groups newgroup) throws UserNotFoundException {

		if (newgroup.getAdmin() == null) {

			throw new UserNotFoundException("The Users object associated with the Groups object is null.");
		}

		int adminUserID = newgroup.getAdmin().getUserID();
		Optional<Users> adminUserOptional = usersRepository.findById(adminUserID);
		if (adminUserOptional.isEmpty()) {
			throw new UserNotFoundException(
					"The admin with ID " + adminUserID + " doesn't exist. Therefore, the group cannot be created.");
		}

		newgroup.setAdmin(adminUserOptional.get());
		groupsRepository.save(newgroup);
	}
//
//	@Override
//	public void updateGroup(int groupId, groupsDto groupRequest) throws GroupsNotFoundException, UserNotFoundException {
//	    Groups existingGroup = groupsRepository.findById(groupId)
//	            .orElseThrow(() -> new GroupsNotFoundException("The group with group ID " + groupId + " doesn't exist."));
//
//	    if (existingGroup.getAdmin() == null) {
//	        throw new UserNotFoundException("Users associated with the existing group is null.");
//	    }
//
//	    // Check if the admin user exists
//	    int adminUserID = groupRequest.getAdminid();
//	    Optional<Users> adminUserOptional = usersRepository.findById(adminUserID);
//
//	    if (adminUserOptional.isEmpty()) {
//	        throw new UserNotFoundException("The admin with ID " + adminUserID + " doesn't exist.");
//	    }
//
//	    // Update the group details
//	    existingGroup.setGroupName(groupRequest.getGroupname());
//	    existingGroup.setAdmin(adminUserOptional.get());
//
//	    // Save the updated group
//	    groupsRepository.save(existingGroup);
//	}
	@Override
	public void updateGroup(int groupId, groupsDto groupRequest) throws GroupsNotFoundException, UserNotFoundException {
	    Groups existingGroup = groupsRepository.findById(groupId)
	            .orElseThrow(() -> new GroupsNotFoundException("The group with group ID " + groupId + " doesn't exist."));

	    if (existingGroup.getAdmin() == null) {
	        throw new UserNotFoundException("Users associated with the existing group is null.");
	    }

	    // Check if the admin user exists
	    int adminUserID = groupRequest.getAdminid();
	    if (adminUserID <= 0) {
	        throw new UserNotFoundException("Invalid admin ID: " + adminUserID);
	    }

	    Optional<Users> adminUserOptional = usersRepository.findById(adminUserID);
	    if (adminUserOptional.isEmpty()) {
	        throw new UserNotFoundException("The admin with ID " + adminUserID + " doesn't exist.");
	    }

	    // Update the group details
	    existingGroup.setGroupName(groupRequest.getGroupname());
	    existingGroup.setAdmin(adminUserOptional.get());

	    // Save the updated group
	    groupsRepository.save(existingGroup);
	}


//	@Override
//	@Transactional
//	public void deleteGroupByItsId(int groupId) throws GroupsNotFoundException {
//		if (groupsRepository.findById(groupId).isEmpty())
//			throw new GroupsNotFoundException("The group with group id " + groupId + " doesn't exists");
//
//		groupsRepository.deleteGroupsNative(groupId);
//	}
	@Override
	@Transactional
	public void deleteGroupByItsId(int groupId) throws GroupsNotFoundException {
	    if (!groupsRepository.existsById(groupId)) {
	        throw new GroupsNotFoundException("The group with group ID " + groupId + " doesn't exist.");
	    }

	    groupsRepository.deleteById(groupId);
	}
	
	

}
