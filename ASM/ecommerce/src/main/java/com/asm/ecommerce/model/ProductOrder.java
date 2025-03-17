package com.asm.ecommerce.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String orderId;

	private LocalDateTime orderDate;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private Double price;

	private Integer quantity;

	@ManyToOne
	private UserDtls user;

	private String status;

	// @Column(name = "product_id", insertable = false, updatable = false)
	// private Long productId;

	private String paymentType;

	@OneToOne(cascade = CascadeType.ALL)
	private OrderAddress orderAddress;

}