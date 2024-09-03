package com.example.product.service;

import java.util.List;

import com.example.product.dto.groupsDto;
import com.example.product.entity.Groups;
import com.example.product.exception.GroupsNotFoundException;
import com.example.product.exception.UserNotFoundException;

public interface GroupService {
	List<groupsDto> getAllListOfGroups();
	groupsDto getAGroupById(int groupId) throws GroupsNotFoundException;
	void createANewGroup(Groups newgroup)throws UserNotFoundException;
	public void updateGroup(int groupId, groupsDto groupRequest)throws GroupsNotFoundException,UserNotFoundException;
	//void deleteGroupByItsId(int groupId)throws GroupsNotFoundException;
	  void deleteGroupByItsId(int groupId) throws GroupsNotFoundException;
	

 

}
