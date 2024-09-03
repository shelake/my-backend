package com.example.product;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.product.entity.Message;
import com.example.product.entity.Users;
import com.example.product.exception.MessageNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.MessageRepository;
import com.example.product.repository.UsersRepository;
import com.example.product.service.MessageServiceImpl;

@SpringBootTest
public class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;
    

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    public void getAllMessages_Success() {
        List<Message> messageList = new ArrayList<>();
        // Add some sample messages to the list
        // Assuming you have some test data set up properly
        when(messageRepository.findAll()).thenReturn(messageList);
        
        List<Message> result = messageService.getAllMessages();
        assertEquals(messageList.size(), result.size());
    }
    
    @Test
    public void getMessageById_Success() throws MessageNotFoundException {
        Message message = new Message(); // Assuming you set up a sample message object
        when(messageRepository.findById(1)).thenReturn(Optional.of(message));
        
        Message result = messageService.getMessagebyId(1);
        assertEquals(message, result);
    }

    @Test
    public void getMessageById_MessageNotFound_ThrowsException() {
        when(messageRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(MessageNotFoundException.class, () -> messageService.getMessagebyId(1));
    }
    
    @Test
    public void deleteMessage_Success() throws MessageNotFoundException {
        when(messageRepository.findById(1)).thenReturn(Optional.of(new Message())); // Assuming message exists
        doNothing().when(messageRepository).deleteMessageNative(1);
        
        messageService.deleteMessage(1);
    }

    @Test
    public void deleteMessage_MessageNotFound_ThrowsException() {
        when(messageRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(MessageNotFoundException.class, () -> messageService.deleteMessage(1));
    }
    
    @Test
    public void createMessage_Success() throws UserNotFoundException {
        Users sender = new Users(); // Assuming you set up sender properly
        Users receiver = new Users(); // Assuming you set up receiver properly
        when(usersRepository.findById(1)).thenReturn(Optional.of(sender));
        when(usersRepository.findById(2)).thenReturn(Optional.of(receiver));

        Message message = new Message();
        message.setMessage_text("Test message");
        message.setSenderID(sender);
        message.setReceiverID(receiver);

        when(messageRepository.save(message)).thenReturn(message);

        String result = messageService.createMessage(1, 2, "Test message");
        assertEquals("Message sent successfully", result);
    }

    @Test
    public void createMessage_SenderNotFound_ThrowsException() {
        when(usersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> messageService.createMessage(1, 2, "Test message"));
    }

    @Test
    public void createMessage_ReceiverNotFound_ThrowsException() {
        Users sender = new Users(); // Assuming you set up sender properly
        when(usersRepository.findById(1)).thenReturn(Optional.of(sender));
        when(usersRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> messageService.createMessage(1, 2, "Test message"));
    }
    
    @Test
    public void updateMessage_Success() throws MessageNotFoundException {
        Message existingMessage = new Message(); // Assuming you set up an existing message properly
        existingMessage.setMessage_text("Original message");
        when(messageRepository.findById(1)).thenReturn(Optional.of(existingMessage));

        Message updatedMessage = new Message();
        updatedMessage.setMessage_text("Updated message");

        when(messageRepository.save(existingMessage)).thenReturn(updatedMessage);

        Message result = messageService.UpdateMessage(updatedMessage, 1);
        assertEquals("Updated message", result.getMessage_text());
    }

    @Test
    public void updateMessage_MessageNotFound_ThrowsException() {
        when(messageRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(MessageNotFoundException.class, () -> {
            Message message = new Message(); // Assuming you set up a message properly
            messageService.UpdateMessage(message, 1);
        });
    }
}

