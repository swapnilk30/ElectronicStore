package com.algowebpro.electronic.store.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algowebpro.electronic.store.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

	// custom finder method
	// User findByEmail(String email);

	Optional<User> findByEmail(String email);

	User findByEmailAndPassword(String email, String password);

	List<User> findByNameContaining(String keyword);
}
