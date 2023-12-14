package com.algowebpro.electronic.store.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

	@Id
	private String productId;

	private String title;
	
	@Column(length = 1000)
	private String description;

	private double price;

	private double discountedPrice;

	private int quantity;

	private Date addedDate;

	private boolean live;

	private boolean stock;

	
	private String productImageName;
	/*
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;

*/
}
