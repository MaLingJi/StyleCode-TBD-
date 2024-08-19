package com.outfit_share.repository.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.orders.OrdersDetails;

public interface OrdersDetailsRepository extends JpaRepository<OrdersDetails, Integer>{

}
