package com.example.product;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.product.dto.CommentsDto;
import com.example.product.dto.Likesdto;
import com.example.product.dto.PostsDto;
import com.example.product.entity.Comments;
import com.example.product.entity.Likes;
import com.example.product.entity.Posts;
import com.example.product.entity.Users;
import com.example.product.exception.PostsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.CommentRepository;
import com.example.product.repository.LikesRepository;
import com.example.product.repository.PostRepository;
import com.example.product.repository.UsersRepository;
import com.example.product.service.PostsServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PostsServiceImplTest {

	@Mock
	private UsersRepository userRepo;

	@Mock
	private PostRepository Postrepo;

	@Mock
	private LikesRepository likesRepo;

	@Mock
	private CommentRepository commentRepo;

	@InjectMocks
	private PostsServiceImpl service;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void createPost_Success() throws UserNotFoundException {
		Users user = new Users(); // assuming constructor and setters
		user.setUserID(1);
		Posts post = new Posts();
		when(userRepo.findById(1)).thenReturn(Optional.of(user));
		service.createPost(post, 1);
		verify(Postrepo).save(post);
	}

	@Test
	public void createPost_UserNotFound_ThrowsException() {
		Posts post = new Posts();
		when(userRepo.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> service.createPost(post, 1));
	}

	@Test
	public void getPostById_Found_Success() throws PostsNotFoundException {
		Posts post = new Posts(); // Configure post appropriately
		when(Postrepo.findById(1)).thenReturn(Optional.of(post));
		PostsDto result = service.getPostById(1);
		assertEquals(post.getPostID(), result.getPostId());
	}

	@Test
	public void getPostById_NotFound_ThrowsException() {
		when(Postrepo.findById(1)).thenReturn(Optional.empty());
		assertThrows(PostsNotFoundException.class, () -> service.getPostById(1));
	}

	@Test
	public void updatePosts_PostNotFound_ThrowsException() {
		when(Postrepo.findById(1)).thenReturn(Optional.empty());
		assertThrows(PostsNotFoundException.class, () -> service.UpdatePosts(new Posts(), 1));
	}

	@Test
	public void deletePosts_Success() {
		service.deletePosts(1);
		verify(Postrepo).deletePostNative(1);
	}

	@Test
	public void addLikeToPost_PostNotFound_ThrowsException() {
		when(Postrepo.findById(1)).thenReturn(Optional.empty());
		assertThrows(PostsNotFoundException.class, () -> service.addliketoPost(1, 1));
	}

	@Test
	public void addLikeToPost_UserNotFound_ThrowsException() {
		Posts post = new Posts(); // Assuming you set up post object properly
		when(Postrepo.findById(1)).thenReturn(Optional.of(post));
		when(userRepo.findById(1)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> service.addliketoPost(1, 1));
	}

	@Test
	public void addCommentinPost_PostNotFound_ThrowsException() {
		when(Postrepo.findById(1)).thenReturn(Optional.empty());
		assertThrows(PostsNotFoundException.class, () -> service.addCommentinPost(1, 1, "Test comment"));
	}

	@Test
	public void addCommentinPost_UserNotFound_ThrowsException() {
		Posts post = new Posts(); // Assuming you set up post object properly
		when(Postrepo.findById(1)).thenReturn(Optional.of(post));
		when(userRepo.findById(1)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> service.addCommentinPost(1, 1, "Test comment"));
	}

	@Test
	public void RemoveCommentFromPost_PostNotFound_ThrowsException() {
		when(Postrepo.findById(1)).thenReturn(Optional.empty());
		assertThrows(PostsNotFoundException.class, () -> service.RemoveCommentFromPost(1, 1));
	}

	@Test
	public void RemoveCommentFromPost_UserNotFound_ThrowsException() {
		Posts post = new Posts(); // Assuming you set up post object properly
		when(Postrepo.findById(1)).thenReturn(Optional.of(post));
		when(userRepo.findById(1)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> service.RemoveCommentFromPost(1, 1));
	}

}
