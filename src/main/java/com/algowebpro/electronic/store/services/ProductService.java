package com.algowebpro.electronic.store.services;

import com.algowebpro.electronic.store.dtos.PageableResponse;
import com.algowebpro.electronic.store.dtos.ProductDto;

public interface ProductService {

	// create
	ProductDto create(ProductDto productDto);

	// update
	ProductDto update(String productId, ProductDto productDto);

	// delete
	void delete(String productId);

	// get all
	// List<ProductDto> getAll();
	PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

	// get by id
	ProductDto getById(String productId);

	// search
	PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy,
			String sortDir);

	// get all live
	PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);
	// other methods

}
