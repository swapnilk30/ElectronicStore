package com.algowebpro.electronic.store.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algowebpro.electronic.store.dtos.ApiResponseMessage;
import com.algowebpro.electronic.store.dtos.ImageResponse;
import com.algowebpro.electronic.store.dtos.PageableResponse;
import com.algowebpro.electronic.store.dtos.ProductDto;
import com.algowebpro.electronic.store.services.FileService;
import com.algowebpro.electronic.store.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private FileService fileService;

	@Value("${product.image.path}")
	private String imagePath;

	// create
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
		ProductDto createdProduct = productService.create(productDto);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId,
			@RequestBody ProductDto productDto) {

		ProductDto upadtedProduct = productService.update(productId, productDto);
		return new ResponseEntity<>(upadtedProduct, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) {
		productService.delete(productId);
		ApiResponseMessage response = ApiResponseMessage.builder().message("Product is deleted successfully!!")
				.status(HttpStatus.OK).success(true).build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// get single
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getById(@PathVariable String productId) {
		ProductDto productDto1 = productService.getById(productId);
		return new ResponseEntity<>(productDto1, HttpStatus.OK);
	}

	// get all
	@GetMapping
	public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
	}

	// get live
	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy,
				sortDir);
		return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
	}

	// search
	@GetMapping("/search/{query}")
	public ResponseEntity<PageableResponse<ProductDto>> searchProduct(@PathVariable String query,
			@RequestParam(value = "pageNUmber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query, pageNumber, pageSize,
				sortBy, sortDir);
		return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
	}

	// upload Image
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponse> uploadProductImage(
			@PathVariable String productId,
			@RequestParam("productImage") MultipartFile image) throws IOException {

		String fileName = fileService.uploadFile(image, imagePath);

		ProductDto productDto = productService.getById(productId);

		productDto.setProductImageName(fileName);

		ProductDto updatedProduct = productService.update(productId, productDto);

		ImageResponse response = ImageResponse.builder().imageName(updatedProduct.getProductImageName())
				.message("Product image successfully updated !!").status(HttpStatus.CREATED).success(true).build();

		return new ResponseEntity<ImageResponse>(response, HttpStatus.CREATED);

	}

	// serve image

}
