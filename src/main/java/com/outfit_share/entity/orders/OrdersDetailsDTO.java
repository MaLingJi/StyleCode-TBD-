	package com.outfit_share.entity.orders;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdersDetailsDTO {
    private Integer id;
    private Integer quantity;
    private String ordersId;
    private Integer productDetailsId; 
    private String productName;
    private Integer price;
	private Integer paymentMethod;
	
    private Integer subcatogoryId;
    private String subcatogoryName;
    private Integer catogoryId;
    private String catogoryName;


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