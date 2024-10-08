package com.outfit_share.repository.orders;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outfit_share.entity.orders.OrdersDetails;

public interface OrdersDetailsRepository extends JpaRepository<OrdersDetails, Integer>{
	@Query("FROM OrdersDetails where orders.id=?1")
	List<OrdersDetails> findByOrdersId(String orderId);
} 
