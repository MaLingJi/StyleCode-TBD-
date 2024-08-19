package com.outfit_share.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outfit_share.entity.product.Pimages;

public interface PimagesRepository extends JpaRepository<Pimages, Integer> {
	
	// 獲取商品的所有圖片
	List<Pimages> findByProductIdProductId(Integer productId);
	
	//封面照
	@Query(value =  "select top(1)* from pimages where product_id = :id" , nativeQuery = true)
	Pimages findTopOneProductImage(@Param("id") Integer productId);
	
	//有滑鼠移入移出事件測試，切換不同的圖片
//	@Query("SELECT p FROM Pimages p WHERE p.productId.productId = :id ORDER BY p.imageType") 	
//    List<Pimages> findByProductIdOrderByImageType(@Param("id") Integer productId);
}
