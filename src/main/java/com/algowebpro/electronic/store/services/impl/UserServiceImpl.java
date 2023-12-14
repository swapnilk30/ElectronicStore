package com.algowebpro.electronic.store.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.algowebpro.electronic.store.dtos.PageableResponse;
import com.algowebpro.electronic.store.dtos.UserDto;
import com.algowebpro.electronic.store.entities.User;
import com.algowebpro.electronic.store.exceptions.ResourceNotFoundException;
import com.algowebpro.electronic.store.helper.Helper;
import com.algowebpro.electronic.store.repositories.UserRepository;
import com.algowebpro.electronic.store.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		// generate userId random
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
		
		// dto to entity
		User user = dtoToEntity(userDto);
		User savedUser = userRepository.save(user);
		// entity to dto
		UserDto newDto = entityToDto(savedUser);
		return newDto;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		User user = userRepository.findById(userId)
				.orElseThrow((() -> new ResourceNotFoundException("UserNot FOund with given id!!")));

		user.setName(userDto.getName());
		// user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setGender(userDto.getGender());
		user.setAbout(userDto.getAbout());
		user.setImageName(userDto.getImageName());

		// save user
		User updatedUser = userRepository.save(user);

		UserDto updatedDto = entityToDto(updatedUser);
		return updatedDto;
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow((() -> new ResourceNotFoundException("UserNot FOund with given id!!")));
		// delete user
		userRepository.delete(user);
	}

	@Override
	public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

		// Sort sort= Sort.by(sortBy);
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		// pageNUmber default start from 0
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<User> page = userRepository.findAll(pageable);
		PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
		return response;
	}

	@Override
	public UserDto getUserById(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow((() -> new ResourceNotFoundException("UserNot FOund with given id!!")));

		return entityToDto(user);
	}

	@Override
	public UserDto getUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow((() -> new ResourceNotFoundException("User not found with given email!!")));
		return entityToDto(user);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> users = userRepository.findByNameContaining(keyword);
		List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
		return dtoList;
	}

	private User dtoToEntity(UserDto userDto) {
		User user = User.builder().userId(userDto.getUserId()).name(userDto.getName()).email(userDto.getEmail())
				.password(userDto.getPassword()).gender(userDto.getGender()).about(userDto.getAbout())
				.imageName(userDto.getImageName()).build();
		return user;
	}

	private UserDto entityToDto(User savedUser) {

		UserDto userDto = UserDto.builder().userId(savedUser.getUserId()).name(savedUser.getName())
				.email(savedUser.getEmail()).password(savedUser.getPassword()).gender(savedUser.getGender())
				.about(savedUser.getAbout()).imageName(savedUser.getImageName()).build();
		return userDto;
	}

}
