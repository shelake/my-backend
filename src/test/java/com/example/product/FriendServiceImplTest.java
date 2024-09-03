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
import com.example.product.entity.Friends;
import com.example.product.entity.Message;
import com.example.product.entity.Users;
import com.example.product.exception.FriendsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.FriendsRepository;
import com.example.product.repository.MessageRepository;
import com.example.product.service.FriendsServiceImpl;

@SpringBootTest
public class FriendServiceImplTest {

    @Mock
    private FriendsRepository friendsRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private FriendsServiceImpl friendsService;
    
    
    
    @Test
    public void getAllMessageBetweenFriends_FriendsNotFound_ThrowsException() {
        when(friendsRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(FriendsNotFoundException.class, () -> friendsService.getAllMessageBetweenFriends(1));
    }

    @Test
    public void getAllMessageBetweenFriends_UserNotFound_ThrowsException() {
        Friends friends = new Friends();
        Users user1 = new Users();
        user1.setUserID(1);
        friends.setUserID1(user1);
        // Assuming user2 is not set
        
        when(friendsRepository.findById(1)).thenReturn(Optional.of(friends));
        
        assertThrows(UserNotFoundException.class, () -> friendsService.getAllMessageBetweenFriends(1));
    }

    @Test
    public void sendMessageToAFriend_Success() throws FriendsNotFoundException, UserNotFoundException {
        // Setup sample data
        Friends friends = new Friends();
        Users user1 = new Users();
        user1.setUserID(1);
        Users user2 = new Users();
        user2.setUserID(2);
        friends.setUserID1(user1);
        friends.setUserID2(user2);
        // Assuming you set up a sample message text
        String messageText = "Hello, friend!";
        
        // Mock repository behavior
        when(friendsRepository.findById(1)).thenReturn(Optional.of(friends));
        
        // Execute the method
        String result = friendsService.sendMessageToAFriend(1, messageText);
        
        // Assertions
        assertEquals("Message sent successfully", result);
        // You can add more assertions if needed
    }

   

   
}
