package com.example.product.service;

import java.util.List;

import com.example.product.entity.Comments;
import com.example.product.exception.CommentNotFoundException;
import com.example.product.exception.PostsNotFoundException;
import com.example.product.exception.UserNotFoundException;

public interface CommentService {
	List<Comments> getAllComments();
		Comments getCommentbyId(int CommentId) throws CommentNotFoundException;
		void addComment(Comments comments,int UserId,int PostId) throws UserNotFoundException,PostsNotFoundException;
		Comments updateComment(Comments comments,int commentsId) throws CommentNotFoundException;
		void deleteComment(int commentsId) throws CommentNotFoundException;
		//additional Api
		void deletecommentofauser(int userid,int commentid) throws UserNotFoundException,CommentNotFoundException;
	}


