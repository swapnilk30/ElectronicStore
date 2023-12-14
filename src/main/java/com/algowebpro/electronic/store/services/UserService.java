package com.algowebpro.electronic.store.services;

import java.util.List;

import com.algowebpro.electronic.store.dtos.PageableResponse;
import com.algowebpro.electronic.store.dtos.UserDto;

public interface UserService {
	
    //create
    UserDto createUser(UserDto userDto);
    //update
    UserDto updateUser(String userId,UserDto userDto);
    //delete
    void deleteUser(String userId);
    //getAll
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
    //getSingle by id
    UserDto getUserById(String userId);
    //get single user by email
    UserDto getUserByEmail(String email);
    //search user
    List<UserDto> searchUser(String keyword);

}
