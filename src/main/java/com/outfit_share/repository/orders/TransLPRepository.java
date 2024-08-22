package com.outfit_share.repository.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.orders.TransactionLP;



public interface TransLPRepository extends JpaRepository<TransactionLP, Integer> {
	TransactionLP findByOrderId(String orderId);
}
