package com.outfit_share.repository.orders;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outfit_share.entity.orders.Orders;

public interface OrdersRepository extends JpaRepository<Orders, String> {
	@Query("FROM Orders WHERE userDetail.id = ?1 ORDER BY orderDate ASC")
	List<Orders> findByUserId(Integer userId);
	
	@Query("FROM Orders WHERE userDetail.id = ?1 AND status =?2 ORDER BY orderDate ASC")
	List<Orders> findByUserIdAndStatus(Integer userId, Integer status);

	@Query("FROM Orders WHERE status = ?1")
	List<Orders> findByStatus(Integer status);
	
	@Query("FROM Orders WHERE orderDate BETWEEN ?1 AND ?2")
	List<Orders> findByDate (LocalDateTime startDate,LocalDateTime endDate);
}
