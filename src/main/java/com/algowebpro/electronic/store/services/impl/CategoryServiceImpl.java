package com.algowebpro.electronic.store.services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.algowebpro.electronic.store.dtos.CategoryDto;
import com.algowebpro.electronic.store.dtos.PageableResponse;
import com.algowebpro.electronic.store.entities.Category;
import com.algowebpro.electronic.store.exceptions.ResourceNotFoundException;
import com.algowebpro.electronic.store.helper.Helper;
import com.algowebpro.electronic.store.repositories.CategoryRepository;
import com.algowebpro.electronic.store.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// create id randomly
		String categoryId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);

		Category category = mapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		return mapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(String categoryId, CategoryDto categoryDto) {
		// get category
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow((() -> new ResourceNotFoundException("Categrory not found !!")));
		// update category details
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		category.setCoverImage(categoryDto.getCoverImage());

		// save
		Category updatedCategory = categoryRepository.save(category);
		return mapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(String categoryId) {
		// get category
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow((() -> new ResourceNotFoundException("Categrory not found !!")));
		categoryRepository.delete(category);
	}

	@Override
	public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Category> page = categoryRepository.findAll(pageable);
		PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
		return pageableResponse;
	}

	@Override
	public CategoryDto getbyId(String categoryId) {
		// get category
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow((() -> new ResourceNotFoundException("Categrory not found !!")));
		return mapper.map(category, CategoryDto.class);
	}

}
