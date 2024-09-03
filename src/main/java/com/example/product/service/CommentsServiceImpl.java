package com.example.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product.entity.Comments;
import com.example.product.entity.Posts;
import com.example.product.entity.Users;
import com.example.product.exception.CommentNotFoundException;
import com.example.product.exception.PostsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.CommentRepository;
import com.example.product.repository.PostRepository;
import com.example.product.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentsServiceImpl implements CommentService {
	
	@Autowired
	   CommentRepository repo;
		@Autowired
		UsersRepository userRepo;
		@Autowired
		PostRepository postRepo;

	 
		@Override
		public List<Comments> getAllComments() {
			return repo.findAll();
		}
	 
		@Override
		public Comments getCommentbyId(int CommentId) throws CommentNotFoundException{	
			if(repo.findById(CommentId).isEmpty()) {
				throw new CommentNotFoundException("Comment with id "+CommentId+" is not found");
			}
			
			return repo.findById(CommentId).get();
			}
	 
		@Override
		@Transactional
		public void addComment(Comments comments,int UserId,int PostId) throws UserNotFoundException,PostsNotFoundException{
			if(userRepo.findById(UserId).isEmpty()) {
				throw new UserNotFoundException("User with id "+UserId+" is not found");
			}
			if(postRepo.findById(PostId).isEmpty()) {
				throw new PostsNotFoundException("Comment with id "+PostId+" is not found");
			}
			
			
			Users user=userRepo.findById(UserId).get();
			Posts post=postRepo.findById(PostId).get();
			comments.setPosts(post);
			comments.setUsers(user);
			repo.save(comments);
		}
	 
		@Override
		@Transactional
		public Comments updateComment(Comments comments, int commentsId) throws CommentNotFoundException{
			if(repo.findById(commentsId).isEmpty()) {
				throw new CommentNotFoundException("Comment with id "+commentsId+" is not found");
			}
			
			Comments Originalcomments=repo.findById(commentsId).get();
			Originalcomments.setComment_text(comments.getComment_text());
			repo.save(Originalcomments);		
			return Originalcomments;
		}
	 
		@Override
		@Transactional
		public void deleteComment(int commentsId) throws CommentNotFoundException{
			if(repo.findById(commentsId).isEmpty()) {
				throw new CommentNotFoundException("Comment with id "+commentsId+" is not found");
			}
			repo.deleteCommentsNative(commentsId);		

		}

		@Override
		@Transactional
		public void deletecommentofauser(int userid, int commentid) throws UserNotFoundException, CommentNotFoundException {
			if(userRepo.findById(userid).isEmpty()) {
				throw new UserNotFoundException("User with id "+userid+" is not found");
			}
			if(repo.findById(commentid).isEmpty()) {
				throw new CommentNotFoundException("Comment with id "+commentid+" is not found");
				
			}
			
			Users user=userRepo.findById(userid).get();
			
			int comment=user.getComments().stream().filter(c->c.getCommentID()==commentid).findFirst().get().getCommentID();
			repo.deleteCommentsNative(comment);
		
		
			
			
			
			
		}


}
