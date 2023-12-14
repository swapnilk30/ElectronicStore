package com.algowebpro.electronic.store.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
	
	private String productId;

	private String title;

	private String description;

	private double price;

	private double discountedPrice;

	private int quantity;

	private Date addedDate;

	private boolean live;

	private boolean stock;

	private String productImageName;
}
