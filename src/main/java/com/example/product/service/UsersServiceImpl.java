package com.example.product.service;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product.dto.CommentsDto;
import com.example.product.dto.Friendsdto;
import com.example.product.dto.Likesdto;
import com.example.product.dto.PostsDto;
import com.example.product.dto.UsersDto;
import com.example.product.dto.groupsDto;
import com.example.product.dto.statustype;
import com.example.product.entity.Comments;
import com.example.product.entity.Friends;
import com.example.product.entity.Groups;
import com.example.product.entity.Likes;
import com.example.product.entity.Message;
import com.example.product.entity.Notifications;
import com.example.product.entity.Posts;
import com.example.product.entity.Users;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.FriendsRepository;
import com.example.product.repository.MessageRepository;
import com.example.product.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	UsersRepository userrepo;

	@Autowired
	FriendsRepository friendsrepo;

	@Autowired
	MessageRepository messagerepo;

	@Override
	public UsersDto getUserbyId(int userId) throws UserNotFoundException {
		if (userrepo.findById(userId).isEmpty())
			throw new UserNotFoundException("User not found");

		Users u = userrepo.findById(userId).get();

		UsersDto users = new UsersDto();
		users.setUserid(u.getUserID());
		users.setUsername(u.getUsername());
		users.setEmail(u.getEmail());
		users.setPassword(u.getPassword());
		users.setProfile_picture(u.getProfilePicture());
		return users;
	}

	@Override
	public List<UsersDto> getAllUsers() {

		List<Users> usersList = userrepo.findAll();
		List<UsersDto> usersdtoList = new ArrayList<>();

		for (Users u : usersList) {
			UsersDto users = new UsersDto();
			users.setUserid(u.getUserID());
			users.setUsername(u.getUsername());
			users.setEmail(u.getEmail());
			users.setPassword(u.getPassword());
			users.setProfile_picture(u.getProfilePicture());
			usersdtoList.add(users);

		}
		return usersdtoList;
	}

	@Override
	public UsersDto getUserbyName(String name) throws UserNotFoundException {
		UsersDto usersDto = new UsersDto();

		Optional<Users> userOptional = userrepo.findAll().stream().filter(u -> u.getUsername().equals(name))
				.findFirst();

		Users user = userOptional.orElseThrow(() -> new UserNotFoundException("User not found with name: " + name));

		usersDto.setUserid(user.getUserID());
		usersDto.setUsername(user.getUsername());
		usersDto.setEmail(user.getEmail());
		usersDto.setPassword(user.getPassword());
		usersDto.setProfile_picture(user.getProfilePicture());

		return usersDto;
	}

	@Override
	public void addUsers(UsersDto user) {
		Users u = new Users();
		u.setUsername(user.getUsername());
		u.setEmail(user.getEmail());
		u.setPassword(user.getPassword());
		u.setProfilePicture(user.getProfile_picture());

		userrepo.save(u);

	}

	@Override
	public void updateUsers(UsersDto newUserDetails, int userId) throws UserNotFoundException {
		Users existingUser = userrepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		existingUser.setUsername(newUserDetails.getUsername());
		existingUser.setEmail(newUserDetails.getEmail());
		existingUser.setPassword(newUserDetails.getPassword());

		userrepo.save(existingUser);
	}

	@Override
	public void deleteUser(int userId) throws UserNotFoundException {
		Users existingUser = userrepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
		userrepo.delete(existingUser);
	}

	@Override
	public List<PostsDto> getallPostOfUser(int userId) throws UserNotFoundException {
		Users user = userrepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
		List<PostsDto> postdto = new ArrayList<>();

		List<Posts> posts = user.getPosts().stream().collect(Collectors.toList());

		for (Posts p : posts) {
			PostsDto d = new PostsDto();
			d.setPostId(p.getPostID());
			d.setContent(p.getContent());
			d.setTimestamp(p.getTimestamp());
			postdto.add(d);
		}

		return postdto;

	}

	@Override
	public List<Friendsdto> getAllFriendsOfUser(int userId) throws UserNotFoundException {

		Users user = userrepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
		List<Friends> allFriends = new ArrayList<>();
		allFriends.addAll(user.getUserID1());
		allFriends.addAll(user.getUserID2());

		List<Friends> acceptedFriends = allFriends.stream().filter(f -> f.getStatus() == statustype.ACCEPTED)
				.collect(Collectors.toList());

		List<Friendsdto> friendsdto = new ArrayList<>();
		for (Friends f : acceptedFriends) {
			Friendsdto fd = new Friendsdto();
			fd.setFriendshipid(f.getFriendshipID());
			fd.setUserid1(f.getUserID1().getUserID());
			fd.setUserid2(f.getUserID2().getUserID());
			fd.setStatus(f.getStatus());
			friendsdto.add(fd);

		}

		return friendsdto;
	}

	@Override
	public List<CommentsDto> getAllCommentsInPostByUser(int userId) throws UserNotFoundException {
		Users user = userrepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
		List<Posts> posts = user.getPosts();

		List<Comments> allComments = new ArrayList<>();
		for (Posts post : posts) {
			allComments.addAll(post.getComments());
		}

		List<CommentsDto> commentsDto = new ArrayList<>();
		for (Comments comment : allComments) {
			CommentsDto cd = new CommentsDto();
			cd.setCommentID(comment.getCommentID());
			cd.setComment_text(comment.getComment_text());
			cd.setTimestamp(comment.getTimestamp());
			cd.setUserid(comment.getUsers().getUserID());
			commentsDto.add(cd);
		}

		return commentsDto;

	}

	@Override
	public List<Friendsdto> getAllRequestInPending(int userId) throws UserNotFoundException {
		Users user = userrepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
		List<Friends> allFriends = new ArrayList<>();
		allFriends.addAll(user.getUserID2());
		List<Friends> Friendrequest = allFriends.stream().filter(f -> f.getStatus() == statustype.PENDING)
				.collect(Collectors.toList());

		List<Friendsdto> friendsdto = new ArrayList<>();
		for (Friends f : Friendrequest) {
			Friendsdto fd = new Friendsdto();
			fd.setFriendshipid(f.getFriendshipID());
			fd.setUserid1(f.getUserID1().getUserID());
			fd.setUserid2(f.getUserID2().getUserID());
			fd.setStatus(f.getStatus());
			friendsdto.add(fd);

		}

		return friendsdto;

	}

	@Override
	public String sendRequest(int from, int to) throws UserNotFoundException {
		Friends newRequest = new Friends();
		if (userrepo.findById(from).isEmpty() || userrepo.findById(to).isEmpty()) {
			throw new UserNotFoundException("User not found");
		}

		Users fromUser = userrepo.findById(from).get();
		Users toUser = userrepo.findById(to).get();

		newRequest.setStatus(statustype.PENDING);
		newRequest.setUserID1(fromUser);
		newRequest.setUserID2(toUser);

		friendsrepo.save(newRequest);

		return "Friend request sent successfully.";
	}

	@Override
	public List<Message> getAllMessageBetweenUser(int send, int received) throws UserNotFoundException {
		if (userrepo.findById(send).isEmpty() || userrepo.findById(received).isEmpty()) {
			throw new UserNotFoundException("User not found");
		}

		Users sendUser = userrepo.findById(send).orElse(null);
		Users receiveUser = userrepo.findById(received).orElse(null);

		if (sendUser == null || receiveUser == null) {
			return Collections.emptyList(); // Handle if either user is not found
		}

		List<Message> result = new ArrayList<>();

		for (Message message : sendUser.getSenderID()) {
			if (message.getReceiverID().getUserID() == received) {
				result.add(message);
			}
		}

		for (Message message : receiveUser.getSenderID()) {
			if (message.getReceiverID().getUserID() == send) {
				result.add(message);
			}
		}

		return result;
	}

	@Override
	public String sendMessage(int from, int to, String message) throws UserNotFoundException {
		if (userrepo.findById(from).isEmpty() || userrepo.findById(to).isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		Message newMessage = new Message();

		Users fromUser = userrepo.findById(from).get();
		Users toUser = userrepo.findById(to).get();

		newMessage.setMessage_text(message);
		newMessage.setSenderID(fromUser);
		newMessage.setReceiverID(toUser);

		messagerepo.save(newMessage);

		return "Message sent successfully";

	}

	@Override
	public List<Likesdto> getlikesPostbyUser(int userid) throws UserNotFoundException {

		Users user = userrepo.findById(userid).orElseThrow(() -> new UserNotFoundException("User not found"));
		List<Posts> posts = user.getPosts().stream().collect(Collectors.toList());

		List<Likes> likes = new ArrayList<>();
		for (Posts p : posts) {
			likes.addAll(p.getLikes());
		}

		List<Likesdto> likesdto = new ArrayList<>();
		for (Likes l : likes) {
			Likesdto ld = new Likesdto();
			ld.setLikeid(l.getLikeID());
			ld.setTimestamp(l.getTimestamp());
			ld.setUserid(l.getUsers().getUserID());
			likesdto.add(ld);

		}

		return likesdto;

	}

	@Override
	public List<Notifications> getnotification(int userid) throws UserNotFoundException {
		Users user = userrepo.findById(userid).orElseThrow(() -> new UserNotFoundException("User not Found"));

		return user.getNotifications();
	}

	@Override
	public List<groupsDto> getGroups(int userid) throws UserNotFoundException {
		Users user = userrepo.findById(userid).orElseThrow(() -> new UserNotFoundException("User not found"));
		List<Groups> groups = user.getGroups();
		List<groupsDto> groupdto = new ArrayList<>();
		for (Groups g : groups) {
			groupsDto gd = new groupsDto();
			gd.setGroupid(g.getGroupID());
			gd.setGroupname(g.getGroupName());
			gd.setAdminid(g.getAdmin().getUserID());
			groupdto.add(gd);
		}
		return groupdto;

	}

	@Override
	public String AddFriend(int userid, int friendid) throws UserNotFoundException {
		if (userrepo.findById(userid).isEmpty() || userrepo.findById(friendid).isEmpty()) {
			throw new UserNotFoundException("User or friend not found");
		}

		Friends friend = new Friends();
		Users fromUser = userrepo.findById(userid).get();
		Users toUser = userrepo.findById(friendid).get();

		friend.setStatus(statustype.ACCEPTED);
		friend.setUserID1(fromUser);
		friend.setUserID2(toUser);

		friendsrepo.save(friend);

		return "Friend successfully added.";

	}

	@Override
	@Transactional
	public String removeFriend(int userid, int friendid) throws UserNotFoundException {
		if (userrepo.findById(userid).isEmpty() || userrepo.findById(friendid).isEmpty()) {
			throw new UserNotFoundException("User or friend not found");
		}

		Users user = userrepo.findById(userid).orElse(null);
		Users friend = userrepo.findById(friendid).orElse(null);

		List<Friends> allFriends = new ArrayList<>();
		allFriends.addAll(user.getUserID1());
		allFriends.addAll(user.getUserID2());

		for (Friends f : allFriends) {

			if ((f.getUserID1().getUserID() == userid && f.getUserID2().getUserID() == friendid)
					|| (f.getUserID1().getUserID() == friendid && f.getUserID2().getUserID() == userid)) {
				friendsrepo.deleteMessageNative(f.getFriendshipID());
				return "Friend successfully removed";
			}
		}
		return "No friendship found to remove";
	}

	@Override
	public List<PostsDto> getAllPostsLikedByUser(int userid) throws UserNotFoundException {
		Users user = userrepo.findById(userid).orElseThrow(() -> new UserNotFoundException("User not found"));
		List<Likes> likes = user.getLikes();
		List<PostsDto> allposts = new ArrayList<>();
		for (Likes l : likes) {
			Posts post = new Posts();
			post = l.getPosts();
			PostsDto pd = new PostsDto();
			pd.setPostId(post.getPostID());
			pd.setContent(post.getContent());
			pd.setTimestamp(post.getTimestamp());
			allposts.add(pd);

		}

		return allposts;

	}

	@Override
	public List<UsersDto> getRandomUsers() {
		// Fetch all users from the database
		List<Users> allUsers = userrepo.findAll();

		// Shuffle the list of users to randomize their order
		Collections.shuffle(allUsers);

		// Select a subset of random users from the shuffled list
		List<Users> randomUsers = allUsers.stream().limit(10).collect(Collectors.toList());

		// Convert the selected users to DTOs (UsersDto)
		List<UsersDto> randomUsersDto = randomUsers.stream().map(user -> {
			UsersDto userDto = new UsersDto();
			userDto.setUserid(user.getUserID());
			userDto.setUsername(user.getUsername());
			userDto.setProfile_picture(user.getProfilePicture());
			return userDto;
		}).collect(Collectors.toList());

		return randomUsersDto;
	}

}
