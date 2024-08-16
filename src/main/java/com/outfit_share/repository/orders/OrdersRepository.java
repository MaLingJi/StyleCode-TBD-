package com.outfit_share.repository.orders;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outfit_share.entity.orders.Orders;

public interface OrdersRepository extends JpaRepository<Orders, UUID> {
	@Query("SELECT o FROM Orders o WHERE o.userDetail.id = :userId")
	List<Orders> findByUserId(@Param("userId") Integer userId);
	
	@Query("FROM Orders where status = ?1")
	List<Orders> findByStatus(Integer status);
}
