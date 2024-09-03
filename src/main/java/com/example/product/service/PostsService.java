package com.example.product.service;

import java.util.List;

import com.example.product.dto.CommentsDto;
import com.example.product.dto.Likesdto;
import com.example.product.dto.PostsDto;
import com.example.product.dto.PostsDto2;
import com.example.product.entity.Likes;
import com.example.product.entity.Posts;
import com.example.product.exception.PostsNotFoundException;
import com.example.product.exception.UserNotFoundException;

public interface PostsService {
	String createPost(Posts post,int userid) throws UserNotFoundException;
	PostsDto getPostById(int postId)throws PostsNotFoundException ;
	PostsDto UpdatePosts(Posts posts, int postid) throws PostsNotFoundException;
	void deletePosts(int postid) throws PostsNotFoundException;
	String addliketoPost(int postId,int userId) throws UserNotFoundException,PostsNotFoundException;
	List<Likesdto> getallLikesinPost(int postId) throws PostsNotFoundException;
	void removeLikeFromPost(int postId,int userId) throws PostsNotFoundException,UserNotFoundException;
	String addCommentinPost(int postId,int userId,String comment) throws PostsNotFoundException,UserNotFoundException;;
	String RemoveCommentFromPost(int postId,int userId)throws PostsNotFoundException,UserNotFoundException;
	List<CommentsDto> getallCommentsInPost(int postId) throws PostsNotFoundException;
	//additional api
	List<PostsDto2> getallposts();
}
