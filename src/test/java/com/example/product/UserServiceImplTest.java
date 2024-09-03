package com.example.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
import com.example.product.service.UsersServiceImpl;

class UsersServiceImplTest {

    @Mock
    private UsersRepository userRepository;

    @Mock
    private FriendsRepository friendsRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private UsersServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserById_ExistingUser() throws UserNotFoundException {
        // Arrange
        Users user = new Users();
        user.setUserID(1);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        UsersDto result = userService.getUserbyId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUserid());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("password", result.getPassword());
    }

    @Test
    void testGetUserById_NonExistingUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserbyId(1));
    }

    // Add more test cases for other methods as needed

    @Test
    void testGetAllUsers() {
        // Arrange
        Users user1 = new Users();
        user1.setUserID(1);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");

        Users user2 = new Users();
        user2.setUserID(2);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");

        List<Users> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UsersDto> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getUserid());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user1@example.com", result.get(0).getEmail());
        assertEquals("password1", result.get(0).getPassword());
        assertEquals(2, result.get(1).getUserid());
        assertEquals("user2", result.get(1).getUsername());
        assertEquals("user2@example.com", result.get(1).getEmail());
        assertEquals("password2", result.get(1).getPassword());
    }

    @Test
    void testGetUserbyName_ExistingUser() throws UserNotFoundException {
        // Arrange
        Users user = new Users();
        user.setUserID(1);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // Act
        UsersDto result = userService.getUserbyName("testuser");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUserid());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("password", result.getPassword());
    }

    @Test
    void testGetUserbyName_NonExistingUser() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserbyName("nonexistentuser"));
    }

    @Test
    void testAddUsers() {
        // Arrange
        UsersDto userDto = new UsersDto();
        userDto.setUserid(1);
        userDto.setUsername("testuser");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");

        Users user = new Users();
        user.setUserID(1);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.save(any(Users.class))).thenReturn(user);

        // Act
        userService.addUsers(userDto);

        // Assert
        verify(userRepository, times(1)).save(any(Users.class));
    }

    // Add more test cases for other methods as needed

    @Test
    void testUpdateUsers() throws UserNotFoundException {
        // Arrange
        UsersDto newUserDetails = new UsersDto();
        newUserDetails.setUserid(1);
        newUserDetails.setUsername("newuser");
        newUserDetails.setEmail("new@example.com");
        newUserDetails.setPassword("newpassword");

        Users existingUser = new Users();
        existingUser.setUserID(1);
        existingUser.setUsername("testuser");
        existingUser.setEmail("test@example.com");
        existingUser.setPassword("password");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(Users.class))).thenReturn(existingUser);

        // Act
        userService.updateUsers(newUserDetails, 1);

        // Assert
        verify(userRepository, times(1)).save(any(Users.class));
        assertEquals("newuser", existingUser.getUsername());
        assertEquals("new@example.com", existingUser.getEmail());
        assertEquals("newpassword", existingUser.getPassword());
    }

    @Test
    void testDeleteUser() throws UserNotFoundException {
        // Arrange
        Users existingUser = new Users();
        existingUser.setUserID(1);
        existingUser.setUsername("testuser");
        existingUser.setEmail("test@example.com");
        existingUser.setPassword("password");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        // Act
        userService.deleteUser(1);

        // Assert
        verify(userRepository, times(1)).delete(existingUser);
    }
    
    @Test
    void testGetallPostOfUser() throws UserNotFoundException {
        // Arrange
        Users user = new Users();
        user.setUserID(1);
        user.setUsername("testuser");

        Posts post1 = new Posts();
        post1.setPostID(1);
        post1.setContent("Post 1");
        post1.setTimestamp(new Timestamp(System.currentTimeMillis()));

        Posts post2 = new Posts();
        post2.setPostID(2);
        post2.setContent("Post 2");
        post2.setTimestamp(new Timestamp(System.currentTimeMillis()));

        user.setPosts(List.of(post1, post2));

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        List<PostsDto> result = userService.getallPostOfUser(1);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getPostId());
        assertEquals("Post 1", result.get(0).getContent());
        assertEquals(2, result.get(1).getPostId());
        assertEquals("Post 2", result.get(1).getContent());
    }

   

    @Test
    void testGetAllCommentsInPostByUser() throws UserNotFoundException {
        // Arrange
        Users user = new Users();
        user.setUserID(1);
        user.setUsername("testuser");

        Posts post = new Posts();
        post.setPostID(1);
        post.setContent("Post Content");

        Comments comment1 = new Comments();
        comment1.setCommentID(1);
        comment1.setComment_text("Comment 1");
        comment1.setTimestamp(new Timestamp(System.currentTimeMillis()));
        comment1.setUsers(user);

        Comments comment2 = new Comments();
        comment2.setCommentID(2);
        comment2.setComment_text("Comment 2");
        comment2.setTimestamp(new Timestamp(System.currentTimeMillis()));
        comment2.setUsers(user);

        post.setComments(List.of(comment1, comment2));
        user.setPosts(List.of(post));

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        List<CommentsDto> result = userService.getAllCommentsInPostByUser(1);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getCommentID());
        assertEquals("Comment 1", result.get(0).getComment_text());
        assertEquals(2, result.get(1).getCommentID());
        assertEquals("Comment 2", result.get(1).getComment_text());
    }

    

    @Test
    void testSendRequest() throws UserNotFoundException {
        // Arrange
        Users fromUser = new Users();
        fromUser.setUserID(1);

        Users toUser = new Users();
        toUser.setUserID(2);

        when(userRepository.findById(1)).thenReturn(Optional.of(fromUser));
        when(userRepository.findById(2)).thenReturn(Optional.of(toUser));

        // Act
        String result = userService.sendRequest(1, 2);

        // Assert
        assertEquals("Friend request sent successfully.", result);
    }

    

    @Test
    void testGetLikesPostByUser() throws UserNotFoundException {
        // Arrange
        Users user = new Users();
        user.setUserID(1);

        Posts post = new Posts();
        post.setPostID(1);

        Likes like1 = new Likes();
        like1.setLikeID(1);
        like1.setUsers(user);
        like1.setPosts(post);

        Likes like2 = new Likes();
        like2.setLikeID(2);
        like2.setUsers(user);
        like2.setPosts(post);

        post.setLikes(List.of(like1, like2));

        user.setPosts(List.of(post));

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        List<Likesdto> result = userService.getlikesPostbyUser(1);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getLikeid());
        assertEquals(1, result.get(0).getUserid());
        assertEquals(2, result.get(1).getLikeid());
        assertEquals(1, result.get(1).getUserid());
    }
    
    @Test
    void testGetnotification() throws UserNotFoundException {
        // Arrange
        Users user = new Users();
        user.setUserID(1);
        user.setUsername("testuser");

        // Create a list of notifications and add them to the user
        List<Notifications> notifications = new ArrayList<>();
        Notifications notification1 = new Notifications();
        notification1.setNotificationID(1);
        notification1.setContent("Notification 1");

        Notifications notification2 = new Notifications();
        notification2.setNotificationID(2);
        notification2.setContent("Notification 2");

        notifications.add(notification1);
        notifications.add(notification2);

        user.setNotifications(notifications);

        // Mock the repository call
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        List<Notifications> result = userService.getnotification(1);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getNotificationID());
        assertEquals("Notification 1", result.get(0).getContent());
        assertEquals(2, result.get(1).getNotificationID());
        assertEquals("Notification 2", result.get(1).getContent());
    }


    @Test
    void testAddFriend() throws UserNotFoundException {
        // Arrange
        Users user1 = new Users();
        user1.setUserID(1);
        user1.setUsername("user1");

        Users user2 = new Users();
        user2.setUserID(2);
        user2.setUsername("user2");

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(Optional.of(user2));

        // Act
        String result = userService.AddFriend(1, 2);

        // Assert
        assertEquals("Friend successfully added.", result);
    }

    

    

}
