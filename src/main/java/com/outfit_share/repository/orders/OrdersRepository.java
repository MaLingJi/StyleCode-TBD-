package com.outfit_share.repository.orders;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outfit_share.entity.orders.Orders;

public interface OrdersRepository extends JpaRepository<Orders, UUID> {
	@Query("FROM Orders WHERE userDetail.id = ?1")
    List<Orders> findByUserId(Integer userId);
}
