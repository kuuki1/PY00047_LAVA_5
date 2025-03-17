package com.asm.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asm.ecommerce.model.ProductOrder;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

	List<ProductOrder> findByUserId(Integer userId);

	ProductOrder findByOrderId(String orderId);

    void deleteByOrderIdLike(String orderId);

	@Query("SELECT SUM(po.quantity), CAST(po.orderDate AS date) FROM ProductOrder po GROUP BY CAST(po.orderDate AS date) ORDER BY CAST(po.orderDate AS date)")
    List<Object[]> getTotalProductsSoldByDay();

    @Query("SELECT SUM(po.quantity), MONTH(po.orderDate), YEAR(po.orderDate) FROM ProductOrder po GROUP BY MONTH(po.orderDate), YEAR(po.orderDate) ORDER BY YEAR(po.orderDate), MONTH(po.orderDate)")
    List<Object[]> getTotalProductsSoldByMonth();

    @Query("SELECT SUM(po.quantity), YEAR(po.orderDate) FROM ProductOrder po GROUP BY YEAR(po.orderDate) ORDER BY YEAR(po.orderDate)")
    List<Object[]> getTotalProductsSoldByYear();

    @Query("SELECT SUM(po.price * po.quantity), CAST(po.orderDate AS date) FROM ProductOrder po GROUP BY CAST(po.orderDate AS date) ORDER BY CAST(po.orderDate AS date)")
    List<Object[]> getTotalIncomeByDay();

    @Query("SELECT SUM(po.price * po.quantity), MONTH(po.orderDate), YEAR(po.orderDate) FROM ProductOrder po GROUP BY MONTH(po.orderDate), YEAR(po.orderDate) ORDER BY YEAR(po.orderDate), MONTH(po.orderDate)")
    List<Object[]> getTotalIncomeByMonth();

    @Query("SELECT SUM(po.price * po.quantity), YEAR(po.orderDate) FROM ProductOrder po GROUP BY YEAR(po.orderDate) ORDER BY YEAR(po.orderDate)")
    List<Object[]> getTotalIncomeByYear();

    @Query("SELECT p.category, SUM(po.quantity) FROM ProductOrder po JOIN po.product p GROUP BY p.category")
	List<Object[]> getTotalProductsSoldByCategory();

}
