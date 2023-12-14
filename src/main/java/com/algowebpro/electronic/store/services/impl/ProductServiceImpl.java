package com.algowebpro.electronic.store.services.impl;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.algowebpro.electronic.store.dtos.PageableResponse;
import com.algowebpro.electronic.store.dtos.ProductDto;
import com.algowebpro.electronic.store.entities.Product;
import com.algowebpro.electronic.store.exceptions.ResourceNotFoundException;
import com.algowebpro.electronic.store.helper.Helper;
import com.algowebpro.electronic.store.repositories.ProductRepository;
import com.algowebpro.electronic.store.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public ProductDto create(ProductDto productDto) {
		Product product = mapper.map(productDto, Product.class);
		// generate random product id
		String productId = UUID.randomUUID().toString();
		product.setProductId(productId);

		// added date
		product.setAddedDate(new Date());

		Product savedProduct = productRepository.save(product);
		return mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductDto update(String productId, ProductDto productDto) {
		// get by id
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found of given id!!"));

		// update
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setQuantity(productDto.getQuantity());
		// product.setAddedDate(productDto.getAddedDate());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());

		// save
		Product updatedProduct = productRepository.save(product);

		return mapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public void delete(String productId) {
		// get by id
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found of given id!!"));

		productRepository.delete(product);

	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findAll(pageable);

		return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public ProductDto getById(String productId) {
		// get by id
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found of given id!!"));

		return mapper.map(product, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy,
			String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);

		return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByLiveTrue(pageable);

		return Helper.getPageableResponse(page, ProductDto.class);
	}

}
