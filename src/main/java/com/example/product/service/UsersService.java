package com.example.product.service;

import java.util.List;

import com.example.product.dto.CommentsDto;
import com.example.product.dto.Friendsdto;
import com.example.product.dto.Likesdto;
import com.example.product.dto.PostsDto;
import com.example.product.dto.UsersDto;
import com.example.product.dto.groupsDto;
import com.example.product.entity.Comments;
import com.example.product.entity.Friends;
import com.example.product.entity.Groups;
import com.example.product.entity.Likes;
import com.example.product.entity.Message;
import com.example.product.entity.Notifications;
import com.example.product.entity.Posts;
import com.example.product.entity.Users;
import com.example.product.exception.FriendsNotFoundException;
import com.example.product.exception.PostsNotFoundException;
import com.example.product.exception.UserNotFoundException;

public interface UsersService {
	List<UsersDto> getAllUsers();
   UsersDto getUserbyId(int userId) throws UserNotFoundException;
   UsersDto getUserbyName(String name) throws UserNotFoundException;
   void addUsers(UsersDto user) ;
   void updateUsers(UsersDto user,int userId) throws UserNotFoundException;
   void deleteUser(int userId) throws UserNotFoundException;
   public List<PostsDto> getallPostOfUser(int userId) throws UserNotFoundException;
   List<Friendsdto> getAllFriendsOfUser(int userId)throws UserNotFoundException;
   List<CommentsDto> getAllCommentsInPostByUser(int userId) throws UserNotFoundException;
   List<Friendsdto> getAllRequestInPending(int userId) throws UserNotFoundException;
   String sendRequest(int from,int to) throws UserNotFoundException;
   List<Message> getAllMessageBetweenUser(int send,int received)throws UserNotFoundException;
   String sendMessage(int from,int to,String Message) throws UserNotFoundException;
   List<Likesdto> getlikesPostbyUser(int userid) throws UserNotFoundException;
   List<Notifications> getnotification(int userid) throws UserNotFoundException;
   List<groupsDto> getGroups(int userid) throws UserNotFoundException;
   String AddFriend(int userid,int friendid) throws UserNotFoundException;
   String removeFriend(int userid,int friendid) throws UserNotFoundException;
   List<PostsDto> getAllPostsLikedByUser(int userid) throws UserNotFoundException;
   //additional API's
   List<UsersDto> getRandomUsers();
  
   


}
