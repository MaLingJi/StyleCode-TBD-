package com.outfit_share.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Sort;
import com.outfit_share.entity.product.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

//	@Query("from Product where productName like %:n%")
//	List<Product> findByNameLikeQuery(@Param("n") String name);

	//模糊搜尋 && 價格由高到低||由低到高 && 全部商品
	@Query("SELECT p FROM Product p WHERE (:name IS NULL OR p.productName LIKE %:name%) ORDER BY "
			+ "CASE WHEN :sort = 'priceAsc' THEN p.price END ASC, "
			+ "CASE WHEN :sort = 'priceDesc' THEN p.price END DESC")
	List<Product> findByNameAndSort(@Param("name") String name, @Param("sort") String sort);

}
