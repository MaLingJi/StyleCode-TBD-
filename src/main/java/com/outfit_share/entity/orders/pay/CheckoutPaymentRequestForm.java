package com.outfit_share.entity.orders.pay;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutPaymentRequestForm {
	private Integer amount;
	private String currency;
	private String orderId;
	private List<ProductPackageForm> packages;
	private RedirectUrls redirectUrls;
}
