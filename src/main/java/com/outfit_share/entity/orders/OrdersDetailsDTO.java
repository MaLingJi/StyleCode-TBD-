package com.outfit_share.entity.orders;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdersDetailsDTO {
    private Integer id;
    private Integer quantity;
    private UUID ordersId;
    private Integer productId; 
    private String productName;
    private Integer price;
    

//    // 構造函數
//    public OrdersDetailsDTO(OrdersDetails ordersDetails) {
//        this.id = ordersDetails.getId();
//        this.quantity = ordersDetails.getQuantity();
//        
//        if (ordersDetails.getOrders() != null) {
//            this.ordersId = ordersDetails.getOrders().getId();
//        }
//        if (ordersDetails.getProduct() != null) {
//            this.productId = ordersDetails.getProduct().getProductId();
//        }
//    }


}