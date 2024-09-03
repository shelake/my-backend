package com.example.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.product.entity.Friends;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Integer> {
	
	@Query(value="delete from friends where friendshipid=:id",nativeQuery = true)
	@Modifying
	void deleteMessageNative(@Param("id") int id);
	 

 
}