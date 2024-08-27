package com.outfit_share.entity.orders;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "transactionLP")
@Getter
@Setter
@NoArgsConstructor
public class TransactionLP {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
    private String transactionId;
    
    private String orderId;
    
    private Integer amount;
    
    private Integer userId;

	@Override
	public String toString() {
		return "TransactionLP [id=" + id + ", transactionId=" + transactionId + ", orderId=" + orderId + ", amount="
				+ amount + ", userId=" + userId + "]";
	}
	
    
}
