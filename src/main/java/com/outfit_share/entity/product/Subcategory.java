package com.outfit_share.entity.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "subcategory")
public class Subcategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subcategory_id")
	private Integer subcategoryId;//子分類ID
	
	@Column(name = "subcategory_name" ,unique = true)
	private String subcategoryName;//子分類名稱
	
	@JsonManagedReference("subcategory_category")
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Categories category;//分類ID
	
	@JsonBackReference("product_subcategory")
	@OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , mappedBy = "subcategoryId")
	private List<Product> prodcut;
}
