package com.example.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.example.bus.entity.UserDetailsEntity;


@Repository
public interface userDetailsRepo extends JpaRepository<UserDetailsEntity,Long>,
JpaSpecificationExecutor<UserDetailsEntity>, QueryByExampleExecutor<UserDetailsEntity>{

	boolean existsByMailId(String email);

    boolean existsByMobileNumber(String mobile);
    
    UserDetailsEntity findByUserId(Long userId);
    
    UserDetailsEntity findByMailId(String mailId);
	
}
