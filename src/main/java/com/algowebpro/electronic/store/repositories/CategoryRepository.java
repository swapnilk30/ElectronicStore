package com.algowebpro.electronic.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algowebpro.electronic.store.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,String>{

}
