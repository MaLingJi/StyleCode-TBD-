package com.outfit_share.entity.orders;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutPaymentRequestForm {
	private BigDecimal amount;
	private String currency;
	private String orderId;
	private List<ProductPackageForm> packages;
	private RedirectUrls redirectUrls;
}
