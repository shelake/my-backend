package com.example.product.controller;
 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.dto.ExceptionMessage;
import com.example.product.dto.groupsDto;
import com.example.product.entity.Groups;
import com.example.product.entity.Users;
import com.example.product.exception.FriendsNotFoundException;
import com.example.product.exception.GroupsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.UsersRepository;
import com.example.product.service.GroupService;
 
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/groups/")

public class GroupsController {
	@Autowired
	GroupService groupsService;
	@Autowired
	UsersRepository usersRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<groupsDto>> retrieveListOfGroups(){
		return new ResponseEntity<List<groupsDto>>
		       (groupsService.getAllListOfGroups(),HttpStatus.OK);
	}
	
	@GetMapping("/{groupId}")
	public ResponseEntity<groupsDto> getGroupById(@PathVariable("groupId") int groupId)throws GroupsNotFoundException{
		return new ResponseEntity<groupsDto>
				(groupsService.getAGroupById(groupId),HttpStatus.OK);
	}

	
	@PostMapping("/{groupName}/{adminid}")
	public ResponseEntity<Void> createNewGroup(@PathVariable("groupName")String groupname, @PathVariable("adminid") int adminid) throws UserNotFoundException {

		Groups newgroup=new Groups();
		Users groupadmin=usersRepository.findById(adminid).get();
		newgroup.setGroupName(groupname);
		newgroup.setAdmin(groupadmin);
		 if (newgroup.getAdmin() == null) {
		        
		        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		    }
		    groupsService.createANewGroup(newgroup);
		    return new ResponseEntity<>(HttpStatus.CREATED);
 
	}
	
	
//	@PutMapping("/{groupId}")
//	public ResponseEntity<Void> updateGroup(
//	        @PathVariable("groupId") int groupId,
//	        @RequestBody groupsDto groupRequest) throws GroupsNotFoundException, UserNotFoundException {
//
//	    groupsService.updateGroup(groupId, groupRequest);
//	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}

	@PutMapping("/{groupId}")
	public ResponseEntity<Void> updateGroup(
	        @PathVariable("groupId") int groupId,
	        @RequestBody groupsDto groupRequest) throws GroupsNotFoundException, UserNotFoundException {

	    // Debugging: Print out groupRequest values
	    System.out.println("Group ID: " + groupId);
	    System.out.println("Group Name: " + groupRequest.getGroupname());
	    System.out.println("Admin ID: " + groupRequest.getAdminid());

	    groupsService.updateGroup(groupId, groupRequest);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@DeleteMapping("/{groupId}")
	public ResponseEntity<Void> deleteGroupById(@PathVariable("groupId") int groupId) throws GroupsNotFoundException {
	    groupsService.deleteGroupByItsId(groupId);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<ExceptionMessage> handleUsersNotFoundException(UserNotFoundException ex){
		ExceptionMessage message=new ExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler({FriendsNotFoundException.class})
	public ResponseEntity<ExceptionMessage> handleFriendsNotFoundException(FriendsNotFoundException ex){
		ExceptionMessage message=new ExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);
	}
//	@ExceptionHandler({GroupsNotFoundException.class})
//	public ResponseEntity<ExceptionMessage> handleGroupsNotFoundException(GroupsNotFoundException ex){
//		ExceptionMessage message=new ExceptionMessage(ex.getMessage());
//		return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);
//	}
	@ExceptionHandler(GroupsNotFoundException.class)
	public ResponseEntity<ExceptionMessage> handleGroupsNotFoundException(GroupsNotFoundException ex) {
	    ExceptionMessage message = new ExceptionMessage(ex.getMessage());
	    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	
 
}