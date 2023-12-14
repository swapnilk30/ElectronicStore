package com.algowebpro.electronic.store.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.algowebpro.electronic.store.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	
	//search
	
	Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
	
	Page<Product> findByLiveTrue(Pageable pageable);
	
	//custom finder methods
	//query method

}
