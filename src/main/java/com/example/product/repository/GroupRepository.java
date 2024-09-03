package com.example.product.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.product.entity.Groups;

@Repository
public interface GroupRepository extends JpaRepository<Groups,Integer> {
	
	@Query(value="delete from groups where groupid=:id",nativeQuery = true)
	@Modifying
	void deleteGroupsNative(@Param("id") int groupid);
	
	



	

}
