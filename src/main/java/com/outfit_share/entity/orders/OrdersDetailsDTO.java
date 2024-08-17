package com.outfit_share.entity.orders;

import java.util.UUID;

public class OrdersDetailsDTO {
    private Integer id;
    private Integer quantity;
    private UUID ordersId;
    private Integer productId;  // 假設 Product 的 id 是 Integer 類型
    

    // 構造函數
    public OrdersDetailsDTO(OrdersDetails ordersDetails) {
        this.id = ordersDetails.getId();
        this.quantity = ordersDetails.getQuantity();
        if (ordersDetails.getOrders() != null) {
            this.ordersId = ordersDetails.getOrders().getId();
        }
        if (ordersDetails.getProduct() != null) {
            this.productId = ordersDetails.getProduct().getProductId();
        }
    }

    // Getter 方法
    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public UUID getOrdersId() {
        return ordersId;
    }

    public Integer getProductId() {
        return productId;
    }


    // Setter 方法（如果需要的話）
    // ...
}