package com.algowebpro.electronic.store.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

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
import com.algowebpro.electronic.store.dtos.UserDto;
import com.algowebpro.electronic.store.services.FileService;
import com.algowebpro.electronic.store.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Value("${user.profile.image.path}")
	private String imageUploadPath;

	// create
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto userDto1 = userService.createUser(userDto);
		return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
			@PathVariable("userId") String userId) {

		UserDto updatedUserDto = userService.updateUser(userId, userDto);
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		ApiResponseMessage message = ApiResponseMessage.builder().message("User is Deleted successfully!").success(true)
				.status(HttpStatus.OK).build();
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	// get all
	@GetMapping
	public ResponseEntity<PageableResponse<UserDto>> getAllUser(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
	}

	// get single user by id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserbyId(@PathVariable String userId) {
		return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
	}

	// get by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserbyEmail(@PathVariable String email) {
		return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
	}

	// search user
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
		return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
	}

	// Upload user image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(
			@RequestParam("userImage") MultipartFile image,
			@PathVariable String userId) throws IOException {
		String imageName = fileService.uploadFile(image, imageUploadPath);
		UserDto user = userService.getUserById(userId);
		user.setImageName(imageName);
		UserDto userDto = userService.updateUser(userId, user);
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).status(HttpStatus.CREATED)
				.success(true).build();
		return new ResponseEntity(imageResponse, HttpStatus.CREATED);
	}
	// serve user image

}
