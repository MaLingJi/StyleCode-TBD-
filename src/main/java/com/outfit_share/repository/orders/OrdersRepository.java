package com.outfit_share.repository.orders;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outfit_share.entity.orders.Orders;

public interface OrdersRepository extends JpaRepository<Orders, UUID> {
	@Query("FROM Orders WHERE userDetail.id = ?1")
	List<Orders> findByUserId(Integer userId);
	
	@Query("FROM Orders WHERE userDetail.id = ?1 AND status =?2")
	List<Orders> findByUserIdAndStatus(Integer userId, Integer status);

	@Query("FROM Orders WHERE status = ?1")
	List<Orders> findByStatus(Integer status);
}
