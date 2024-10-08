package com.outfit_share.entity.orders;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.outfit_share.entity.users.UserDetail;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Orders {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "totalAmounts")
	private Integer totalAmounts;

	@Column(name = "status")
	private Integer status;
	
	@Column(name = "payment_method")
	private Integer payment_method;
	
	@Column(name = "shipping_address")
	private String shipping_address;
	
	@Column(name = "discount_points")
	private Integer discount_points;
	
	@Column(name = "updated_at")
	private Date updated_at;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="applyRefund_at")
	private LocalDateTime applyRefundDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="reviewRefund_at")
	private LocalDateTime reviewRefundDate;
	
	@Column(name="refundStatus")
	private Integer refundStatus;
	
	@Column(name="refundReason")
	private String refundReason;
	
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "order_date")
    private LocalDateTime orderDate;

	
	@ManyToOne
	@JoinColumn(name="user_id")
	private  UserDetail userDetail;
	

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "orders")
	private List<OrdersDetails> ordersDetails;
	

	@PrePersist
	public void onCreate() {
		if (orderDate == null) {
			orderDate = LocalDateTime.now();
		}
		if(status == null) {
			this.status = 0;
		}
	}
	
	

}

