package com.outfit_share.repository.orders;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.outfit_share.entity.orders.TransactionLP;



public interface TransLPRepository extends JpaRepository<TransactionLP, Integer> {
	TransactionLP findByOrderId(String orderId);
	
	@Query("FROM TransactionLP WHERE transactionId = ?1")
	TransactionLP findByTransId(String transId);
	
}
