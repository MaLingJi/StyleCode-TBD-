package com.outfit_share.entity.orders.pay;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmData {
	private BigDecimal amount;
	private String currency;
}
