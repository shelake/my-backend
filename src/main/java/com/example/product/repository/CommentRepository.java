package com.example.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.product.entity.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer> {
	
	@Query(value="delete from comments where commentid=:id",nativeQuery = true)
	@Modifying
	void deleteCommentsNative(@Param("id") int id);

}
