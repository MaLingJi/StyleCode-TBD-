package com.outfit_share.entity.orders;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class RefundDTO {
	private String orderId;
	private Integer refundAmounts;
	private Integer refundStatus;
	private Integer userId;
	private String refundReason;
	private LocalDateTime applyRefundDate;
	private LocalDateTime reviewRefundDate;
	
	public RefundDTO(Orders orders) {
		this.orderId = orders.getId();
		this.refundReason =orders.getRefundReason();
		this.refundAmounts = orders.getTotalAmounts();
		this.refundStatus = orders.getRefundStatus();
		this.applyRefundDate = orders.getApplyRefundDate();
	}
}
