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
import org.springframework.web.bind.annotation.RestController;

import com.example.product.dto.CommentsDto;
import com.example.product.dto.ExceptionMessage;
import com.example.product.dto.Likesdto;
import com.example.product.dto.PostsDto;
import com.example.product.dto.PostsDto2;
import com.example.product.entity.Posts;
import com.example.product.exception.PostsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.service.PostsService;

@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin(origins ="http://localhost:3000")
public class PostsController {
	
    @Autowired
     PostsService postsService; 	
    
    @PostMapping("/{userid}")
    public ResponseEntity<String> createPost(@RequestBody Posts post,@PathVariable int userid) throws UserNotFoundException{
        String result = postsService.createPost(post,userid);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    @PostMapping("/{postId}/likes/add/{UserId}")
    public ResponseEntity<String> addLikeToPost(@PathVariable int postId, @PathVariable int UserId) throws UserNotFoundException,PostsNotFoundException{
        String result = postsService.addliketoPost(postId, UserId);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    
    @PostMapping("/{postId}/comment/add/{UserId}")
    public ResponseEntity<String> addCommentToPost(@PathVariable int postId, @PathVariable int UserId,@RequestBody String Comment) throws PostsNotFoundException,UserNotFoundException{
        String result = postsService.addCommentinPost(postId, UserId,Comment);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    
    @GetMapping("/{postid}")
	public ResponseEntity<PostsDto> getPostById(@PathVariable("postid") int PostId) throws PostsNotFoundException{
		return new ResponseEntity<PostsDto>(postsService.getPostById(PostId),HttpStatus.OK);
	}
    
    @GetMapping("/likes/{postid}")
	public ResponseEntity<List<Likesdto>> getLikesonPost(@PathVariable("postid") int PostId) throws PostsNotFoundException{
		return new ResponseEntity<List<Likesdto>>(postsService.getallLikesinPost(PostId),HttpStatus.OK);
	}
    
    
    @GetMapping("/comments/{postid}")
   	public ResponseEntity<List<CommentsDto>> getCommentsonPost(@PathVariable("postid") int PostId) throws PostsNotFoundException{
   		return new ResponseEntity<List<CommentsDto>>(postsService.getallCommentsInPost(PostId),HttpStatus.OK);
   	}
    
    @GetMapping("/all")
   	public ResponseEntity<List<PostsDto2>> getallPosts(){
   		return new ResponseEntity<List<PostsDto2>>(postsService.getallposts(),HttpStatus.OK);
   	}
    
    @PutMapping("/update/{postid}")
	public ResponseEntity<PostsDto> UpdatePosts(@RequestBody Posts posts,@PathVariable("postid") int postid) throws PostsNotFoundException{
		return new ResponseEntity<PostsDto>( postsService.UpdatePosts(posts, postid),HttpStatus.ACCEPTED);
		 
		 
			
	}
    
	@DeleteMapping("/delete/{postid}")
	public ResponseEntity<Void> deletePosts(@PathVariable("postid") int postid) throws PostsNotFoundException{
		postsService.deletePosts(postid);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		
	}
	
	@DeleteMapping("/{postId}/likes/remove/{UserId}")
    public ResponseEntity<Void> RemoveLike(@PathVariable int postId, @PathVariable int UserId) throws UserNotFoundException,PostsNotFoundException {
        postsService.removeLikeFromPost(postId, UserId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
       
    }
	
	@DeleteMapping("/{postId}/comments/remove/{UserId}")
    public ResponseEntity<Void> RemoveComment(@PathVariable int postId, @PathVariable int UserId) throws UserNotFoundException,PostsNotFoundException {
        postsService.RemoveCommentFromPost(postId, UserId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
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
	
}

