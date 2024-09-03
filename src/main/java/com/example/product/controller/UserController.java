package com.example.product.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.dto.CommentsDto;
import com.example.product.dto.ExceptionMessage;
import com.example.product.dto.Friendsdto;
import com.example.product.dto.Likesdto;
import com.example.product.dto.PostsDto;
import com.example.product.dto.UsersDto;
import com.example.product.dto.groupsDto;
import com.example.product.entity.Message;
import com.example.product.entity.Notifications;
import com.example.product.exception.PostsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.service.UsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins ="http://localhost:3000")

public class UserController {

	@Autowired
	UsersService service;

	@GetMapping("/{userid}")
	public ResponseEntity<UsersDto> getUsersById(@PathVariable("userid") int UserId) throws UserNotFoundException {
		return new ResponseEntity<UsersDto>(service.getUserbyId(UserId), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<UsersDto>> getAllUsers() {
		return new ResponseEntity<List<UsersDto>>(service.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/search/{username}")
	public ResponseEntity<UsersDto> getUsersByname(@PathVariable("username") String name) throws UserNotFoundException {
		return new ResponseEntity<UsersDto>(service.getUserbyName(name), HttpStatus.OK);
	}

	@GetMapping("/posts/{userid}")
	public ResponseEntity<List<PostsDto>> getallPosts(@PathVariable("userid") int UserId)
			throws UserNotFoundException, PostsNotFoundException {
		return new ResponseEntity<List<PostsDto>>(service.getallPostOfUser(UserId), HttpStatus.OK);
	}

	@GetMapping("/{userid}/friends")
	public ResponseEntity<List<Friendsdto>> getallFriends(@PathVariable("userid") int UserId) throws UserNotFoundException {
		return new ResponseEntity<List<Friendsdto>>(service.getAllFriendsOfUser(UserId), HttpStatus.OK);
	}

	@GetMapping("/{userid}/friend-requests/pending")
				//v1/users/17/friend-requests/pending
	public ResponseEntity<List<Friendsdto>> getallFriendRequest(@PathVariable("userid") int UserId) throws UserNotFoundException{
		return new ResponseEntity<List<Friendsdto>>(service.getAllRequestInPending(UserId), HttpStatus.OK);
	}

	@GetMapping("/{userid}/posts/comments")
	public ResponseEntity<List<CommentsDto>> getallCommentonPosts(@PathVariable("userid") int UserId) throws UserNotFoundException{
		return new ResponseEntity<List<CommentsDto>>(service.getAllCommentsInPostByUser(UserId), HttpStatus.OK);
	}	

	@GetMapping("/{userId}/messages/{otherUserId}")
	public ResponseEntity<List<Message>> getallMessages(
			@PathVariable int userId, 
			@PathVariable int otherUserId)
					throws UserNotFoundException{
		return new ResponseEntity<List<Message>>(service.getAllMessageBetweenUser(userId, otherUserId), HttpStatus.OK);
	}

	@GetMapping("/{userid}/posts/likes")
	public ResponseEntity<List<Likesdto>> getallLikes(@PathVariable("userid") int UserId) throws UserNotFoundException{
		return new ResponseEntity<List<Likesdto>>(service.getlikesPostbyUser(UserId), HttpStatus.OK);
	}

	@GetMapping("/{userid}/notifications")
	public ResponseEntity<List<Notifications>> getallNotifications(@PathVariable("userid") int UserId) throws UserNotFoundException{
		return new ResponseEntity<List<Notifications>>(service.getnotification(UserId), HttpStatus.OK);
	}

	@GetMapping("/{userid}/groups")
	public ResponseEntity<List<groupsDto>> getgroups(@PathVariable("userid") int UserId) throws UserNotFoundException{
		return new ResponseEntity<List<groupsDto>>(service.getGroups(UserId), HttpStatus.OK);
	}
	
	@GetMapping("/{userid}/likes")
	public ResponseEntity<List<PostsDto>> getallPostsLikedByUser(@PathVariable("userid") int UserId) throws UserNotFoundException{
		return new ResponseEntity<List<PostsDto>>(service.getAllPostsLikedByUser(UserId), HttpStatus.OK);
	}
	
	@GetMapping("/random")
	public ResponseEntity<List<UsersDto>> getRandomUsers(){
		return new ResponseEntity<List<UsersDto>>(service.getRandomUsers(), HttpStatus.OK);
	}

	 @PostMapping()
	    public ResponseEntity<Void> createUser(@Valid @RequestBody UsersDto users) {
	        service.addUsers(users);
	        return ResponseEntity.status(HttpStatus.CREATED).build();
	    }

	@PutMapping("/update/{userid}")
	public ResponseEntity<Void> UpdateUser( @Valid @RequestBody UsersDto user, @PathVariable("userid") int userid) throws UserNotFoundException {
		service.updateUsers(user, userid);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@DeleteMapping("/delete/{userid}")
	public ResponseEntity<Void> deleteUsers(@PathVariable("userid") int userid) throws UserNotFoundException {
		service.deleteUser(userid);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();

	}

	@PostMapping("/{userId}/friendrequest/send/{otherUserId}")
	public ResponseEntity<String> sendRequest(
			@PathVariable("userId") int userId,
		    @PathVariable("otherUserId") int otherUserId
	) throws UserNotFoundException {
	    String result = service.sendRequest(userId, otherUserId);
	    return new ResponseEntity<>(result, HttpStatus.CREATED);
	}


	@PostMapping("/{userId}/friends/{otherUserId}")
	public ResponseEntity<String> AddFriend(@PathVariable int userId, @PathVariable int otherUserId) throws UserNotFoundException {
		String result = service.AddFriend(userId, otherUserId);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PostMapping("/{userId}/{message}/send/{otherUserId}")
	public ResponseEntity<String> sendMessage(
			@PathVariable int userId, 
			@PathVariable int otherUserId,
			@RequestParam String message)
					throws UserNotFoundException {
		String result = service.sendMessage(userId, otherUserId, message);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}/friends/{otherUserId}")
	public ResponseEntity<String> RemoveFriends(@PathVariable int userId, @PathVariable int otherUserId) throws UserNotFoundException {
		String result = service.removeFriend(userId, otherUserId);
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}

	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> UserNotFoundException(UserNotFoundException ex) {
		ExceptionMessage message = new ExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> PostsNotFoundException(PostsNotFoundException ex) {
		ExceptionMessage message = new ExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);

	}
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//	    return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
//	}

}
