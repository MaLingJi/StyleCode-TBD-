package com.outfit_share.repository.orders;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.orders.Orders;

public interface OrdersRepository extends JpaRepository<Orders, UUID> {

}
