package com.example.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.example.bus.entity.OrderDetailsEntity;

public interface orderDetailsRepo extends JpaRepository<OrderDetailsEntity,Long>,
JpaSpecificationExecutor<OrderDetailsEntity>, QueryByExampleExecutor<OrderDetailsEntity>{

	OrderDetailsEntity findByOrderNumber(Long orderNumber);
	
	OrderDetailsEntity findByOrderId(Long orderId);
}
