package com.algowebpro.electronic.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algowebpro.electronic.store.dtos.ApiResponseMessage;
import com.algowebpro.electronic.store.dtos.CategoryDto;
import com.algowebpro.electronic.store.dtos.PageableResponse;
import com.algowebpro.electronic.store.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// create
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		CategoryDto categoryDto2 = categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(categoryDto2, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(
			@PathVariable("id") String categoryId,
			@RequestBody CategoryDto categoryDto) {

		CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryDto);

		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("id") String categoryId) {

		categoryService.deleteCategory(categoryId);

		ApiResponseMessage response = ApiResponseMessage.builder().message("Categroy is deleted sucessfully !!")
				.status(HttpStatus.OK).success(true).build();
		return new ResponseEntity<ApiResponseMessage>(response, HttpStatus.OK);
	}

	// get all
	@GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>> getAll(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

	) {
		PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<CategoryDto>>(pageableResponse, HttpStatus.OK);
	}

	// get by id
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getById(@PathVariable("id") String categoryId) {
		CategoryDto categoryDto = categoryService.getbyId(categoryId);
		return ResponseEntity.ok(categoryDto);
	}

}
