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

import com.example.product.entity.Comments;
import com.example.product.entity.Posts;
import com.example.product.entity.Users;
import com.example.product.exception.CommentNotFoundException;
import com.example.product.exception.PostsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.CommentRepository;
import com.example.product.repository.PostRepository;
import com.example.product.repository.UsersRepository;
import com.example.product.service.CommentsServiceImpl;

@SpringBootTest
public class CommentsServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UsersRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentsServiceImpl commentService;

    @Test
    public void getAllComments_Success() {
        List<Comments> comments = new ArrayList<>();
        // Add some sample comments to the list
        // Assuming you have some test data set up properly
        
        when(commentRepository.findAll()).thenReturn(comments);
        
        List<Comments> result = commentService.getAllComments();
        assertEquals(comments.size(), result.size());
    }

    @Test
    public void getCommentById_Success() throws CommentNotFoundException {
        Comments comment = new Comments(); // Assuming you set up a sample comment object
        comment.setCommentID(1);
        // Set other properties of the comment
        
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        
        Comments result = commentService.getCommentbyId(1);
        assertEquals(comment, result);
    }

    @Test
    public void getCommentById_CommentNotFound_ThrowsException() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(CommentNotFoundException.class, () -> commentService.getCommentbyId(1));
    }

    @Test
    public void addComment_Success() throws UserNotFoundException, PostsNotFoundException {
        Comments comment = new Comments(); // Assuming you set up a sample comment object
        Users user = new Users(); // Assuming you set up a sample user object
        Posts post = new Posts(); // Assuming you set up a sample post object
        
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        
        commentService.addComment(comment, 1, 1);
        // Add assertions if necessary to verify the behavior
    }

    

    @Test
    public void updateComment_CommentNotFound_ThrowsException() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(CommentNotFoundException.class, () -> commentService.updateComment(new Comments(), 1));
    }

    @Test
    public void deleteComment_Success() throws CommentNotFoundException {
        Comments comment = new Comments(); // Assuming you set up a sample comment object
        comment.setCommentID(1);
        // Set other properties of the comment
        
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        
        commentService.deleteComment(1);
        // Add assertions if necessary to verify the behavior
    }

    @Test
    public void deleteComment_CommentNotFound_ThrowsException() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(CommentNotFoundException.class, () -> commentService.deleteComment(1));
    }
}
