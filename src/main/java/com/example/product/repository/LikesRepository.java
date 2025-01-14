package com.example.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.product.entity.Likes;


@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer>{
	
	@Query(value="delete from likes where likeid=:id",nativeQuery = true)
	@Modifying
	void deleteLikeNative(@Param("id") int id);

}
