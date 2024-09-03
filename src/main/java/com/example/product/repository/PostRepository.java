package com.example.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.product.entity.Posts;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {
	
	@Query(value="delete from posts where postid=:id",nativeQuery = true)
	@Modifying
	void deletePostNative(@Param("id") int id);
	

}
