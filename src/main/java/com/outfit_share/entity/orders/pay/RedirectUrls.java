package com.outfit_share.entity.orders.pay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedirectUrls {
	private String confirmUrl;
	private String cancelUrl;
	@Override
	public String toString() {
		return "RedirectUrls [confirmUrl=" + confirmUrl + ", cancelUrl=" + cancelUrl + "]";
	}
}
