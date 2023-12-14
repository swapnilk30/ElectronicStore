package com.algowebpro.electronic.store.entities;

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
@Table(name = "Categories")
public class Category {

	@Id
	@Column(name = "id")
	private String categoryId;

	@Column(name = "category_title", length = 60, nullable = false)
	private String title;

	@Column(name = "category_desc", length = 255)
	private String description;
	
	
	private String coverImage;

	/*
	
	// other properties
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> products = new ArrayList<>();

	*/
}
