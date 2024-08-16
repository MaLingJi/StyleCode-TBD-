package com.outfit_share.entity.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Categories {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="category_id")
	private Integer categoryId;//分類ID
	
	@Column(name ="category_name" ,unique = true)
	private String categoryName;//分類名稱
	
	@JsonManagedReference("subcategory_category")
	@OneToMany(cascade =  CascadeType.ALL ,fetch = FetchType.LAZY ,mappedBy = "category")
	private List<Subcategory> subcategories;
}
